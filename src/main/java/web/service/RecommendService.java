package web.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import web.model.dao.*;
import web.model.dto.FairDto;
import web.model.dto.MembersDto;
import web.model.dto.PageDto;
import web.service.strategy.CategoryRecommendStrategy;
import web.service.strategy.PopularRecommendStrategy;
import web.service.strategy.WishlistRecommendStrategy;

import java.util.*;

@Service
public class RecommendService {

    @Autowired CategoryRecommendStrategy categoryStrategy;
    @Autowired PopularRecommendStrategy popularRecommendStrategy;
    @Autowired WishlistRecommendStrategy wishlistRecommendStrategy;
    @Autowired VisitLogDao visitLogDao;
    @Autowired WishListDao wishListDao;
    @Autowired MemberDao memberDao;
    @Autowired FairDao fairDao;

    private static final int RECOMMEND_COUNT = 10;

    // 회원별 추천 박람회 리스트
    public List<FairDto> getRecommendations(int mno){

        MembersDto member = memberDao.info(mno);
        String memberAddress = member != null ? member.getMaddress() : "";

        Map<Integer, Double> scoreMap = new HashMap<>();

        // 인기순 점수
        List<FairDto> popularList = popularRecommendStrategy.recommend(mno);
        for(FairDto fair : popularList){
            if(fair != null) scoreMap.put(fair.getFno(), scoreMap.getOrDefault(fair.getFno(), 0.0) + 10.0);
        }

        // 즐겨찾기 기반 점수
        List<FairDto> wishlistList = wishlistRecommendStrategy.recommend(mno);
        for(FairDto fair : wishlistList){
            if(fair != null) scoreMap.put(fair.getFno(), scoreMap.getOrDefault(fair.getFno(), 0.0) + 20.0);
        }

        // 카테고리 기반 점수
        List<FairDto> categoryList = categoryStrategy.recommend(mno);
        for(FairDto fair : categoryList){
            if(fair != null) scoreMap.put(fair.getFno(), scoreMap.getOrDefault(fair.getFno(), 0.0) + 15.0);
        }

        // 최근 방문 기반 점수
        List<Integer> recentFnoList = visitLogDao.getVisitFnoByMember(mno);
        for(Integer fno : recentFnoList){
            if(fno != null && fairDao.getFairbyFno(fno) != null){
                scoreMap.put(fno, scoreMap.getOrDefault(fno, 0.0) + 25.0);
            }
        }

        // 지역 기반 점수
        List<FairDto> allFairs = fairDao.selectAllFairs();
        for(FairDto fair : allFairs){
            if(fair != null && !memberAddress.isEmpty() && fair.getFplace() != null &&
                    fair.getFplace().contains(memberAddress)){
                scoreMap.put(fair.getFno(), scoreMap.getOrDefault(fair.getFno(), 0.0) + 15.0);
            }
        }

        // ============= 점수 출력 디버그용 =============
        System.out.println("=== 점수 맵 ===");
        for(Integer fno : scoreMap.keySet()){
            FairDto fair = fairDao.getFairbyFno(fno);
            if(fair != null){
                System.out.println(fair.getFname() + " : 점수=" + scoreMap.get(fno));
            } else {
                System.out.println("존재하지 않는 FNO: " + fno + ", 점수=" + scoreMap.get(fno));
            }
        }

        double totalScore = scoreMap.values().stream().mapToDouble(Double::doubleValue).sum();
        if(totalScore == 0) return new ArrayList<>();

        // ============= 확률 출력 디버그용 =============
        System.out.println("\n=== 확률 맵 ===");
        Map<Integer, Double> probabilityMap = new HashMap<>();
        for(Integer fno : scoreMap.keySet()){
            double prob = scoreMap.get(fno) / totalScore * 100;
            probabilityMap.put(fno, prob);
            FairDto fair = fairDao.getFairbyFno(fno);
            if(fair != null){
                System.out.println(fair.getFname() + " : 확률=" + String.format("%.2f", prob) + "%");
            } else {
                System.out.println("존재하지 않는 FNO: " + fno + ", 확률=" + String.format("%.2f", prob) + "%");
            }
        }

        List<Integer> fnoPool = new ArrayList<>(scoreMap.keySet());
        List<FairDto> resultList = new ArrayList<>();
        Random random = new Random();

        // 확률 기반 랜덤 추천
        System.out.println("\n=== 선택 과정 맵 ===");
        while(resultList.size() < RECOMMEND_COUNT && !fnoPool.isEmpty()){
            double r = random.nextDouble() * totalScore;
            double cumulative = 0.0;
            Integer selectedFno = null;

            for(Integer fno : fnoPool){
                cumulative += scoreMap.get(fno);
                if(r <= cumulative){
                    selectedFno = fno;
                    break;
                }
            }

            if(selectedFno != null){
                FairDto selectedFair = fairDao.getFairbyFno(selectedFno);
                if(selectedFair != null){
                    resultList.add(selectedFair);
                    System.out.println("랜덤값: " + r + ", 선택된 박람회: " +
                            selectedFair.getFname() +
                            ", 점수: " + scoreMap.get(selectedFno) +
                            ", 현재 totalScore: " + totalScore);
                } else {
                    System.out.println("랜덤 선택된 FNO 존재하지 않음: " + selectedFno);
                }

                totalScore -= scoreMap.get(selectedFno);
                fnoPool.remove(selectedFno);
            }
        }

        return resultList;
    }

    // 추천 결과 PageDto 로 변환 (검색 + 페이징)
    public PageDto getRecommendationsPaged(int mno, int page, int count, String key, String keyword){

        List<FairDto> allRecommendation = getRecommendations(mno);

        if(key != null && !key.isEmpty() && keyword != null && !keyword.isEmpty()){
            String lowerKeyword = keyword.toLowerCase();
            allRecommendation = allRecommendation.stream()
                    .filter(fair -> fair != null && switch (key){
                        case "fname" -> fair.getFname() != null && fair.getFname().toLowerCase().contains(lowerKeyword);
                        case "fplace" -> fair.getFplace() != null && fair.getFplace().toLowerCase().contains(lowerKeyword);
                        case "finfo" -> fair.getFinfo() != null && fair.getFinfo().toLowerCase().contains(lowerKeyword);
                        default -> true;
                    })
                    .toList();
        }

        int totalCount = allRecommendation.size();
        int totalPage = totalCount % count == 0 ? totalCount / count : totalCount / count + 1;
        int startRow = (page -1) * count;
        int endRow = Math.min(startRow + count, totalCount);
        List<FairDto> pageList = (startRow < totalCount) ? allRecommendation.subList(startRow , endRow) : Collections.emptyList();

        int btnCount = 5;
        int startBtn = ((page - 1) / btnCount) * btnCount + 1;
        int endBtn = Math.min(startBtn + btnCount -1 , totalPage);

        PageDto pageDto = new PageDto();
        pageDto.setCurrentPage(page);
        pageDto.setTotalPage(totalPage);
        pageDto.setPerCount(count);
        pageDto.setTotalCount(totalCount);
        pageDto.setStartBtn(startBtn);
        pageDto.setEndBtn(endBtn);
        pageDto.setData(pageList);

        return pageDto;
    }

}

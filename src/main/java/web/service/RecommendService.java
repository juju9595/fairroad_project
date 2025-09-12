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

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
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


    private static final int RECOMMEND_COUNT = 10; // 한 번에 보여줄 추천 박람회 수

    public List<FairDto> getRecommendations(int mno, List<Integer> excludedFno) {

        MembersDto member = memberDao.info(mno);
        String memberAddress = member != null ? member.getMaddress() : "";

        Map<Integer, Double> scoreMap = new HashMap<>();
        System.out.println("=== STEP 1: 초기 scoreMap ===");
        System.out.println(scoreMap);

        // -------------------- 인기순 추천 점수 --------------------
        List<FairDto> popularList = popularRecommendStrategy.recommend(mno);
        for (FairDto fair : popularList) {
            if (fair != null && (excludedFno == null || !excludedFno.contains(fair.getFno()))) {
                double prev = scoreMap.getOrDefault(fair.getFno(), 0.0);
                double added = 20.0;
                scoreMap.put(fair.getFno(), prev + added);
                System.out.printf("[Popularity] FNO: %d, 이전 점수: %.1f, 추가: %.1f, 현재 점수: %.1f%n",
                        fair.getFno(), prev, added, prev + added);
            }
        }

        // -------------------- 즐겨찾기 점수 --------------------
        List<FairDto> wishlistList = wishlistRecommendStrategy.recommend(mno);
        for (FairDto fair : wishlistList) {
            if (fair != null && (excludedFno == null || !excludedFno.contains(fair.getFno()))) {
                double prev = scoreMap.getOrDefault(fair.getFno(), 0.0);
                double added = 20.0;
                scoreMap.put(fair.getFno(), prev + added);
                System.out.printf("[Wishlist] FNO: %d, 이전 점수: %.1f, 추가: %.1f, 현재 점수: %.1f%n",
                        fair.getFno(), prev, added, prev + added);
            }
        }

        // -------------------- 카테고리 점수 --------------------
        List<FairDto> categoryList = categoryStrategy.recommend(mno);
        for (FairDto fair : categoryList) {
            if (fair != null && (excludedFno == null || !excludedFno.contains(fair.getFno()))) {
                double prev = scoreMap.getOrDefault(fair.getFno(), 0.0);
                double added = 15.0;
                scoreMap.put(fair.getFno(), prev + added);
                System.out.printf("[Category] FNO: %d, 이전 점수: %.1f, 추가: %.1f, 현재 점수: %.1f%n",
                        fair.getFno(), prev, added, prev + added);
            }
        }

        // -------------------- 최근 방문 점수 --------------------
        List<Integer> recentFnoList = visitLogDao.getVisitFnoByMember(mno);
        for (Integer fno : recentFnoList) {
            if (fno != null && fairDao.getFairbyFno(fno) != null &&
                    (excludedFno == null || !excludedFno.contains(fno))) {
                double prev = scoreMap.getOrDefault(fno, 0.0);
                double added = 25.0;
                scoreMap.put(fno, prev + added);
                System.out.printf("[RecentVisit] FNO: %d, 이전 점수: %.1f, 추가: %.1f, 현재 점수: %.1f%n",
                        fno, prev, added, prev + added);
            }
        }

        // -------------------- 지역 점수 --------------------
        List<FairDto> allFairs = fairDao.selectAllFairs();
        for (FairDto fair : allFairs) {
            if (fair != null && !memberAddress.isEmpty() && fair.getFplace() != null &&
                    fair.getFplace().contains(memberAddress) &&
                    (excludedFno == null || !excludedFno.contains(fair.getFno()))) {
                double prev = scoreMap.getOrDefault(fair.getFno(), 0.0);
                double added = 15.0;
                scoreMap.put(fair.getFno(), prev + added);
                System.out.printf("[Location] FNO: %d, 이전 점수: %.1f, 추가: %.1f, 현재 점수: %.1f%n",
                        fair.getFno(), prev, added, prev + added);
            }
        }

        // -------------------- 종료 박람회 제거 --------------------
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        scoreMap.keySet().removeIf(fno -> {
            FairDto fair = fairDao.getFairbyFno(fno);
            if (fair == null || fair.getEnd_date() == null) return true;
            try {
                LocalDate endDate = LocalDate.parse(fair.getEnd_date(), formatter);
                return !endDate.isAfter(LocalDate.now());
            } catch (Exception e) {
                return true;
            }
        });
        System.out.println("=== STEP 7: 종료 박람회 제거 후 scoreMap ===");
        scoreMap.forEach((k,v) -> System.out.printf("FNO: %d, 최종 점수: %.1f%n", k, v));

        // -------------------- 가중치 랜덤 선택 --------------------
        double totalScore = scoreMap.values().stream().mapToDouble(Double::doubleValue).sum();
        if (totalScore == 0) return new ArrayList<>();

        List<Integer> fnoPool = new ArrayList<>(scoreMap.keySet());
        List<FairDto> resultList = new ArrayList<>();
        Random random = new Random();

        System.out.println("=== STEP 8: Weighted Random 후보 풀 ===");
        System.out.println(fnoPool);

        while (resultList.size() < RECOMMEND_COUNT && !fnoPool.isEmpty()) {
            double r = random.nextDouble() * totalScore;
            double cumulative = 0.0;
            Integer selectedFno = null;

            for (Integer fno : fnoPool) {
                cumulative += scoreMap.get(fno);
                if (r <= cumulative) {
                    selectedFno = fno;
                    break;
                }
            }

            if (selectedFno != null) {
                FairDto selectedFair = fairDao.getFairbyFno(selectedFno);
                if (selectedFair != null) {
                    resultList.add(selectedFair);
                    System.out.printf("[Weighted Random] 랜덤값: %.2f, 선택 FNO: %d, 이름: %s, 점수: %.1f%n",
                            r, selectedFno, selectedFair.getFname(), scoreMap.get(selectedFno));
                }
                totalScore -= scoreMap.get(selectedFno);
                fnoPool.remove(selectedFno);
            }
        }

        System.out.println("=== STEP 9: 최종 추천 리스트 ===");
        for (FairDto f : resultList) {
            System.out.printf("FNO: %d | 이름: %s | 점수: %.1f%n", f.getFno(), f.getFname(), scoreMap.getOrDefault(f.getFno(), 0.0));
        }

        return resultList;
    }


    public PageDto getRecommendationsPaged(int mno, int page, int count, String key, String keyword, List<Integer> shownFno) {

        // ===================== 1️⃣ 추천 후보 조회 =====================
        List<FairDto> allRecommendation = getRecommendations(mno, shownFno); // excludedFno 적용

        // ===================== 2️⃣ 검색 필터 적용 =====================
        if (key != null && !key.isEmpty() && keyword != null && !keyword.isEmpty()) {
            String lowerKeyword = keyword.toLowerCase();
            allRecommendation = allRecommendation.stream()
                    .filter(fair -> fair != null && switch (key) {
                        case "fname" -> fair.getFname() != null && fair.getFname().toLowerCase().contains(lowerKeyword);
                        case "fplace" -> fair.getFplace() != null && fair.getFplace().toLowerCase().contains(lowerKeyword);
                        case "finfo" -> fair.getFinfo() != null && fair.getFinfo().toLowerCase().contains(lowerKeyword);
                        default -> true;
                    })
                    .toList();
        }

        // ===================== 3️⃣ 중복 제거 =====================
        if (shownFno != null && !shownFno.isEmpty()) {
            Set<Integer> showSet = new HashSet<>(shownFno);
            allRecommendation = allRecommendation.stream()
                    .filter(fair -> fair != null && !showSet.contains(fair.getFno()))
                    .toList();
        }

        // ===================== 4️⃣ 종료된 박람회 제외 =====================
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        allRecommendation = allRecommendation.stream()
                .filter(fair -> {
                    if (fair == null || fair.getEnd_date() == null) return false;
                    try {
                        LocalDate endDate = LocalDate.parse(fair.getEnd_date(), formatter);
                        return endDate.isAfter(LocalDate.now());
                    } catch (Exception e) {
                        return false;
                    }
                })
                .toList();

        // ===================== 5️⃣ 페이지별 슬라이스 =====================
        int startRow = (page - 1) * count;
        int endRow = Math.min(startRow + count, allRecommendation.size());
        List<FairDto> pageList = (startRow < allRecommendation.size()) ?
                new ArrayList<>(allRecommendation.subList(startRow, endRow)) :
                new ArrayList<>();

        // ===================== 6️⃣ perCount만큼 부족하면 전체 박람회에서 랜덤 채움 =====================
        if (pageList.size() < count) {
            // 전체 박람회 조회
            List<FairDto> allFairs = fairDao.selectAllFairs();
            Random random = new Random();
            Set<Integer> existingFno = new HashSet<>();
            pageList.forEach(f -> existingFno.add(f.getFno()));

            while (pageList.size() < count && !allFairs.isEmpty()) {
                // 랜덤 선택
                FairDto candidate = allFairs.get(random.nextInt(allFairs.size()));
                if (candidate != null
                        && candidate.getEnd_date() != null
                        && !existingFno.contains(candidate.getFno())) {
                    // 종료일 체크
                    try {
                        LocalDate endDate = LocalDate.parse(candidate.getEnd_date(), formatter);
                        if (endDate.isAfter(LocalDate.now())) {
                            pageList.add(candidate);
                            existingFno.add(candidate.getFno());
                        }
                    } catch (Exception e) {
                        // 파싱 실패하면 제외
                    }
                }
            }
        }

        // ===================== 7️⃣ PageDto 구성 =====================
        int totalCount = allRecommendation.size();
        int totalPage = (int) Math.ceil((double) totalCount / count);

        PageDto pageDto = new PageDto();
        pageDto.setCurrentPage(page);
        pageDto.setTotalCount(totalCount);
        pageDto.setPerCount(count);
        pageDto.setTotalPage(totalPage);
        pageDto.setData(pageList);
        pageDto.setLastPage(page >= totalPage);

        return pageDto;
    } // func e

} // class e


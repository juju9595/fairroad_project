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

        /*
         * ===================== 전체 로직 흐름 (핵심 처리 기능) =====================
         * 1️⃣ Popularity-based Recommendation       → 인기순 점수 누적
         * 2️⃣ Wishlist-based Recommendation         → 즐겨찾기 기반 점수 누적
         * 3️⃣ Category-based Recommendation         → 카테고리 기반 점수 누적
         * 4️⃣ Recent Visit-based Recommendation     → 최근 방문 기반 점수 누적
         * 5️⃣ Location-based Recommendation         → 지역 기반 점수 누적
         * 6️⃣ Expired Event Filtering               → 종료된 박람회 제거
         * 7️⃣ Weighted Random Selection             → 점수 기반 확률적 랜덤 추천
         * 8️⃣ Fallback Random Recommendation       → 부족 시 전체 박람회에서 랜덤 채움
         * 9️⃣ Exclusion List Handling               → 이미 보여준 항목 제외
         */

        // ===================== 1️⃣ 회원 정보 조회 =====================
        MembersDto member = memberDao.info(mno); // 회원 정보 가져오기
        String memberAddress = member != null ? member.getMaddress() : ""; // 회원 주소, 지역 추천에 활용

        // ===================== 2️⃣ 후보 박람회 점수 맵 생성 =====================
        Map<Integer, Double> scoreMap = new HashMap<>(); // key: FNO, value: 점수

        // ===================== 3️⃣ 인기순 추천 점수 =====================
        List<FairDto> popularList = popularRecommendStrategy.recommend(mno);
        for (FairDto fair : popularList) {
            if (fair != null && (excludedFno == null || !excludedFno.contains(fair.getFno()))) {
                // 점수 누적 (Popularity-based Recommendation)
                scoreMap.put(fair.getFno(), scoreMap.getOrDefault(fair.getFno(), 0.0) + 10.0);
            }
        }

        // ===================== 4️⃣ 즐겨찾기 기반 추천 점수 =====================
        List<FairDto> wishlistList = wishlistRecommendStrategy.recommend(mno);
        for (FairDto fair : wishlistList) {
            if (fair != null && (excludedFno == null || !excludedFno.contains(fair.getFno()))) {
                // 점수 누적 (Wishlist-based Recommendation)
                scoreMap.put(fair.getFno(), scoreMap.getOrDefault(fair.getFno(), 0.0) + 20.0);
            }
        }

        // ===================== 5️⃣ 카테고리 기반 추천 점수 =====================
        List<FairDto> categoryList = categoryStrategy.recommend(mno);
        for (FairDto fair : categoryList) {
            if (fair != null && (excludedFno == null || !excludedFno.contains(fair.getFno()))) {
                // 점수 누적 (Category-based Recommendation)
                scoreMap.put(fair.getFno(), scoreMap.getOrDefault(fair.getFno(), 0.0) + 15.0);
            }
        }

        // ===================== 6️⃣ 최근 방문 기반 추천 점수 =====================
        List<Integer> recentFnoList = visitLogDao.getVisitFnoByMember(mno);
        for (Integer fno : recentFnoList) {
            if (fno != null && fairDao.getFairbyFno(fno) != null &&
                    (excludedFno == null || !excludedFno.contains(fno))) {
                // 점수 누적 (Recent Visit-based Recommendation)
                scoreMap.put(fno, scoreMap.getOrDefault(fno, 0.0) + 25.0);
            }
        }

        // ===================== 7️⃣ 지역 기반 추천 점수 =====================
        List<FairDto> allFairs = fairDao.selectAllFairs(); // 전체 박람회 조회
        for (FairDto fair : allFairs) {
            if (fair != null && !memberAddress.isEmpty() && fair.getFplace() != null &&
                    fair.getFplace().contains(memberAddress) &&
                    (excludedFno == null || !excludedFno.contains(fair.getFno()))) {
                // 점수 누적 (Location-based Recommendation)
                scoreMap.put(fair.getFno(), scoreMap.getOrDefault(fair.getFno(), 0.0) + 15.0);
            }
        }

        // ===================== 8️⃣ 종료 박람회 제거 =====================
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        scoreMap.keySet().removeIf(fno -> {
            FairDto fair = fairDao.getFairbyFno(fno);
            if (fair == null || fair.getEnd_date() == null) return true; // 데이터 없으면 제외
            try {
                LocalDate endDate = LocalDate.parse(fair.getEnd_date(), formatter);
                return !endDate.isAfter(LocalDate.now()); // 종료일 지난 박람회 제외
            } catch (Exception e) {
                return true; // 파싱 오류 시 제외
            }
        });

        // ===================== 9️⃣ 점수 기반 랜덤 추천 =====================
        double totalScore = scoreMap.values().stream().mapToDouble(Double::doubleValue).sum(); // 총 점수
        if (totalScore == 0) return new ArrayList<>(); // 후보 없으면 빈 리스트

        List<Integer> fnoPool = new ArrayList<>(scoreMap.keySet()); // 후보 풀
        List<FairDto> resultList = new ArrayList<>();
        Random random = new Random();

        while (resultList.size() < RECOMMEND_COUNT && !fnoPool.isEmpty()) {
            double r = random.nextDouble() * totalScore; // 랜덤값 생성
            double cumulative = 0.0;
            Integer selectedFno = null;

            // Weighted Random Selection (점수 기반 누적 합)
            for (Integer fno : fnoPool) {
                cumulative += scoreMap.get(fno);
                if (r <= cumulative) {
                    selectedFno = fno; // 선택 완료
                    break;
                }
            }

            if (selectedFno != null) {
                FairDto selectedFair = fairDao.getFairbyFno(selectedFno);
                if (selectedFair != null) {
                    resultList.add(selectedFair); // 최종 추천 리스트에 추가
                }
                totalScore -= scoreMap.get(selectedFno); // 선택된 점수 제거
                fnoPool.remove(selectedFno);              // 후보 풀에서 제거
            }
        }

        // ===================== 10️⃣ 부족 시 전체 박람회로 채움 =====================
        if (resultList.size() < RECOMMEND_COUNT) {
            for (FairDto fair : allFairs) {
                // 중복, 제외 항목 체크
                if (fair != null && (excludedFno == null || !excludedFno.contains(fair.getFno())) &&
                        !resultList.contains(fair)) {
                    resultList.add(fair); // Fallback Random Recommendation
                    if (resultList.size() == RECOMMEND_COUNT) break;
                }
            }
        }

        return resultList; // 최종 추천 리스트 반환
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


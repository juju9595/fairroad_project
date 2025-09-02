package web.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import web.model.dao.*;
import web.model.dto.FairDto;
import web.model.dto.MembersDto;
import web.service.strategy.CategoryRecommendStrategy;
import web.service.strategy.PopularRecommendStrategy;
import web.service.strategy.WishlistRecommendStrategy;

import java.util.*;

@Service
public class RecommendService {

    @Autowired CategoryRecommendStrategy categoryStrategy;
    @Autowired PopularRecommendStrategy popularRecommendStrategy;
    @Autowired WishlistRecommendStrategy wishlistRecommendStrategy;
    @Autowired
    VisitLogDao visitLogDao;
    @Autowired WishListDao wishListDao;
    @Autowired
    MemberDao memberDao;
    @Autowired FairDao fairDao;

    // 추천해줄 개수
    private static final int RECOMMEND_COUNT = 10;


    // 회원별 추천 박람회 리스트
    public List<FairDto> getRecommendations(int mno){

        // 회원 정보
        MembersDto member = memberDao.getMemberByMno(mno);
        String memberAddress = member != null ? member.getMaddress() : "";

        // 점수 맵 생성
        Map<Integer , Double> scoreMap = new HashMap<>();

        // 인기순 점수 : 10점
        List<FairDto> popularList = popularRecommendStrategy.recommend(mno);
        for(FairDto fair : popularList ){
            scoreMap.put(fair.getFno() , scoreMap.getOrDefault(fair , 0.0) + 10.0);
        } // for e

        // 즐겨찾기 기반 점수 : 20점
        List<FairDto> wishlistList = wishlistRecommendStrategy.recommend(mno);
        for(FairDto fair : wishlistList){
            scoreMap.put(fair.getFno() , scoreMap.getOrDefault(fair , 0.0) + 20.0);
        } // for e

        // 카테고리 기반 점수 : 15점
        List<FairDto> categoryList = categoryStrategy.recommend(mno);
        for(FairDto fair : categoryList){
            scoreMap.put(fair.getFno() , scoreMap.getOrDefault(fair , 0.0) + 15.0);
        } // for e

        // 최근 활동 기반 점수 : 25점
        List<Integer> recentFnoList = visitLogDao.getVisitFnoByMember(mno);
        for(Integer fno : recentFnoList){
            if(fno != null ){
                scoreMap.put(fno , scoreMap.getOrDefault(fno , 0.0) + 25.0);
            } // if e
        } // for e

        // 지역 기반 점수 : 15점
        List<FairDto> allFairs = fairDao.selectAllFairs(); // 모든 박람회 조회
        for(FairDto fair : allFairs){
            if(!memberAddress.isEmpty() && fair.getFplace() != null && fair.getFplace().contains(memberAddress)){
                scoreMap.put(fair.getFno() ,scoreMap.getOrDefault(fair.getFno() , 0.0) + 15.0);
            } //if e
        } // func e

        // 점수 합산 후 확률 계산
        double totalScore = scoreMap.values().stream().mapToDouble(Double::doubleValue).sum();
        if(totalScore == 0) return new ArrayList<>();

        List<Integer> fnoPool = new ArrayList<>(scoreMap.keySet());
        List<FairDto> resultList = new ArrayList<>();
        Random random = new Random();

        // 확률 기반 랜덤 추천
        while (resultList.size() < RECOMMEND_COUNT && !fnoPool.isEmpty()){
            double r = random.nextDouble() * totalScore;
            double cumulative = 0.0;
            Integer selectedFno = null;

            for (Integer fno : fnoPool){
                cumulative += scoreMap.get(fno);
                if ( r <= cumulative ){
                    selectedFno = fno;
                    break;
                } // if e
            } // for e

            if (selectedFno != null ){
                resultList.add(fairDao.getFairbyFno(selectedFno));
                totalScore -= scoreMap.get(selectedFno);
                fnoPool.remove(selectedFno);
            } // if e
        } // while e
        return resultList;
    } // func e

} // class e

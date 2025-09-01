package web.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import web.model.dao.FairDao;
import web.model.dao.VisitLogDao;
import web.model.dao.WishListDao;
import web.model.dto.FairDto;
import web.service.strategy.CategoryRecommendStrategy;
import web.service.strategy.PopularRecommendStrategy;
import web.service.strategy.RecommendStrategy;
import web.service.strategy.WishlistRecommendStrategy;

import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

@Service
public class RecommendService {
    @Autowired
    FairDao fairDao;
    @Autowired
    VisitLogDao visitLogDao;
    @Autowired
    WishListDao wishListDao;
    @Autowired
    private CategoryRecommendStrategy categoryStrategy;
    @Autowired
    private PopularRecommendStrategy popularRecommendStrategy;
    @Autowired WishlistRecommendStrategy wishlistRecommendStrategy;


    // 회원별 추천 박람회 리스트
    public List<FairDto> getRecommendations(int mno){

        // 인기순 추천
        List<FairDto> popularList = popularRecommendStrategy.recommend(mno);

        // 즐겨찾기 기반 추천
        List<FairDto> wishlistList = wishlistRecommendStrategy.recommend(mno);

        // 카테고리 기반 추천
        List<FairDto> categoryList = categoryStrategy.recommend(mno);

        // 전략 결과 합쳐서 반환
        Set<FairDto> combined = new java.util.LinkedHashSet<>(); // 순서 유지하면서 중복 제거
        combined.addAll(popularList);
        combined.addAll(wishlistList);
        combined.addAll(categoryList);

        return new java.util.ArrayList<>(combined);

    } // func e

} // class e

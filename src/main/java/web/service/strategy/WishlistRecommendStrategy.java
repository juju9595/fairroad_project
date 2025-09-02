package web.service.strategy;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import web.model.dao.FairDao;
import web.model.dao.WishListDao;
import web.model.dto.FairDto;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class WishlistRecommendStrategy implements RecommendStrategy{
    @Autowired
    WishListDao wishListDao;
    @Autowired
    FairDao fairDao;

    @Override
    public List<FairDto> recommend(int mno){
        // 회원 즐겨찾기한 fno 목록 조회
        List<Integer> wishFnoList = wishListDao.getWishFnoByMember(mno);

        // fno -> FairDto 변환 후 리스트 반환
        return wishFnoList.stream()
                .map(fairDao::getFairbyFno)
                .filter(f -> f != null)
                .collect(Collectors.toList());
    } // func e

} // class e

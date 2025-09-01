package web.service.strategy;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import web.model.dao.FairDao;
import web.model.dao.WishListDao;
import web.model.dto.FairDto;

import java.util.ArrayList;
import java.util.List;

@Service
public class WishlistRecommendStrategy implements RecommendStrategy{
    @Autowired
    WishListDao wishListDao;
    @Autowired
    FairDao fairDao;

    @Override
    public List<FairDto> recommend(int mno){
        List<Integer> wishFnoList = wishListDao.getWishFnoByMember(mno);

        List<FairDto> list = new ArrayList<>();
        for(int fno : wishFnoList){
            FairDto fair = fairDao.getFairbyFno(fno);
            if(fair != null )list.add(fair);
        }
        return list;
    } // func e


} // class e

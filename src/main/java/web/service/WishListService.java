package web.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import web.model.dao.WishListDao;
import web.model.dto.MemberWishListDto;
import web.model.dto.WishListDto;

import java.util.List;

@Service
public class WishListService {
    @Autowired
    WishListDao wishlistDao;

    // 회원별 즐겨찾기 목록 조회
    public MemberWishListDto memberWishList(int mno){
        List<WishListDto> wishList = wishlistDao.memberWishList(mno);
        MemberWishListDto result = new MemberWishListDto(mno , wishList);
        return result;
    } // func e

} // class e

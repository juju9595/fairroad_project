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

    // 즐겨 찾기 등록 [버튼]
    public int fairWishList(int mno,int fno){
        int result = wishlistDao.fairWishList(mno,fno);
        return result;
    }//func end

} // class e

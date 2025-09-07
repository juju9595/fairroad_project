package web.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import web.model.dao.WishListDao;
import web.model.dto.MemberWishListDto;
import web.model.dto.MemberWishListPageDto;
import web.model.dto.WishListDto;

import java.util.List;

@Service
public class WishListService {
    @Autowired
    WishListDao wishlistDao;

    // 회원별 즐겨찾기 목록 조회
    public MemberWishListPageDto getMemberWishListPage(int mno, int page, int count){
        List<WishListDto> list = wishlistDao.memberWishList(mno, page, count);
        int totalCount = wishlistDao.getTotalWishCount(mno);
        int totalPage = (int)Math.ceil((double)totalCount / count);

        return new MemberWishListPageDto(page, totalPage, totalCount, list);
    }

    // 즐겨 찾기 등록/취소 [버튼]
    public int fairWishToggle(int mno,int fno){
        int result = wishlistDao.fairWishToggle(mno,fno);
        return result;
    }//func end
} // class e

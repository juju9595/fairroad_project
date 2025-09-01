package web.controller;


import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import web.model.dto.MemberWishListDto;
import web.service.WishListService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/wish")
public class WishListController { // class start
    @Autowired
    private WishListService wishlistService;

    // 회원별 즐겨찾기 목록 조회
    @GetMapping("/member")
    public MemberWishListDto memberWishList(@RequestParam int mno){
        MemberWishListDto result = wishlistService.memberWishList(mno);
        return result;
    } // func e

} // class end

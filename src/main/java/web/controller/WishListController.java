package web.controller;


import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
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

    // 즐겨 찾기 등록 [버튼]
    @PostMapping("/write")
    public int fairWishList(@RequestBody int mno,int fno){
        int result = wishlistService.fairWishList(mno,fno);
        return result;
    }//func end

} // class end


package web.controller;


import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import web.model.dto.MemberWishListDto;
import web.model.dto.MemberWishListPageDto;
import web.model.dto.WishListDto;
import web.service.WishListService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/wish")
public class WishListController { // class start
    @Autowired
    private WishListService wishlistService;

    // 회원별 즐겨찾기 목록 조회
    @GetMapping("/member")
    public MemberWishListPageDto memberWishList(
            @RequestParam int mno,
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "6") int count
    ){
        return wishlistService.getMemberWishListPage(mno, page, count);
    }

    // 즐겨 찾기 등록 [버튼]
    @PostMapping("/write")
    public int fairWishToggle(@RequestParam int fno, HttpSession session){
        // 현재 로그인상태 확인
        Object login = session.getAttribute("loginMno");
        // 비로그인시 즐겨찾기 실패
        if(session.getAttribute("loginMno")==null)return 0;
        int mno = (int)login;
        return wishlistService.fairWishToggle(mno,fno);
    }//func end
} // class end


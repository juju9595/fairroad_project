package web.controller;


import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import web.model.dto.MembersDto;
import web.model.dto.WishListDto;
import web.service.MembersService;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/member")
public class MembersController { // class start

    @Autowired
    private MembersService membersService;

    // [1] 회원가입 구현
    @PostMapping("/signup")
    public int signUp(@RequestBody MembersDto membersDto){
        int result = membersService.signUp(membersDto);
        return  result;
    }//func e

//-----------------------------------------------------------------------------------------//

    // [2] 로그인 구현
    @PostMapping("/login")
    public int login(@RequestBody MembersDto membersDto, HttpServletRequest request){
        //1. 사용자 세션 정보 가져오기
        HttpSession session = request.getSession();
        //2. 사용자 로그인 성공한 회원번호 확인
        int result = membersService.login(membersDto);
        if(result > 0){
            //일반 사용자 세션 저장
            session.setAttribute("loginMno", result);

            // 회원번호 1=관리자 그외 사용자
            boolean loginAdmin = (result==1);
            //관리자 세션 저장
            session.setAttribute("loginAdmin",loginAdmin);
        }//if end
        return result;
    }//func e


//-----------------------------------------------------------------------------------------//

    // [3] 로그아웃 구현
    @GetMapping("/logout")
    public boolean logout(HttpServletRequest request){
        HttpSession session = request.getSession(false); // false로 해야 새 세션 생성 방지
        if(session == null || session.getAttribute("loginMno")==null){
            return false; // 비로그인 상태
        }
        session.removeAttribute("loginMno");
        return true;
    }


// -----------------------------------------------------------------------------------------//

    // [4] 회원정보 수정 접근권한
    @PostMapping("/checkinfo")
    public boolean signUpCheck(@RequestParam String mpwd, HttpSession session){
        if(session == null || session.getAttribute("loginMno") == null)return false;
        int loginMno = (int)session.getAttribute("loginMno");
        boolean result = membersService.signUpCheck(loginMno, mpwd);
        return result;
    }


// -----------------------------------------------------------------------------------------//

    // [5] 회원정보 수정

    @PutMapping("/update")
    public boolean update(@RequestBody MembersDto membersDto, HttpServletRequest request){
        HttpSession session = request.getSession();
        if(session == null || session.getAttribute("loginMno") == null ){
            return false;
        }
        Object obj = session.getAttribute("loginMno");
        int loginMno = (int)obj;
        membersDto.setMno(loginMno);
        boolean result = membersService.update(membersDto);
        return result;}

// -----------------------------------------------------------------------------------------//

    // [6] 비밀번호 수정
    @PutMapping("/update/password")
    public boolean pwdUpdate(@RequestBody Map<String, String> map, HttpServletRequest request){
        HttpSession session = request.getSession();
        if(session == null || session.getAttribute("loginMno") == null)return false;
        Object obj = session.getAttribute("loginMno");
        int loginMno = (int)obj;
        boolean result = membersService.pwdUpdate(loginMno, map);
        session.removeAttribute("loginMno"); // 세션 지우기 // 로그아웃
        return result;
    }

// -----------------------------------------------------------------------------------------//

    // [7] 즐겨찾기 목록
    @GetMapping("/wishlist")
    public List<WishListDto> wishList(HttpServletRequest request){

        HttpSession session = request.getSession();
        if(session == null || session.getAttribute("loginMno") == null)return null;
        Object obj = session.getAttribute("loginMno");
        int mno = (int)obj;



        List<WishListDto> result = membersService.wishList(mno);
        return result;
    }

// -----------------------------------------------------------------------------------------//

    // [8] 즐겨찾기 목록 삭제
    @DeleteMapping("/wishlist/delete")
    public boolean wishListDelete(@RequestParam int fno, HttpSession session ){
        Integer mno = (Integer) session.getAttribute("loginMno"); // 로그인 시 넣어둔 세션 키명
        if (mno == null) return false; // 미로그인 등
        boolean result = membersService.wishListDelete(mno, fno);
        return result;
    }//func e
//
//-----------------------------------------------------------------------------

    // [9] 회원번호로 회원 정보 조회
    @GetMapping("/info")
    public MembersDto info(HttpServletRequest request){
        HttpSession session = request.getSession();
        if(session == null || session.getAttribute("loginMno") == null){
            return null;
        }
        Object obj = session.getAttribute("loginMno");
        int loginMno = (int)obj;
        MembersDto result = membersService.info(loginMno);
        return result;
    }//func e

 //-----------------------------------------------------------------------------

    //[10] 아이디 찾기
    @GetMapping("/findid")
    public Map<String, String> findId(@RequestParam Map<String, String> map){
        return membersService.findId(map);
    }

//-----------------------------------------------------------------------------

    //[11] 비밀번호 찾기
    @GetMapping("/findpwd")
    public Map<String,String> findPwd(@RequestParam Map<String, String> map){
        return membersService.findPwd(map);
    }

    //-----------------------------------------------------------------------------

    //[12]회원탈퇴
    @DeleteMapping("/delete")
    public boolean memberdelete(HttpSession session){
        if(session == null || session.getAttribute("loginMno") == null)return false;
        int loginMno = (int)session.getAttribute("loginMno");
        boolean result = membersService.delete(loginMno);
        if(result == true) session.getAttribute("loginMno");
        return result;
    }

} // class e

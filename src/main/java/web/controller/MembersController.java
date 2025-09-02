package web.controller;


import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import web.model.dto.MembersDto;
import web.service.MembersService;

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
        //1. 세션 정보 가져오기
        HttpSession session = request.getSession();
        //2. 로그인 성공한 회원번호 확인
        int result = membersService.login(membersDto);
        if(result > 0){
            session.setAttribute("loginMno", result);
        }
        //
        return result;
    }//func e

} // class end

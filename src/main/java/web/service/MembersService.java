package web.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import web.model.dao.MemberDao;
import web.model.dao.WishListDao;
import web.model.dto.MembersDto;
import web.model.dto.WishListDto;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

@Service
public class MembersService {
    @Autowired
    private MemberDao memberDao;

    // [1] 회원가입
    public int signUp(MembersDto membersDto){
        int result = memberDao.signUp(membersDto);
        return result;
    }

//-----------------------------------------------------------------------------------------//

    //중복검사
    public boolean check(String type, String data){
        boolean result = memberDao.check(type, data);
        return result;
    }

//-----------------------------------------------------------------------------------------//

    // [2] 로그인
    public int login(MembersDto membersDto){
        int result = memberDao.login(membersDto);
        return result;
    }

// -----------------------------------------------------------------------------------------//

    // [3] 회원정보 수정 접근권한
    public boolean signUpCheck(int mno, String mpwd){
        boolean result = memberDao.signUpCheck(mno, mpwd);
        return result;
    }

// -----------------------------------------------------------------------------------------//

    // [4] 회원정보 수정
    public boolean update(MembersDto membersDto){
        boolean result = memberDao.update(membersDto);
        return result;
    }

// -----------------------------------------------------------------------------------------//

    // [5] 비밀번호 수정
    public boolean pwdUpdate(int mno, Map<String, String> map){
        boolean result = memberDao.pwdUpdate(mno, map);
        return result;
    }

// -----------------------------------------------------------------------------------------//

    // [6] 즐겨찾기 목록
    public List<WishListDto> wishList(int mno){
        List<WishListDto> result = memberDao.wishList(mno);
        return result;
    }

// -----------------------------------------------------------------------------------------//

    //[7] 즐겨찾기 목록 삭제
    public boolean wishListDelete(int mno, int fno){
        boolean result = memberDao.wishListDelete(mno, fno);
        return result;
    }
// -----------------------------------------------------------------------------------------//

    //[8] 내정보 조회
    public MembersDto info(int mno){
        MembersDto result = memberDao.info(mno);
        return result;
    }//func e

// -----------------------------------------------------------------------------------------//

    //[9] 아이디 찾기
    public Map<String,String> findId(Map<String,String> map){
        String result = memberDao.findId(map);
        Map<String, String> resultMap = new HashMap<>();
        if(result == null){
            resultMap.put("status", "fail");
            resultMap.put("msg" , "해당 정보로 가입된 회원이 없습니다.");
        }else {
            resultMap.put("msg", result);
        }return resultMap;
    }//func e

// -----------------------------------------------------------------------------------------//

    //[10] 비밀번호 찾기
    public Map<String, String > findPwd(Map<String, String> map){
    String mpwd = "";
    for(int i = 1; i<6; i++){
        Random random = new Random();
        mpwd += random.nextInt(10);
    }
    map.put("mpwd", mpwd);
    boolean result = memberDao.findPwd(map);
    Map<String, String> resultMap = new HashMap<>();
    if(result == true){
        resultMap.put("msg", mpwd);
    }else {resultMap.put("msg","회원정보없음");}
    //반환
        return resultMap;
    }

// -----------------------------------------------------------------------------------------//

    //[11] 회원탈퇴
    public boolean delete(int mno){
        boolean result = memberDao.delete(mno);
        return result;
    }
}//class e

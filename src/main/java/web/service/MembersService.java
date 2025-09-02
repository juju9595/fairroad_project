package web.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import web.model.dao.MemberDao;
import web.model.dto.MembersDto;
import web.model.dto.WishListDto;

import java.util.List;
import java.util.Map;

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

    // [2] 로그인
    public int login(MembersDto membersDto){
        int result = memberDao.login(membersDto);
        return result;
    }

// -----------------------------------------------------------------------------------------//

    // [4] 연락처 수정
    public boolean phoneUpdate(MembersDto membersDto){
        boolean result = memberDao.phoneUpdate(membersDto);
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
    public List<WishListDto> wishList(){
        List<WishListDto> result = memberDao.wishList();
        return result;
    }


}//class e

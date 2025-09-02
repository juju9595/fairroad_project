package web.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import web.model.dao.MemberDao;
import web.model.dto.MembersDto;

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


}//class e

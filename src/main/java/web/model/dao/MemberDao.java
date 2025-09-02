package web.model.dao;

import org.springframework.stereotype.Repository;
import web.model.dto.MembersDto;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

@Repository
public class MemberDao extends Dao{

    //회원가입
    public int signUp(MembersDto membersDto){
        try{
            String sql = "insert into members (mid, mpwd, mname, mbirth, mphone, memail, maddress) values(?,?,?,?,?,?,?)";
            PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, membersDto.getMid());
            ps.setString(2, membersDto.getMpwd());
            ps.setString(3, membersDto.getMname());
            ps.setString(4, membersDto.getMbirth());
            ps.setString(5, membersDto.getMphone());
            ps.setString(6, membersDto.getMemail());
            ps.setString(7, membersDto.getMaddress() );
            int count = ps.executeUpdate();
            if(count == 1){
                ResultSet rs = ps.getGeneratedKeys();
                if(rs.next()){
                    int mno = rs.getInt(1); // pk 값 가져오기
                    return mno; // 회원가입에 성공한 회원번호를 반환
                }
            }
        }catch(Exception e){
            System.out.println(e);
        }return 0; // 회원가입 실패시 0
    }//func e
//-----------------------------------------------------------------------------------------//

    //로그인
    public int login(MembersDto membersDto){
        try{
            String sql = "select *from members where mid =? and mpwd =?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1,membersDto.getMid());
            ps.setString(2,membersDto.getMpwd());
            ResultSet rs = ps.executeQuery();
            if(rs.next()){
                int mno = rs.getInt("mno");
                return mno; // 로그인 성공시 조회한 회원번호 반환
            }
        }catch (Exception e){
            System.out.println();
        }return 0;
    }//func e
    //-----------------------------------------------------------------------------------------//
    //
}//class e

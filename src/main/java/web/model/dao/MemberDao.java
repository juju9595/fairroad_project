package web.model.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import web.model.dto.MembersDto;
import web.model.dto.WishListDto;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Repository
public class MemberDao extends Dao{

    @Autowired private WishListDao wishListDao;

    // [1] 회원가입
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

    // [2] 로그인
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
    }//func


    // -----------------------------------------------------------------------------------------//

    //[3] 회원정보 수정 접근권한
    public boolean signUpCheck(int mno, String mpwd){
        try{
            String sql = "select mpwd from members where mno = ? and mpwd = ? limit 1";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, mno);
            ps.setString(2, mpwd);
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next(); // 한 행이라도 있으면 일치
            }
        }catch (Exception e){
            System.out.println(e);
        }return false;
    }



    // -----------------------------------------------------------------------------------------//

    // [4] 회원 정보 수정
    public boolean update(MembersDto membersDto){
        try{
            String sql = "update members set mphone=?, maddress=? where mno =?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, membersDto.getMphone());
            ps.setString(2, membersDto.getMaddress());
            ps.setInt(3,membersDto.getMno());
            int count = ps.executeUpdate();
            return count == 1;
        } catch (Exception e) {
            System.out.println(e);
        }return false;
    }


// -----------------------------------------------------------------------------------------//

    // [5] 비밀번호 수정
    public boolean pwdUpdate(int mno, Map<String, String> map){
        try{
            String sql = "update members set mpwd =? where mno=? and mpwd =?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, map.get("newpwd"));
            ps.setInt(2, mno);
            ps.setString(3, map.get("oldpwd"));
            int count = ps.executeUpdate();
            return count == 1;
        }catch (Exception e){
            System.out.println(e);
        }return false;
    }


// -----------------------------------------------------------------------------------------//

    // [6] 즐겨찾기 목록
    public List<WishListDto> wishList(int mno){
        List<WishListDto> list = new ArrayList<>();
        try{String sql = "select f.fno, f.fname from wishlist w join fair f on w.fno = f.fno where w.mno = ?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, mno);
            ResultSet rs = ps.executeQuery();
            while (rs.next()){
                WishListDto wishListDto = new WishListDto();
                wishListDto.setFno(rs.getInt("fno"));
                wishListDto.setFname(rs.getString("fname"));
                list.add(wishListDto);
            }
        }catch (Exception e) {System.out.println();}
        return list;
    }

// -----------------------------------------------------------------------------------------//

    //[7] 즐겨찾기 목록 삭제
    public boolean wishListDelete(int mno, int fno){
        try{
            String sql = "delete from wishlist where mno =? and fno = ?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1,mno);
            ps.setInt(2, fno);
            return ps.executeUpdate() == 1;
        } catch (Exception e) {
            System.out.println(e);
        }return false;
    }

    // ---------------------------- 추천 알고리즘 --------------------- //

    // [8] 회원번호로 회원 정보 조회
    public MembersDto info(int mno){
        try{
            String sql = "select * from members where mno = ?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, mno);
            ResultSet rs = ps.executeQuery();
            if(rs.next()){
                MembersDto member = new MembersDto();
                member.setMno(rs.getInt("mno"));
                member.setMid(rs.getString("mid"));
                member.setMpwd(rs.getString("mpwd"));
                member.setMname(rs.getString("mname"));
                member.setMbirth(rs.getString("mbirth"));
                member.setMphone(rs.getString("mphone"));
                member.setMemail(rs.getString("memail"));
                member.setMaddress(rs.getString("maddress"));
                return member;
            }
        } catch (Exception e){
            System.out.println(e);
        }
        return null; // 조회 실패
    }//func e

// -----------------------------------------------------------------------------------------//

    //[9] 아이디 찾기
    public String findId(Map<String, String> map){
        try{
            String sql = "select mid from members where mname =? and mphone=? ";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, map.get("mname"));
            ps.setString(2, map.get("mphone"));
            ResultSet rs = ps.executeQuery();
            if(rs.next())return rs.getString("mid");
        }catch (Exception e){
            System.out.println(e);
        }return null;
    }//func e

    //[10] 비밀번호 찾기
    public boolean findPwd(Map<String, String>map){
        try{
            String sql = "update members set mpwd = ? where mid = ? and mphone = ?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, map.get("mpwd"));
            ps.setString(2, map.get("mid"));
            ps.setString(3, map.get("mphone"));
            int count = ps.executeUpdate();
            return count == 1;
        }catch (Exception e){
            System.out.println(e);
            return false;
        }
    }//func e

    // [11] 회원탈퇴
    public boolean delete(int mno){
        try{
            String sql = "delete from members where mno=? ";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1,mno);
            return ps.executeUpdate() == 1;
        }catch (Exception e ){
            System.out.println(e);
        }return false;
    }



}//class e




package web.model.dao;

import org.springframework.stereotype.Repository;
import web.model.dto.WishListDto;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

@Repository
public class WishListDao extends Dao{

    // 회원별 즐겨찾기 목록 조회
    public List<WishListDto> memberWishList(int mno){
        List<WishListDto> list = new ArrayList<>();
        String sql = "select f.fno , f.fname from wishlist w join fair f on w.fno = f.fno where w.mno = ? ";
        try (PreparedStatement ps = conn.prepareStatement(sql)){ // PreparedStatement 블록 끝나면 자동 close
            ps.setInt(1, mno);
            try (ResultSet rs = ps.executeQuery()){ // ResultSet 블록 끝나면 자동 close
                while (rs.next()){
                    WishListDto dto = new WishListDto(rs.getInt("fno") , rs.getString("fname"));
                    list.add(dto);
                }
            }
        }catch (Exception e ){
            System.out.println(e);
        }
        return list;
    } // func e


} // class e

package web.model.dao;

import org.springframework.stereotype.Repository;
import web.model.dto.WishListDto;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

@Repository
public class WishListDao extends Dao{

    // 회원별 즐겨찾기 목록 조회 ( 박람회 상세정보 조회로 확장)
    public List<WishListDto> memberWishList(int mno){
        List<WishListDto> list = new ArrayList<>();
        String sql = "SELECT f.fno, f.fname, f.fimg, f.fplace, f.fprice, f.furl " +
                "FROM wishlist w " +
                "JOIN fair f ON w.fno = f.fno " +
                "WHERE w.mno = ?";

        try (PreparedStatement ps = conn.prepareStatement(sql)) { // PreparedStatement 블록 끝나면 자동 close
            ps.setInt(1, mno);

            try (ResultSet rs = ps.executeQuery()) { // ResultSet 블록 끝나면 자동 close
                while (rs.next()) {
                    WishListDto dto = new WishListDto(
                            rs.getInt("fno"),
                            rs.getString("fname"),
                            rs.getString("fimg"),
                            rs.getString("fplace"),
                            rs.getInt("fprice"),
                            rs.getString("furl")
                    );
                    list.add(dto);
                }
            }
        } catch (Exception e) {
            System.out.println(e);
        }

        return list;
    } // func e

    // ------------------------------ 추천 알고리즘 ----------------------- //

    // 회원 즐겨찾기 fno 목록 조회
    public List<Integer> getWishFnoByMember(int mno) {
        List<Integer> list = new ArrayList<>();
        String sql = "SELECT fno FROM wishlist WHERE mno = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, mno);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                list.add(rs.getInt("fno"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    } // func e

} // class e

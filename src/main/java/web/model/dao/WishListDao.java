package web.model.dao;

import org.springframework.boot.context.event.SpringApplicationEvent;
import org.springframework.stereotype.Repository;
import web.model.dto.WishListDto;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

@Repository
public class WishListDao extends Dao{

    // 회원별 즐겨찾기 목록 조회 (페이징 적용)
    public List<WishListDto> memberWishList(int mno, int page, int count){
        List<WishListDto> list = new ArrayList<>();
        String sql = "SELECT f.fno, f.fname, f.fimg, f.fplace, f.fprice, f.furl , f.start_date , f.end_date " +
                "FROM wishlist w " +
                "JOIN fair f ON w.fno = f.fno " +
                "WHERE w.mno = ? " +
                "ORDER BY w.fno DESC " +  // 최신 즐겨찾기 순서
                "LIMIT ? OFFSET ?";        // 페이징

        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, mno);
            ps.setInt(2, count);                 // 한 페이지당 갯수
            ps.setInt(3, (page - 1) * count);   // OFFSET 계산

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    WishListDto dto = new WishListDto(
                            rs.getInt("fno"),
                            rs.getString("fname"),
                            rs.getString("fimg"),
                            rs.getString("fplace"),
                            rs.getInt("fprice"),
                            rs.getString("furl"),
                            rs.getString("start_date"),
                            rs.getString("end_date")
                    );
                    list.add(dto);
                }
            }
        } catch (Exception e) {
            System.out.println(e);
        }
        return list;
    } // func e

    // 전체 즐겨찾기 수 조회 (페이징 버튼용)
    public int getTotalWishCount(int mno){
        String sql = "SELECT COUNT(*) FROM wishlist WHERE mno = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, mno);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) return rs.getInt(1);
            }
        } catch (Exception e) {
            System.out.println(e);
        }
        return 0;
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

    //-------------------------------------------------------------------------------------//

    // 즐겨 찾기 등록/취소 [버튼]
    public int fairWishToggle(int mno,int fno){
        try {
            String sqlCheck = "SELECT COUNT(*) FROM wishlist WHERE mno=? and fno=? ;";
            PreparedStatement psCheck = conn.prepareStatement(sqlCheck);
            psCheck.setInt(1,mno);
            psCheck.setInt(2,fno);
            ResultSet rs = psCheck.executeQuery();
            if(rs.next() && rs.getInt(1)>0){
                String sqlDelete = "DELETE FROM wishlist WHERE mno=? and fno =?;";
                PreparedStatement psDel = conn.prepareStatement(sqlDelete);
                psDel.setInt(1,mno);
                psDel.setInt(2,fno);
                psDel.executeUpdate();
                return -1;
            }else{
                String sqlWrite = "INSERT INTO wishlist(mno,fno) values(?,?);";
                PreparedStatement psAdd = conn.prepareStatement(sqlWrite, Statement.RETURN_GENERATED_KEYS);
                psAdd.setInt(1,mno);
                psAdd.setInt(2,fno);
                int count = psAdd.executeUpdate();
                if(count==1){
                    ResultSet rs2 = psAdd.getGeneratedKeys();
                    if(rs2.next()){
                        return rs2.getInt(1);
                    }//if end
                }//if end
            }//if end
        } catch (SQLException e) {System.out.println(e);}
        return 0;
    }//func end

    //즐겨 찾기 Active
    public boolean fairWishActive(int mno, int fno){
        try{
            String sql = "SELECT COUNT(*) FROM wishlist where mno=? and fno =?;";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1,mno);
            ps.setInt(2,fno);
            ResultSet rs = ps.executeQuery();
            if(rs.next()){
                int count = rs.getInt(1);
                return count > 0;
            }//if end
        } catch (Exception e){System.out.println(e);}//catch end
        return false;
    }//func end
} // class e


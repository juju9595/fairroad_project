package web.model.dao;

import org.springframework.stereotype.Repository;
import web.model.dto.ReviewDto;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

@Repository
public class ReviewDao extends Dao{    // [1] 방문 리뷰 등록
    public boolean reviewWrite( ReviewDto reviewDto ) {
        String sql = "INSERT INTO review ( rcontent, rdate ) VALUES ( ?, ? )";
        try {
            PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

            ps.setString(1, reviewDto.getRcontent());
            ps.setDate(2, Date.valueOf(reviewDto.getRdate()));

            int count = ps.executeUpdate();
            if (count == 1) {
                try (ResultSet rs = ps.getGeneratedKeys()) {
                    if (rs.next()) reviewDto.setRno(rs.getInt(1));
                }
                return true;
            }
        } catch ( Exception e ){
            System.out.println( e );
        }
        return false;
    } // func end


    //--------------------------------------------------------------------------------------------------//

    // [2] 방문 리뷰 전체 조회
    public List<ReviewDto> reviewPrint() {
        String sql = "SELECT rno, rcontent, rdate FROM review ORDER BY rno DESC";
        List<ReviewDto> list = new ArrayList<>();
        try {
            PreparedStatement ps = conn.prepareStatement( sql );
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                list.add(new ReviewDto(
                        rs.getInt("rno"),
                        rs.getString("rcontent"),
                        rs.getDate("rdate").toLocalDate()
                ));
            }
        } catch (Exception e){
            System.out.println( e );
        }
        return list;
    }


    //--------------------------------------------------------------------------------------------------//

    // [3] 방문 리뷰 개별 조회
    public ReviewDto reviewPrint2( int rno ) {
        String sql = "SELECT rno, rcontent, rdate FROM review WHERE rno = ?";
        try{
            PreparedStatement ps = conn.prepareStatement( sql );

            ps.setInt(1, rno);
            try (ResultSet rs = ps.executeQuery()) {
                if ( rs.next() ) {
                    return new ReviewDto(
                            rs.getInt("rno"),
                            rs.getString("rcontent"),
                            rs.getDate("rdate").toLocalDate()
                    );
                }
            }
        } catch (Exception e) { e.printStackTrace(); }
        return null;
    }




    //--------------------------------------------------------------------------------------------------//



    // [4] 방문 리뷰 수정
    public boolean reviewUpdate( int rno , String rcontent ){
        String sql = "UPDATE review SET rcontent = ? WHERE rno = ?";
        try{
            PreparedStatement ps = conn.prepareStatement( sql );
            ps.setString( 1 , rcontent );
            ps.setInt( 2 , rno );

            int count = ps.executeUpdate();
            if( count == 1 ){
                return true;
            }
        }catch ( Exception e ){
            System.out.println( e );
        }
        return false;
    }


    // [5] 방문 리뷰 삭제
    public boolean reviewDelete( int rno ) {
        String sql = "DELETE FROM review WHERE rno = ?";
        try{
            PreparedStatement ps = conn.prepareStatement( sql );

            ps.setInt(1, rno);
            int count = ps.executeUpdate();

            if( count == 1 ){
                return true;
            }
        } catch ( Exception e ){
            System.out.println( e );
        }
        return false;
    }

}

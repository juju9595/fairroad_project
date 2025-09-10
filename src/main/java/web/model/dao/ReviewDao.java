package web.model.dao;

import org.springframework.stereotype.Repository;
import web.model.dto.ReviewDto;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Repository
    public class ReviewDao extends Dao{
        // [1] 방문 리뷰 등록
        public int reviewWrite(ReviewDto reviewDto) {
            String sql = "INSERT INTO review ( mno, fno, rtitle , rcontent ) VALUES ( ? ,?, ?, ? )";
            try (PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
                ps.setInt(1, reviewDto.getMno());
                ps.setInt(2, reviewDto.getFno());
                ps.setString( 3,reviewDto.getRtitle());
                ps.setString(4, reviewDto.getRcontent());

                int count = ps.executeUpdate();
                if (count == 1) {
                    try (ResultSet rs = ps.getGeneratedKeys()) {
                        if (rs.next()) return rs.getInt(1);
                    }
                }
            } catch ( Exception e ){
                System.out.println( e );
            }
            return 0;
        } // func end


    //--------------------------------------------------------------------------------------------------//

//        // [2] 박람회별 조회
//        public List<ReviewDto> reviewPrint(int fno) {
//            String sql = "SELECT rno, mno, fno, rtitle, rcontent, rdate " +
//                    "FROM review WHERE fno = ? ORDER BY rno DESC";
//            List<ReviewDto> list = new ArrayList<>();
//
//            try (PreparedStatement ps = conn.prepareStatement(sql)) {
//                ps.setInt(1, fno);
//                try (ResultSet rs = ps.executeQuery()) {
//                    while (rs.next()) {
//                        ReviewDto dto = new ReviewDto();
//                        dto.setRno(rs.getInt("rno"));
//                        dto.setMno(rs.getInt("mno"));
//                        dto.setFno(rs.getInt("fno"));
//                        dto.setRtitle(rs.getString("rtitle"));
//                        dto.setRcontent(rs.getString("rcontent"));
//                        dto.setRdate(String.valueOf(rs.getDate("rdate")));
//
//                        list.add(dto);
//                    }
//                }
//            } catch (Exception e) {
//                System.out.println(e);
//            }
//            return list;
//        }

    //--------------------------------------------------------------------------------------------------//

        // [3] 방문 리뷰 개별 조회
        public ReviewDto reviewPrint2(int rno) {
            String sql = "SELECT rno, mno, fno, rtitle , rcontent, rdate FROM review WHERE rno = ?";
            try (PreparedStatement ps = conn.prepareStatement( sql )) {
                ps.setInt(1, rno);
                try (ResultSet rs = ps.executeQuery()) {
                    if (rs.next()) {
                        java.sql.Date d = rs.getDate("rdate");
                        java.time.LocalDate rdate = (d != null ? d.toLocalDate() : null);

                        ReviewDto dto = new ReviewDto();
                        dto.setRno(rs.getInt("rno"));
                        dto.setMno(rs.getInt("mno"));
                        dto.setFno(rs.getInt("fno"));
                        dto.setRtitle(rs.getString("rtitle"));
                        dto.setRcontent(rs.getString("rcontent"));
                        dto.setRdate(String.valueOf(rdate));
                        return dto;
                    }
                }
            } catch ( Exception e ){
                System.out.println( e );
            }
            return null;
        }


    //--------------------------------------------------------------------------------------------------//



        // [4] 방문 리뷰 수정
        public int reviewUpdate( int rno, int mno, String rtitle, String rcontent ) {
            String sql = "UPDATE review SET rtitle = ?, rcontent = ? WHERE rno = ? AND mno = ?";
            try { PreparedStatement ps = conn.prepareStatement( sql );
                ps.setString(1, rtitle);
                ps.setString(2, rcontent);
                ps.setInt(3, rno);
                ps.setInt(4, mno); // 작성자 회원번호 검증

                int count = ps.executeUpdate();
                if (count == 1) {
                    return rno; // 성공
                }
            } catch (Exception e) {
                System.out.println(e);
            }
            return 0;
        }


    // [5] 방문 리뷰 삭제 (fno 반환)
    public int reviewDelete(int rno, int mno) {
        String sqlSelect = "SELECT fno FROM review WHERE rno = ? AND mno = ?";
        String sqlDelete = "DELETE FROM review WHERE rno = ? AND mno = ?";
        try {
            // 먼저 fno 조회
            PreparedStatement ps = conn.prepareStatement(sqlSelect);
            ps.setInt(1, rno);
            ps.setInt(2, mno);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                int fno = rs.getInt("fno");

                // 삭제 실행
                PreparedStatement ps2 = conn.prepareStatement(sqlDelete);
                ps2.setInt(1, rno);
                ps2.setInt(2, mno);
                int count = ps2.executeUpdate();

                if (count == 1) return fno; // 성공 시 fno 반환
            }
        } catch (Exception e) {
            System.out.println(e);
        }
        return 0; // 실패 시 0 반환
    }



    // ------------------------------------------------------//
        // [6] 박람회별 게시물 수
        public int getTotalCount( int fno ){
            try{
                String sql = "select count(*) from review where fno = ? ";
                PreparedStatement ps = conn.prepareStatement( sql );
                ps.setInt( 1 , fno );
                ResultSet rs = ps.executeQuery();
                if( rs.next() ){
                    return rs.getInt( 1 );
                }
            }catch ( Exception e ){
                System.out.println( e );
            }
            return 0;
        }

        // [7] 게시물 전체 조회
        public List<ReviewDto> reviewPrint( int fno ){
            List< ReviewDto > list = new ArrayList<>();
            try{
                String sql = "SELECT * FROM review r " +
                        "INNER JOIN members m ON r.mno = m.mno " +
                        "WHERE r.fno = ? " +
                        "ORDER BY r.rno DESC";
                PreparedStatement ps = conn.prepareStatement( sql );
                ps.setInt( 1 , fno );
                ResultSet rs = ps.executeQuery();
                while ( rs.next() ){
                    ReviewDto reviewDto = new ReviewDto();
                    reviewDto.setMno( rs.getInt("mno" ) );
                    reviewDto.setFno( rs.getInt( "fno" ) );
                    reviewDto.setRno( rs.getInt( "rno" ) );
                    reviewDto.setRtitle( rs.getString( "rtitle" ) );
                    reviewDto.setRcontent( rs.getString( "rcontent" ) );
                    reviewDto.setRdate(String.valueOf(rs.getDate( "rdate" )));
                    list.add( reviewDto );
                }
            }catch ( Exception e ){
                System.out.println( e );
            }
            return list;
        }





}

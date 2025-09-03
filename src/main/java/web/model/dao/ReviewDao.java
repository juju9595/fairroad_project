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
    public class ReviewDao extends Dao{
        // [1] 방문 리뷰 등록
        public int reviewWrite(ReviewDto reviewDto) {
            String sql = "INSERT INTO review (mno, fno, rtitle , rcontent) VALUES ( ? ,?, ?, ? )";
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
            } catch (Exception e) { e.printStackTrace(); }
            return 0;
        } // func end


    //--------------------------------------------------------------------------------------------------//

        // [2] 방문 리뷰 전체 조회 (널/타입 안전 + 리소스 정리)
        public List<ReviewDto> reviewPrint() {
            String sql = "SELECT rno, mno, fno, rtitle, rcontent, rdate FROM review ORDER BY rno DESC";
            List<ReviewDto> list = new ArrayList<>();

            try (PreparedStatement ps = conn.prepareStatement(sql);
                 ResultSet rs = ps.executeQuery()) {

                while (rs.next()) {
                    java.time.LocalDate rdate = null;
                    try {
                        rdate = rs.getObject("rdate", java.time.LocalDate.class);
                    } catch (Throwable ignore) {
                        java.sql.Date d = rs.getDate("rdate");
                        if (d != null) rdate = d.toLocalDate();
                        else {
                            java.sql.Timestamp ts = rs.getTimestamp("rdate");
                            if (ts != null) rdate = ts.toLocalDateTime().toLocalDate();
                        }
                    }

                    ReviewDto dto = new ReviewDto();
                    dto.setRno(rs.getInt("rno"));
                    dto.setMno(rs.getInt("mno"));
                    dto.setFno(rs.getInt("fno"));
                    dto.setRtitle(rs.getString("rtitle"));
                    dto.setRcontent(rs.getString("rcontent"));
                    dto.setRdate(String.valueOf(rdate));               // (필드 타입이 LocalDate라면)

                    list.add(dto);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            return list;
        }


    //--------------------------------------------------------------------------------------------------//

        // [3] 방문 리뷰 개별 조회
        public ReviewDto reviewPrint2(int rno) {
            String sql = "SELECT rno, mno, fno, rtitle , rcontent, rdate FROM review WHERE rno = ?";
            try (PreparedStatement ps = conn.prepareStatement(sql)) {
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
            } catch (Exception e) { e.printStackTrace(); }
            return null;
        }


    //--------------------------------------------------------------------------------------------------//



    // [4] 방문 리뷰 수정
    public int reviewUpdate( int rno, String rtitle, String rcontent ) {
        String sql = "UPDATE review SET rtitle = ? , rcontent = ? WHERE rno = ?";
        try (PreparedStatement ps = conn.prepareStatement( sql )) {
            ps.setString(1, rtitle);
            ps.setString(2, rcontent);
            ps.setInt(3, rno);

            int count = ps.executeUpdate();
            if (count == 1) {
                return rno;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
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



    // ------------------------------------------------------//



}

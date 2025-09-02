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
        ReviewDto reviewDto = new ReviewDto();
        // [1] 방문 리뷰 등록
        public int reviewWrite( ReviewDto reviewDto ) {
            String sql = "INSERT INTO review ( rcontent, rdate ) VALUES ( ?, ? )";
            try {
                PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

                ps.setString(1, reviewDto.getRcontent());
                ps.setDate(2, Date.valueOf(reviewDto.getRdate()));

                int count = ps.executeUpdate();

                if( count == 1 ){
                    try (ResultSet rs = ps.getGeneratedKeys()) {
                        if (rs.next()) {
                            int rno = rs.getInt(1);   // 생성된 PK
                            reviewDto.setRno(rno);   // DTO에 세팅
                            return rno;              // PK 반환
                        }
                    }
                }

            } catch ( Exception e ){
                System.out.println( e );
            }
            return 0;
        } // func end


    //--------------------------------------------------------------------------------------------------//

        // [2] 방문 리뷰 전체 조회 (널/타입 안전 + 리소스 정리)
        public List<ReviewDto> reviewPrint() {
            String sql = "SELECT rno, rcontent, rdate FROM review ORDER BY rno DESC";
            List<ReviewDto> list = new ArrayList<>();

            try (PreparedStatement ps = conn.prepareStatement(sql);
                 ResultSet rs = ps.executeQuery()) {

                while (rs.next()) {
                    java.time.LocalDate rdate = null;

                    // 1) 드라이버가 LocalDate 매핑 지원하면 이게 제일 깔끔 (mysql-connector-j 8.x)
                    try {
                        rdate = rs.getObject("rdate", java.time.LocalDate.class);
                    } catch (Throwable ignore) {
                        // 2) 폴백: DATE 또는 TIMESTAMP/DATETIME 모두 처리
                        java.sql.Date d = rs.getDate("rdate");
                        if (d != null) rdate = d.toLocalDate();
                        else {
                            java.sql.Timestamp ts = rs.getTimestamp("rdate");
                            if (ts != null) rdate = ts.toLocalDateTime().toLocalDate();
                        }
                    }

                    list.add(new ReviewDto(
                            rs.getInt("rno"),
                            rs.getString("rcontent"),
                            rdate    // null 허용 (DTO가 LocalDate nullable이면 OK)
                    ));
                }
            } catch (Exception e) {
                e.printStackTrace(); // 원인 추적에 유리
            }
            return list;
        }


    //--------------------------------------------------------------------------------------------------//

        // [3] 방문 리뷰 개별 조회
        public ReviewDto reviewPrint2(int rno) {
            String sql = "SELECT rno, rcontent, rdate FROM review WHERE rno = ?";
            try (PreparedStatement ps = conn.prepareStatement(sql)) {
                ps.setInt(1, rno); // 매개변수로 받은 rno 바인딩

                try (ResultSet rs = ps.executeQuery()) {
                    if (rs.next()) {
                        java.sql.Date d = rs.getDate("rdate");
                        return new ReviewDto(
                                rs.getInt("rno"),            // ✅ 컬럼명 문자열
                                rs.getString("rcontent"),    // ✅ 컬럼명 문자열
                                (d != null ? d.toLocalDate() : null) // ✅ 널 안전
                        );
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }


    //--------------------------------------------------------------------------------------------------//



    // [4] 방문 리뷰 수정
    public int reviewUpdate( int rno , String rcontent ){
        String sql = "UPDATE review SET rcontent = ? WHERE rno = ?";
        try{
            PreparedStatement ps = conn.prepareStatement( sql );
            ps.setString( 1 , rcontent );
            ps.setInt( 2 , rno );

            int count = ps.executeUpdate();
            if( count == 1 ){
                return rno;
            }
        }catch ( Exception e ){
            System.out.println( e );
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

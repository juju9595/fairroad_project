package web.model.dao;

import org.springframework.stereotype.Repository;
import web.model.dto.VisitlogDto;

import java.sql.PreparedStatement;
import java.sql.Types;

@Repository
public class VisitlogDao extends Dao{

    // 방문 로그 저장
    public boolean insertVisitLog(VisitlogDto visitlogDto ){
        try {
            String sql = "insert into visitlog (mno , fno ) values ( ? , ? ) ";
            PreparedStatement ps = conn.prepareStatement(sql);

            if( visitlogDto.getMno() != null ) ps.setInt(1, visitlogDto.getMno()); // 회원 방문이면 회원번호 저장
            else ps.setNull(1, Types.INTEGER); // 아니면 null 저장 , 정수타입에 null 넣기 위한 SQL 타입명시

            ps.setInt(2, visitlogDto.getFno());

            int count = ps.executeUpdate();
            return count == 1;

        }catch (Exception e ){
            System.out.println(e);
        }
        return false;
    } // func e


} // claas e

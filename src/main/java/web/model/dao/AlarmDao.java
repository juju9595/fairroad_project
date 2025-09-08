package web.model.dao;

import org.springframework.stereotype.Repository;
import web.model.dto.AlarmDto;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;


@Repository
public class AlarmDao extends Dao{ // class start
    // 알림 저장
    public boolean insert(AlarmDto dto) {
        String sql = "insert into alarm (mno, fno, message) values (?, ?, ?)";
        try (
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, dto.getMno());
            ps.setInt(2, dto.getFno());
            ps.setString(3, dto.getMessage());

            return ps.executeUpdate() == 1;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    // 특정 회원 알림 조회
    public List<AlarmDto> findByMember(int mno) {
        List<AlarmDto> list = new ArrayList<>();
        String sql = "select * from alarm where mno=? order by created_at desc";

        try (
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, mno);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                AlarmDto dto = new AlarmDto();
                dto.setAno(rs.getInt("ano"));
                dto.setMno(rs.getInt("mno"));
                dto.setFno(rs.getInt("fno"));
                dto.setMessage(rs.getString("message"));
                dto.setCreatedAt(rs.getTimestamp("created_at").toLocalDateTime());
                list.add(dto);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

} // class end

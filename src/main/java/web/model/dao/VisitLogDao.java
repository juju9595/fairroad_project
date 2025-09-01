package web.model.dao;

import org.springframework.stereotype.Repository;
import web.model.dto.LastVisitDto;
import web.model.dto.VisitLogDto;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

@Repository
public class VisitLogDao extends Dao{

    // 방문 로그 저장
    public boolean insertVisitLog(VisitLogDto visitlogDto ){
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

    // 최근 본 박람회 조회
    public List<LastVisitDto> lastVisitList(int mno){
        List<LastVisitDto> list = new ArrayList<>();
        String sql = "select f.fno , f.fname , v.vdate from visitlog v " +
                "join fair f on v.fno = f.fno where v.mno = ? order by v.vdate desc limit 10 "; // 가장 최근으로 10개만 조회

        try(PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, mno);

            try(ResultSet rs = ps.executeQuery()) {
                while (rs.next()){
                    LastVisitDto dto = new LastVisitDto();
                    dto.setFno(rs.getInt("fno"));
                    dto.setFname(rs.getString("fname"));
                    dto.setVdate(rs.getString("vdate"));
                    list.add(dto);
                }

            }
        }catch (Exception e ){
            System.out.println(e);
        }
        return list;
    } // func e

    // --------------------------- 추천 알고리즘 ------------------------ //

    // 회원별 방문 로그 조회
    public List<Integer> getVisitFnoByMember(int mno){
        List<Integer> list = new ArrayList<>();
        String sql = "select fno from visitlog where mno = ? ";
        try(PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1,mno);
            ResultSet rs = ps.executeQuery();
            while (rs.next()){
                list.add(rs.getInt("fno"));
            }
        }catch (Exception e ){
            System.out.println(e);
        }
        return list;
    } // func e

    // 전체 방문수 기준 top 박람회
    public List<Integer> getTopVisitedFairs(int limit){
        List<Integer> list = new ArrayList<>();
        String sql = " select fno from visitlog group by fno order by count(*) desc limit ? ";
        try(PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1,limit);
            ResultSet rs = ps.executeQuery();
            while (rs.next()){
                list.add(rs.getInt("fno"));
            }
        }catch (Exception e ){
            System.out.println(e);
        }
        return list;
    } // func e

} // claas e

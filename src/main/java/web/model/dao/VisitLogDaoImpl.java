package web.model.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import web.model.dto.FairDto;
import web.model.dao.VisitLogDao;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class VisitLogDaoImpl implements VisitLogDao{

    /* 방문 로그 기록 저장소 DB 처리 시 있던 메소드

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
    */
    @Autowired
    FairDao fairDao;

    private final String CSV_PATH = "/visitlog.csv"; // CSV 파일 경로

    // 회원별 방문 fno 조회
    @Override
    public List<Integer> getVisitFnoByMember(int mno){

        List<Integer> list = new ArrayList<>();

        try(BufferedReader br = new BufferedReader(new FileReader(CSV_PATH))) {
            String line;
            br.readLine();

            while ((line = br.readLine()) != null){
                String[] parts = line.split(",");
                int loginMno = parts[1].isEmpty() ? -1 : Integer.parseInt(parts[1]);
                int fno = Integer.parseInt(parts[2]);
                if(loginMno == mno){
                    list.add(fno);
                }
            } // while e
        }catch (Exception e ){
            System.out.println(e);
        }
        return list;
    } // func e

    // 전체 방문수 기준 상위 10개 박람회 조회
    @Override
    public List<FairDto> getTopVisitedFairs(int n){
        Map<Integer , Integer> fnoCount = new HashMap<>();

        try(BufferedReader br = new BufferedReader(new FileReader(CSV_PATH))) {
            String line;
            br.readLine();

            while ((line = br.readLine()) != null ){
                String[] parts = line.split(",");
                int fno = Integer.parseInt(parts[2]);
                fnoCount.put(fno , fnoCount.getOrDefault(fno , 0) + 1 );
            } // while e

        }catch (Exception e){
            System.out.println(e);
        }

        // 방문수 기준 내림차순 정렬 후 상위 10개 fno 추출
        List<Integer> topFnoList = fnoCount.entrySet().stream()
                .sorted((e1 , e2) -> e2.getValue() - e1.getValue())
                .limit(10)
                .map(Map.Entry::getKey)
                .toList();

        // fno로 박람회 정보 가져오기
        List<FairDto> topFairs = new ArrayList<>();
        for( int fno : topFnoList){
            FairDto dto = fairDao.getFairbyFno(fno);
            if (dto != null ) topFairs.add(dto);
        } // for e

        return topFairs;
    } // func e



} // claas e

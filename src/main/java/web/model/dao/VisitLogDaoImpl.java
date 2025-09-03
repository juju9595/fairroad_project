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

    @Autowired
    FairDao fairDao;

    // private final String CSV_PATH = "logs/visitlog.csv"; // CSV 파일 경로

    // 오늘 날짜 기준  csv파일명

    private String getCsvPath() {
        String folder = "logs";
        java.time.format.DateTimeFormatter formatter = java.time.format.DateTimeFormatter.ofPattern("yyyy-MM-dd");
        String today = java.time.LocalDate.now().format(formatter);
        return folder + "/visitlog_" + today + ".csv";
    }

    // 회원별 방문 fno 조회
    @Override
    public List<Integer> getVisitFnoByMember(int mno){

        List<Integer> list = new ArrayList<>();

        try(BufferedReader br = new BufferedReader(new FileReader(getCsvPath()))) {
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

        try(BufferedReader br = new BufferedReader(new FileReader(getCsvPath()))) {
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

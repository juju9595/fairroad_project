package web.model.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import web.model.dto.FairDto;
import web.model.dto.VisitLogDto;

import java.io.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class VisitLogDaoImpl implements VisitLogDao {

    @Autowired
    FairDao fairDao;

    private int currentVno = 0;
    private final Object lock = new Object(); // 동기화 테스트용

    private String getCsvPath() {
        String folder = "static/logs"; // resources/static/logs
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        String today = LocalDate.now().format(formatter);
        return folder + "/visitlog_" + today + ".csv";
    }

    // CSV 저장 메소드 (멀티스레드 안전 + 헤더 처리)
    public void saveVisitLog(VisitLogDto log) {
        synchronized (lock) {
            try {
                File file = new File("src/main/resources/" + getCsvPath());
                boolean fileExists = file.exists();

                try (BufferedWriter bw = new BufferedWriter(new FileWriter(file, true))) {
                    // 새 파일일 경우 헤더 먼저 작성
                    if (!fileExists || file.length() == 0) {
                        bw.write("vno,mno,fno,vdate");
                        bw.newLine();
                    }

                    // vno 자동 증가
                    log.setVno(++currentVno);
                    // 현재 시간 적용
                    log.setVdate(java.time.LocalDateTime.now());

                    // 데이터 기록
                    bw.write(log.toCsv());
                    bw.newLine();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    // 회원별 방문 fno 조회
    @Override
    public List<Integer> getVisitFnoByMember(int mno) {
        List<Integer> list = new ArrayList<>();
        String resourcePath = getCsvPath();

        try (InputStream is = getClass().getClassLoader().getResourceAsStream(resourcePath)) {
            if (is == null) {
                System.out.println("파일 없음: " + resourcePath);
                return list;
            }
            BufferedReader br = new BufferedReader(new InputStreamReader(is));
            br.readLine(); // 헤더 건너뛰기

            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",");
                int loginMno = parts[1].isEmpty() ? -1 : Integer.parseInt(parts[1]);
                int fno = Integer.parseInt(parts[2]);
                if (loginMno == mno) {
                    list.add(fno);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }

    // 전체 방문수 기준 상위 n개 박람회 조회
    @Override
    public List<FairDto> getTopVisitedFairs(int n) {
        Map<Integer, Integer> fnoCount = new HashMap<>();
        String resourcePath = getCsvPath();

        try (InputStream is = getClass().getClassLoader().getResourceAsStream(resourcePath)) {
            if (is == null) {
                System.out.println("파일 없음: " + resourcePath);
                return new ArrayList<>();
            }
            BufferedReader br = new BufferedReader(new InputStreamReader(is));
            br.readLine(); // 헤더 건너뛰기

            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",");
                int fno = Integer.parseInt(parts[2]);
                fnoCount.put(fno, fnoCount.getOrDefault(fno, 0) + 1);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        // 방문수 기준 내림차순 정렬 후 상위 n개 fno 추출
        List<Integer> topFnoList = fnoCount.entrySet().stream()
                .sorted((e1, e2) -> e2.getValue() - e1.getValue())
                .limit(n)
                .map(Map.Entry::getKey)
                .toList();

        // fno로 박람회 정보 가져오기
        List<FairDto> topFairs = new ArrayList<>();
        for (int fno : topFnoList) {
            FairDto dto = fairDao.getFairbyFno(fno);
            if (dto != null) topFairs.add(dto);
        }

        return topFairs;
    }
}

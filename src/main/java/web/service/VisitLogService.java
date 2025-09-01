package web.service;

import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import web.model.dao.VisitLogDao;
import web.model.dto.LastVisitDto;
import web.model.dto.VisitLogDto;

import java.io.*;
import java.nio.Buffer;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

@Service
public class VisitLogService {
    @Autowired
    VisitLogDao visitlogDao;

    /* 방문 기록 DB 처리 시 썼던 코드
    // 방문 로그 저장
    public boolean insertVisitLog(VisitLogDto visitlogDto ){
        boolean result = visitlogDao.insertVisitLog(visitlogDto);
        return result;
    } // func e

    // 최근 본 박람회 조회
    public List<LastVisitDto> lastVisitList(int mno){
        List<LastVisitDto> result = visitlogDao.lastVisitList(mno);
        return result;
    } // func e
    */

    // --------------- CSV 파일 처리 할때 쓰는 코드 ---------------- //

    // vno 카운터
    private int currentVno = 0;

    @PostConstruct
    public void init() {
        // 서버 시작할 때 CSV에서 최대 vno 읽어서 currentVno 초기화
        List<VisitLogDto> logs = readAllLogs();
        if (!logs.isEmpty()) {
            currentVno = logs.stream()
                    .mapToInt(VisitLogDto::getVno)
                    .max()
                    .orElse(0);
        }
    } // func e

    private synchronized int getNextVno() {
        return ++currentVno;
    }


    private String getFileName() {
        return "visitlog_" + LocalDate.now().format(dateFormatter) + ".csv";
    }
    private static final DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    private final Object fileLock = new Object();
    private AtomicInteger vnoGenerator = new AtomicInteger(1); // vno 자동 증가

    // 방문 로그 비동기
    public void addVisitLogAsync(VisitLogDto log){
        // vno 자동 증가 (전역 AtomicInteger 사용)
        log.setVno(vnoGenerator.getAndIncrement());

        addVisitLog(log);
    }

    private void addVisitLog(VisitLogDto log){
        synchronized (fileLock){
            try(BufferedWriter bw = new BufferedWriter(new FileWriter(getFileName() , true))) {
                File file = new File(getFileName());
                if(file.length() == 0){
                    bw.write("vno, mno, fno , vdate");
                    bw.newLine();
                    // vno 자동생성
                    log.setVno(getNextVno());
                }
                bw.write(log.toCsv());
                bw.newLine();
            }catch (Exception e ){
                System.out.println(e);
            }
        } // syn e
    } // func e

    // CSV 전체 읽기
    private List<VisitLogDto> readAllLogs(){
        List<VisitLogDto> logs = new ArrayList<>();
        File file = new File(getFileName());
        if(!file.exists()) return logs;

        synchronized (fileLock){ // 읽는 동안 쓰기 충돌 방지
            try(BufferedReader br = new BufferedReader(new FileReader(file))) {
                br.lines().skip(1)
                        .map(VisitLogDto::fromCsv)
                        .forEach(logs::add);

            }catch (Exception e ){
                System.out.println(e);
            }
        } // syn e
        return logs;
    } // func e

    // 회원별 방문 로그 조회
    public List<VisitLogDto> getLogsByMember(int mno){
        return readAllLogs().stream()
                .filter(log -> log.getMno() == mno)
                .sorted(Comparator.comparing(VisitLogDto::getVdate).reversed())
                .collect(Collectors.toList());
    } // func e

    // 전체 방문수 기준 박람회 조회
    public Map<Integer , Long> getVisitCountByFair(){
        return readAllLogs().stream()
                .collect(Collectors.groupingBy(VisitLogDto::getFno , Collectors.counting()));
    } // func e

    // 회원별 최근 본 박람회
    public Optional<VisitLogDto> getRecentVisitByMember(int mno){
        return readAllLogs().stream()
                .filter(logs -> logs.getMno() == mno)
                .max(Comparator.comparing(VisitLogDto::getVdate));
    } // func e

    // 하루 단위 아카이브 (매일 자정 실행)
    @Scheduled(cron = "0 0 0 * * ?")
    public void archiveDailyLogsScheduled(){
        archiveDailyLogs();
    }

    public void archiveDailyLogs(){
        List<VisitLogDto> logs = readAllLogs();
        if(logs.isEmpty()) return;

        Map<String , List<VisitLogDto>> byDate = logs.stream()
                .collect(Collectors.groupingBy(log -> log.getVdate().toLocalDate().format(dateFormatter)));

        for(String date : byDate.keySet()){
            String archiveDir = "logs/" + date;
            new File(archiveDir).mkdirs();
            String archiveFile = archiveDir + "/visitlog.csv";

            synchronized (fileLock){ // 아카이브 중에도 동시 접근 안전
                try (BufferedWriter bw = new BufferedWriter(new FileWriter(archiveFile))){
                    bw.write("vno , mno , fno , vdate");
                    bw.newLine();
                    for(VisitLogDto log : byDate.get(date)){
                        bw.write(log.toCsv());
                        bw.newLine();
                    } // for2 e

                }catch (Exception e ){
                    System.out.println(e);
                }
            } // syn e
            System.out.println("아카이브 완료 : " + LocalDate.now());
        } // for1 e
    } // func e


} // class e




package web.service;

import jakarta.annotation.PostConstruct;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import web.model.dto.VisitLogDto;

import java.io.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

@Service
public class VisitLogService {

    private static final DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    private final Object fileLock = new Object(); // 파일 잠금 객체 생성

    // vno 자동 증가 , AtomicInteger : 멀티스레드 환경에서 숫자를 안전하게 증가, 감소, 읽기 할 수 있는 클래스
    private AtomicInteger vnoGenerator;

    @PostConstruct // 빈 생성 후 , 의존성 주입 완료 후 자동 실행
    // 마지막 vno(방문 기록 번호) 값 읽어오는 메소드
    public void init() {
        int lastVno = 0;

        // 오늘 파일 기준 최대 vno 확인
        File todayFile = new File(getFileName());
        if (todayFile.exists()) {
            lastVno = getMaxVnoFromFile(todayFile);
        } else {
            // 오늘 파일 없으면 이전 파일 기준 최대 vno 확인
            File logDir = new File(".");
            File[] files = logDir.listFiles((dir, name) -> name.startsWith("visitlog_") && name.endsWith(".csv"));
            if (files != null && files.length > 0) {
                File lastFile = Arrays.stream(files)
                        .sorted(Comparator.comparing(File::getName))
                        .reduce((first, second) -> second)
                        .get();
                lastVno = getMaxVnoFromFile(lastFile);
            }
        }

        // AtomicInteger 초기화
        vnoGenerator = new AtomicInteger(lastVno + 1); // 숫자 안전하게 읽기 -> 증가 -> 쓰기 충돌없이 안전하게 증가
        System.out.println("VisitLogService initialized. Last vno = " + lastVno);
    } // func e

    // csv에 기록된 방문 로그 중 가장 큰 번호 찾아오는 메소드
    private int getMaxVnoFromFile(File file) {
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {    // 파일읽기
            return br.lines()                                                   // 파일의 모든 줄 스트림으로 가져오기
                    .skip(1)                                                 // 헤더(첫줄) 건너뜀
                    .map(line -> line.split(",")[0].trim())         // 각 줄에서 첫번째 컬럼(vno) 추출
                    .mapToInt(Integer::parseInt)                                // 문자열 숫자로 변환
                    .max()
                    .orElse(0);                                            // 최대값 반환 , 파일 비어있거나 오류나면 0 반환
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    } // func e

    // 파일명 현재 날짜로 저장 메소드
    private String getFileName() {
        return "visitlog_" + LocalDate.now().format(dateFormatter) + ".csv";
    } // func e

    // 방문 로그 비동기 저장
    @Async // 비동기 환경 설정
    public void addVisitLogAsync(VisitLogDto log) {
        log.setVno(vnoGenerator.getAndIncrement());
        addVisitLog(log);
    } // func e


    // 방문기록 csv 파일에 저장 메소드
    private void addVisitLog(VisitLogDto log) {

        synchronized (fileLock) { // 동기화 블럭 시작!!
            // 여러 스레드 동시 접근 가능하지만 csv 접근해서 파일에 저장할 때는 하나의 스레드만 실행되게 Lock!!
            try (BufferedWriter bw = new BufferedWriter(new FileWriter(getFileName(), true))) {
                File file = new File(getFileName());
                if (file.length() == 0) { // 파일 길이 확인 , 비어있으면 새로 작성 후 저장
                    // 헤더 작성
                    bw.write("vno, mno, fno, vdate");
                    bw.newLine();
                }
                bw.write(log.toCsv()); // Dto를 csv 문자열로 변환 후 기록
                bw.newLine(); // 한줄씩 기록
            } catch (Exception e) {
                e.printStackTrace();
            }
        } // syn
    } // func e

    // CSV 전체 읽기
    private List<VisitLogDto> readAllLogs() {
        List<VisitLogDto> logs = new ArrayList<>();
        File file = new File(getFileName()); // 오늘 날짜 기준 파일 가져오기
        if (!file.exists()) return logs; // 파일 없으면 빈 list 반환

        synchronized (fileLock) { // 동기화 블럭 시작!!
            // 파일 읽을 때도 다른 스레드가 동시에 파일 쓰면 오류 발생 가능
            try (BufferedReader br = new BufferedReader(new FileReader(file))) {
                br.lines() // 파일 라인 읽기
                        .skip(1) // 헤더 스킵
                        .map(VisitLogDto::fromCsv) // csv 파일 dto 객체로 변환
                        .forEach(logs::add); // 리스트에 추가
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return logs; // 리스트 반환
    } // func e

    // 회원별 방문 로그 조회
    public List<VisitLogDto> getLogsByMember(int mno) {
        return readAllLogs().stream() // csv 파일 전체 리스트를 스트림으로 변환 후 읽기
                .filter(log -> log.getMno() != null && log.getMno().equals(mno))
                // 회원 필터링 : 비회원 기록 제외 , 조회할 회원번호와 일치하는 로그만 선택
                .sorted(Comparator.comparing(VisitLogDto::getVdate).reversed())
                // 방문일 기준 내림차순 정렬 , 최근 방문기록 우선
                .collect(Collectors.toList());
                // 스트림 결과 list<VisitLogDto> 형태로 반환
    } // func e

    // 전체 방문수 기준 박람회 조회
    public Map<Integer, Long> getVisitCountByFair() {
        return readAllLogs().stream() // 스트림으로 변환 후 읽기
                .collect(Collectors.groupingBy(VisitLogDto::getFno, Collectors.counting()));
                // { 박람회 번호 = 방문횟수 } 그룹화 해서 Map 반환
    } // func e

    // 회원별 최근 본 박람회 최대 10개
    public List<VisitLogDto> getRecentVisitsByMember(int mno) {
        return readAllLogs().stream()
                .filter(log -> log.getMno() != null && log.getMno() == mno) // 해당 회원만
                .sorted(Comparator.comparing(VisitLogDto::getVdate).reversed()) // 최근 날짜 순 정렬
                .limit(10) // 최대 10개만 가져오기
                .collect(Collectors.toList()); // List로 반환
    } // func e

    // 하루 단위 아카이브 (매일 자정 실행)
    @Scheduled(cron = "0 0 0 * * ?")
    public void archiveDailyLogsScheduled() {
        archiveDailyLogs();
    } // func e

    // 날짜별로 csv 파일 백업 메소드 (매일 자정 실행)
    public void archiveDailyLogs() {
        List<VisitLogDto> logs = readAllLogs();
        if (logs.isEmpty()) return; // 로그 비어있으면 함수 종료

        Map<String, List<VisitLogDto>> byDate = logs.stream()
                .collect(Collectors.groupingBy(log -> log.getVdate().toLocalDate().format(dateFormatter)));
        // 로그 순회하면서 방문날짜 기준으로 그룹화
        // LocalDate(yyyy-MM-dd) 형태로 추출
        // format() : 문자열로 반환

        // 날짜별 디렉토리 생성
        for (String date : byDate.keySet()) {
            String archiveDir = "logs/" + date;
            new File(archiveDir).mkdirs();
            String archiveFile = archiveDir + "/visitlog.csv";

            synchronized (fileLock) { // 동기화 블럭 시작!!
                try (BufferedWriter bw = new BufferedWriter(new FileWriter(archiveFile))) {
                    bw.write("vno, mno, fno, vdate");
                    bw.newLine();
                    for (VisitLogDto log : byDate.get(date)) {
                        bw.write(log.toCsv());
                        bw.newLine();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } // syn e
            System.out.println("아카이브 완료 : " + date);
        } // for e
    } // func e

} // class e

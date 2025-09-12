package web.service;

import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import web.model.dao.FairDao;
import web.model.dto.FairDto;
import web.model.dto.VisitLogDto;

import java.io.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

@Service
public class VisitLogService {
    @Autowired
    FairDao fairDao;

    private static final DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    private final Object fileLock = new Object(); // 파일 잠금 객체 생성

    // ✅ 공통 로그 디렉토리 (개발 환경: resources/static/logs)
    // 운영 배포 시에는 아래 경로를 user.dir 기준으로 바꾸는 게 안전합니다.
    private static final String LOG_DIR = "src/main/resources/static/logs";

    // vno 자동 증가
    private AtomicInteger vnoGenerator;

    @PostConstruct
    public void init() {
        int lastVno = 0;

        // 오늘 파일 기준 최대 vno 확인
        File todayFile = new File(getFileName());
        if (todayFile.exists()) {
            lastVno = getMaxVnoFromFile(todayFile);
        } else {
            // 오늘 파일 없으면 이전 파일 기준 최대 vno 확인
            File logDir = new File(LOG_DIR);
            File[] files = logDir.listFiles((dir, name) -> name.startsWith("visitlog_") && name.endsWith(".csv"));
            if (files != null && files.length > 0) {
                File lastFile = Arrays.stream(files)
                        .sorted(Comparator.comparing(File::getName))
                        .reduce((first, second) -> second)
                        .get();
                lastVno = getMaxVnoFromFile(lastFile);
            }
        }

        vnoGenerator = new AtomicInteger(lastVno + 1);
        System.out.println("VisitLogService initialized. Last vno = " + lastVno);
    }

    private int getMaxVnoFromFile(File file) {
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            return br.lines()
                    .skip(1)
                    .map(line -> line.split(",")[0].trim())
                    .mapToInt(Integer::parseInt)
                    .max()
                    .orElse(0);
        } catch (Exception e) {
            // e.printStackTrace();
            return 0;
        }
    }

    // ✅ 파일명 현재 날짜 기준으로 생성
    private String getFileName() {
        File dir = new File(LOG_DIR);
        if (!dir.exists()) {
            dir.mkdirs();
        }
        return LOG_DIR + File.separator + "visitlog_"
                + LocalDate.now().format(dateFormatter) + ".csv";
    }

    @Async
    public void addVisitLogAsync(VisitLogDto log) {
        log.setVno(vnoGenerator.getAndIncrement());
        addVisitLog(log);
    }

    private void addVisitLog(VisitLogDto log) {
        synchronized (fileLock) {
            try (BufferedWriter bw = new BufferedWriter(new FileWriter(getFileName(), true))) {
                File file = new File(getFileName());
                if (file.length() == 0) {
                    bw.write("vno,mno,fno,vdate");
                    bw.newLine();
                }
                bw.write(log.toCsv());
                bw.newLine();
            } catch (Exception e) {
                // e.printStackTrace();
            }
        }
    }

    // ✅ 공통 LOG_DIR 사용
    private List<VisitLogDto> readAllLogs() {
        List<VisitLogDto> logs = new ArrayList<>();
        File logDir = new File(LOG_DIR);
        if (!logDir.exists()) return logs;

        File[] files = logDir.listFiles((dir, name) -> name.startsWith("visitlog_") && name.endsWith(".csv"));
        if (files == null || files.length == 0) return logs;

        Arrays.sort(files, Comparator.comparing(File::getName));

        for (File file : files) {
            synchronized (fileLock) {
                try (BufferedReader br = new BufferedReader(new FileReader(file))) {
                    br.lines()
                            .skip(1)
                            .map(VisitLogDto::fromCsv)
                            .forEach(logs::add);
                } catch (Exception e) {
                    // e.printStackTrace();
                }
            }
        }
        return logs;
    }

    public List<VisitLogDto> getLogsByMember(int mno) {
        return readAllLogs().stream()
                .filter(log -> log.getMno() != null && log.getMno().equals(mno))
                .sorted(Comparator.comparing(VisitLogDto::getVdate).reversed())
                .collect(Collectors.toList());
    }

    public Map<Integer, Long> getVisitCountByFair() {
        return readAllLogs().stream()
                .collect(Collectors.groupingBy(VisitLogDto::getFno, Collectors.counting()));
    }

    public Map<String, Object> getRecentVisitsWithName(int mno, int page, int count) {
        List<Map<String, Object>> allLogs = readAllLogs().stream()
                .filter(log -> log.getMno() != null && log.getMno() == mno)
                .sorted(Comparator.comparing(VisitLogDto::getVdate).reversed())
                .map(log -> {
                    FairDto fair = fairDao.getFairbyFno(log.getFno());
                    if (fair == null) return null;
                    Map<String, Object> map = new HashMap<>();
                    map.put("vdate", log.getVdate());
                    map.put("fno", fair.getFno());
                    map.put("fname", fair.getFname());
                    map.put("fplace", fair.getFplace());
                    map.put("fprice", fair.getFprice());
                    map.put("furl", fair.getFurl());
                    map.put("fimg", fair.getFimg());
                    map.put("start_date", fair.getStart_date());
                    map.put("end_date", fair.getEnd_date());
                    return map;
                })
                .filter(Objects::nonNull)
                .toList();

        int total = allLogs.size();
        int fromIndex = Math.min((page - 1) * count, total);
        int toIndex = Math.min(page * count, total);

        List<Map<String, Object>> pagedLogs = allLogs.subList(fromIndex, toIndex);

        Map<String, Object> result = new HashMap<>();
        result.put("totalCount", total);
        result.put("lastvisitfair", pagedLogs);

        return result;
    }

    @Scheduled(cron = "0 0 0 * * ?")
    public void archiveDailyLogsScheduled() {
        archiveDailyLogs();
    }

    // ✅ archive도 LOG_DIR 사용
    public void archiveDailyLogs() {
        List<VisitLogDto> logs = readAllLogs();

        if (logs.isEmpty()) {
            System.out.println("archiveDailyLogs 종료: 로그가 비어있음");
            return;
        }

        String baseDir = LOG_DIR; // ← 공통 경로 사용
        System.out.println("baseDir 절대 경로: " + new File(baseDir).getAbsolutePath());

        Map<String, List<VisitLogDto>> byDate = logs.stream()
                .filter(log -> log.getVdate() != null)
                .collect(Collectors.groupingBy(log -> log.getVdate().toLocalDate().format(dateFormatter)));

        for (String date : byDate.keySet()) {
            String archiveDir = baseDir + File.separator + date;
            File dir = new File(archiveDir);
            if (!dir.exists()) {
                boolean dirCreated = dir.mkdirs();
                System.out.println("디렉토리 생성: " + archiveDir + " -> " + dirCreated);
            }

            String archiveFile = archiveDir + File.separator + "visitlog.csv";
            synchronized (fileLock) {
                try (BufferedWriter bw = new BufferedWriter(new FileWriter(archiveFile))) {
                    bw.write("vno,mno,fno,vdate");
                    bw.newLine();

                    for (VisitLogDto log : byDate.get(date)) {
                        bw.write(log.toCsv());
                        bw.newLine();
                    }
                    bw.flush();
                } catch (Exception e) {
                    // e.printStackTrace();
                }
            }
            System.out.println("아카이브 완료: " + date + ", 로그 수 = " + byDate.get(date).size());
        }
    }

}

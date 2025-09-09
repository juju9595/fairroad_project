package web.websocket;

// 스케줄링 기능을 사용하기 위해 @Scheduled를 import
import org.springframework.scheduling.annotation.Scheduled;
// 스프링 빈으로 등록하기 위해 @Component 사용
import org.springframework.stereotype.Component;
// WebSocket을 통해 실시간 알림을 보내는 핸들러
import web.handler.AlarmSocketHandler;
// DB 연결을 위한 기본 Dao 상속 (conn 사용 가능)
import web.model.dao.Dao;
// 알림을 DB에 저장하는 서비스
import web.service.AlarmService;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

// [1] 스프링 컴포넌트로 등록되는 클래스 (스케줄링 대상)
@Component
public class FairNotificationScheduler extends Dao {

    // 알림 저장 서비스
    private final AlarmService alarmService;
    // 웹소켓 핸들러 (실시간 전송)
    private final AlarmSocketHandler socketHandler;

    // [2] 생성자 주입 방식으로 서비스/핸들러 받아옴
    public FairNotificationScheduler (AlarmService alarmService,
                                      AlarmSocketHandler socketHandler){
        this.alarmService = alarmService;   // DB 저장용
        this.socketHandler = socketHandler; // 웹소켓 전송용
    }
    //
    // [3] 3분마다 실행되는 스케줄러
    // cron = "0 */3 * * * *"
    // 초(0), 분(3분마다), 시, 일, 월, 요일
    @Scheduled(cron = "0 */1 * * * *")
    public void notifyUpcomingFairs() {

        // [4] 위시리스트 + 박람회 테이블 조인
        // 시작일이 오늘 기준 3일 후인 박람회 조회
        String sql =
                "select w.mno, f.fno, f.fname, f.start_date " +
                        "from wishlist w " +
                        "join fair f on w.fno = f.fno " +
                        "where f.start_date = date_add(curdate(), interval 3 day)";

        try (
                // [5] SQL 준비
                PreparedStatement ps = conn.prepareStatement(sql);
                // [6] SQL 실행 → 결과 집합(ResultSet) 반환
                ResultSet rs = ps.executeQuery()) {

            // [7] 결과 집합 반복 처리
            while (rs.next()) {
                int mno = rs.getInt("mno");            // 회원 번호
                int fno = rs.getInt("fno");            // 박람회 번호
                String fname = rs.getString("fname");  // 박람회 이름
                String startDate = rs.getDate("start_date").toString(); // 시작일

                // [8] 알림 메시지 포맷팅
                String msg = String.format("📢 '%s' 박람회가 %s에 열립니다!", fname, startDate);

                // [9] DB에 알림 저장 (알람 이력 남김)
                alarmService.createAlarm(mno, fno, msg);

                // [10] WebSocket으로 해당 회원에게 실시간 알림 전송
                socketHandler.sendMessageToUser(mno, msg);
            }
        } catch (Exception e) {
            // [11] 예외 발생 시 로그 출력
            System.out.println("notifyUpcomingFairs 오류: " + e);
        }
    }
}
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


    public void notifyUpcomingFairsForUser(int mno) {
        String sql =
                "select w.mno, f.fno, f.fname, f.start_date " +
                        "from wishlist w " +
                        "join fair f on w.fno = f.fno " +
                        "where w.mno = ? and f.start_date = date_add(curdate(), interval 3 day)";

        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, mno);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    int fno = rs.getInt("fno");
                    String fname = rs.getString("fname");
                    String startDate = rs.getDate("start_date").toString();

                    String msg = String.format("📢 '%s' 박람회가 %s에 열립니다!", fname, startDate);

                    alarmService.createAlarm( mno, fno, msg );
                    socketHandler.sendMessageToUser( mno, msg );
                }
            }
        } catch (Exception e) {
            System.out.println("notifyUpcomingFairsForUser 오류: " + e);
        }
    }
}



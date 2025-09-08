package web.handler;

import org.springframework.stereotype.Component;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;


@Component
public class AlarmSocketHandler extends TextWebSocketHandler {

    // 회원별 WebSocket 세션을 저장하는 Map (Key: 회원번호, Value: 세션)
    private Map<Integer, WebSocketSession> userSessions = new ConcurrentHashMap<>();

    @Override
    public void afterConnectionEstablished(WebSocketSession session) {
        try {
            // URL 쿼리 파라미터에서 mno 추출 (예: ws://localhost:8080/alarm?mno=3 → "mno=3")
            String query = session.getUri().getQuery(); // 쿼리 문자열 가져오기
            Integer mno = null; // 회원 번호를 저장할 변수

            // 쿼리 문자열이 "mno=" 로 시작하면 숫자 부분을 잘라서 int 로 변환
            if (query != null && query.startsWith("mno=")) {
                mno = Integer.parseInt(query.split("=")[1]);
            }

            // mno 값이 정상적으로 추출된 경우 세션 Map 에 등록
            if (mno != null) {
                userSessions.put(mno, session); // 회원번호 → 세션 저장
                System.out.println("WebSocket 연결 성공: mno=" + mno); // 로그 출력
            } else {
                System.out.println("mno 파라미터 없음, 세션 등록 실패");
            }

        } catch (Exception e) {
            // 연결 중 오류 발생 시 로그 출력
            System.out.println("afterConnectionEstablished 오류: " + e);
        }
    }

    // 특정 회원(mno)에게 메시지 전송
    public void sendMessageToUser(int mno, String message) {
        WebSocketSession session = userSessions.get(mno); // 회원번호로 세션 조회
        if (session != null && session.isOpen()) { // 세션이 존재하고 열려있을 경우
            try {
                session.sendMessage(new TextMessage(message)); // 메시지 전송
                System.out.println("메시지 전송 성공 → mno=" + mno + ", msg=" + message);
            } catch (Exception e) {
                System.out.println("메시지 전송 오류: " + e);
            }
        } else {
            // 세션이 없거나 닫혀있을 경우
            System.out.println("세션 없음 → mno=" + mno);
        }
    }
}

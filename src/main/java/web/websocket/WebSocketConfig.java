package web.websocket;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;
import org.springframework.web.socket.server.support.HttpSessionHandshakeInterceptor;
import web.handler.AlarmSocketHandler;

@Configuration // 스프링이 실행될 때 WebSocket 설정을 읽도록 등록
@EnableWebSocket // WebSocket 기능을 활성화
public class WebSocketConfig implements WebSocketConfigurer {

    // 우리가 작성한 AlarmSocketHandler(WebSocket 메시지 주고받는 핵심 클래스)를 스프링이 자동 주입
    @Autowired
    private AlarmSocketHandler alarmSocketHandler;

    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        registry.addHandler(alarmSocketHandler, "/alarm")
                // CORS 문제를 피하기 위해 모든 오리진(*)을 허용 -- > 프론트에서 JS fetch 또는 WebSocket 같은 걸 호출
                // 실제 서비스 배포 시에는 특정 도메인만 허용해야 보안상 안전
                .setAllowedOrigins("*");
    }
}

console.log("alarm.js open");

// JSP에서 내려준 mno 사용
const mno = window.loginMno;

// mno가 있을 때만 WebSocket 연결
if (mno) {
    const socket = new WebSocket(`ws://localhost:8080/alarm?mno=${mno}`);

    socket.onopen = () => {
        console.log(`✅ WebSocket 연결 성공 (mno=${mno})`);
    };

    socket.onmessage = (event) => {
        const alarmBox = document.getElementById("alarmBox");

        if (alarmBox) {
            const msgDiv = document.createElement("div");
            msgDiv.textContent = event.data;
            msgDiv.style.padding = "5px";
            msgDiv.style.borderBottom = "1px solid #ddd";
            alarmBox.appendChild(msgDiv);

            // 초기 안내 문구 삭제
            if (alarmBox.textContent.includes("📭 아직 도착한 알림이 없습니다.")) {
                alarmBox.innerHTML = "";
                alarmBox.appendChild(msgDiv);
            }
        }
    };

    socket.onclose = () => {
        console.log("❌ WebSocket 연결 종료");
    };

    socket.onerror = (err) => {
        console.error("⚠️ WebSocket 오류:", err);
    };
} else {
    console.warn("🚫 로그인하지 않은 상태 → 알림 기능 비활성화");
}
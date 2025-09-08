console.log("alarm.js open");

if (typeof loginMno !== "undefined" && loginMno) {
  const socket = new WebSocket("ws://localhost:8080/alarm?mno=" + loginMno);

  socket.onopen = () => {
    console.log(" WebSocket 연결 성공 (mno=" + loginMno + ")");
  };

  socket.onmessage = (event) => {
    const data = event.data;
    console.log("알림 수신:", data);

    const alarmList = document.getElementById("alarmList");
    if (alarmList) {
      const li = document.createElement("li");
      li.textContent = data;
      alarmList.appendChild(li);
    }

    alert(data);
  };

  socket.onclose = () => console.log(" WebSocket 연결 종료");
  socket.onerror = (error) => console.error(" WebSocket 오류:", error);
} else {
  console.warn(" loginMno 없음 → WebSocket 연결 안 함");
}
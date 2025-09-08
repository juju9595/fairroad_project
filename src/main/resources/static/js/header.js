// header.js (프런트 전용 아이콘 전환)
document.addEventListener("DOMContentLoaded", function () {
  const menuEl = document.querySelector(".menu");
  const menuLink = document.querySelector(".menu a"); // 아이콘 앵커
  if (!menuEl || !menuLink) {
    console.warn("header.js: .menu 또는 .menu a 를 찾을 수 없습니다.");
    return;
  }

  const logMenu = document.createElement("ul");
  logMenu.id = "log-menu";
  logMenu.style.display = "none";
  menuEl.appendChild(logMenu);

  // ---- 상태 읽기/쓰기 (localStorage) ----
  const getLoginState = () => localStorage.getItem("isMember") === "true";
  const getMemberNo = () => localStorage.getItem("memberNo");

  // 상태에 따라 링크 목적지 반영
  function applyUI() {
    const isMember = getLoginState();
    if (!isMember) {
      // 미로그인: 클릭하면 로그인 페이지로 바로 이동 (기본 동작 유지)
      menuLink.setAttribute("href", "/Members/login.jsp");
      logMenu.style.display = "none";
    } else {
      // 로그인: 드롭다운 열기용
      menuLink.setAttribute("href", "#");
    }
  }
  applyUI();

  // 다른 탭에서 로그인/로그아웃했을 때 동기화
  window.addEventListener("storage", (e) => {
    if (e.key === "isMember" || e.key === "memberNo") {
      applyUI();
    }
  });

  // 클릭 핸들러
  menuLink.addEventListener("click", function (e) {
    const isMember = getLoginState();

    if (!isMember) {
      // 미로그인: 기본 이동(로그인 페이지) 그대로
      return;
    }

    // 로그인: 드롭다운 토글
    e.preventDefault();
    if (logMenu.style.display === "none") {
      logMenu.innerHTML = `
        <li><a href="/Members/mypage.jsp">마이페이지</a></li>
        <li><a href="#" id="logoutBtn">로그아웃</a></li>
      `;
      logMenu.style.display = "block";

      // 로그아웃(프런트 전용 + 서버 호출은 선택)
      document.getElementById("logoutBtn").addEventListener("click", async function (ev) {
        ev.preventDefault();
        if (!confirm("정말 로그아웃 하시겠습니까?")) return;
        try {
          // 과제/시연용이면 실패해도 무시 가능
          await fetch("/member/logout", { method: "GET" }).catch(() => {});
        } finally {
          localStorage.removeItem("isMember");
          localStorage.removeItem("memberNo");
          location.href = "/index.jsp";
        }
      });
    } else {
      logMenu.style.display = "none";
    }
  });
});

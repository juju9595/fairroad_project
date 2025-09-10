document.addEventListener("DOMContentLoaded", function () {
    const menuLink = document.querySelector(".menu a");
    const logMenu = document.getElementById("log-menu");
    logMenu.style.display = "none"; // 초기 숨김
    logMenu.innerHTML = ""; // 초기 HTML 비우기

    // ==============================
    // 1️⃣ sessionStorage에서 로그인 상태 가져오기
    // ==============================
    const memberNo = parseInt(sessionStorage.getItem("memberNo")) || 0;
    let mid = null;
    if (memberNo === 1) {
        mid = "admin";
    } else if (memberNo > 1) {
        mid = "user";
    }

    // ==============================
    // 2️⃣ 메뉴 클릭 이벤트
    // ==============================
    menuLink.addEventListener("click", function (e) {
        e.preventDefault();

        const memberNo = parseInt(sessionStorage.getItem("memberNo")) || 0;
        const isMember = memberNo > 0;
        const mid = (memberNo === 1) ? "admin" : (memberNo > 1 ? "user" : null);

        if (!isMember) {
            // 비회원 → 로그인 페이지
            location.href = "/Members/login.jsp";
            return;
        }

        // ul 토글
        if (logMenu.style.display === "none") {
            // 메뉴 열기 전에 기존 내용 초기화
            logMenu.innerHTML = "";

            if (mid === "admin") {
                logMenu.innerHTML = `
                    <li><a href="/Fair/fairWrite.jsp">등록</a></li>
                    <li><a href="#" id="logoutBtn">로그아웃</a></li>
                `;
            } else {
                logMenu.innerHTML = `
                    <li><a href="/Members/mypage.jsp">마이페이지</a></li>
                    <li><a href="#" id="logoutBtn">로그아웃</a></li>
                `;
            }
            logMenu.style.display = "flex";

            // 로그아웃 처리
            document.getElementById("logoutBtn").addEventListener("click", function () {
                if (!confirm("정말 로그아웃 하시겠습니까?")) return;

                fetch("/member/logout", { method: "GET" })
                    .then(res => res.json())
                    .then(resp => {
                        if (resp === true || resp === "true") {
                            sessionStorage.clear();
                            location.href = "/index.jsp";
                        } else {
                            alert("비정상 요청, 관리자에게 문의");
                        }
                    });
            });

        } else {
            logMenu.style.display = "none";
        }
    });

    // ==============================
    // 3️⃣ 검색창 토글
    // ==============================
    const searchBtn = document.querySelector(".search");
    const searchBar = document.getElementById("search-bar");
    const closeBtn = document.getElementById("close-search");

    searchBtn.addEventListener("click", () => searchBar.classList.add("active"));
    closeBtn.addEventListener("click", () => searchBar.classList.remove("active"));
});

document.addEventListener("DOMContentLoaded", function () {
    // -------------------------------
    // [1] 로그인/메뉴 처리
    // -------------------------------
    const menuLink = document.querySelector(".menu a");
    const logMenu = document.createElement("ul"); // 동적으로 생성
    logMenu.id = "log-menu";
    logMenu.style.display = "none"; // 처음에는 숨김
    document.querySelector(".menu").appendChild(logMenu);

    // 로그인 상태 확인
    const isMember = sessionStorage.getItem("isMember") === "true";
    const memberNo = sessionStorage.getItem("memberNo");

    menuLink.addEventListener("click", function (e) {
        e.preventDefault(); // 기본 링크 이동 방지

        if (!isMember) {
            // 비회원이면 로그인/회원가입 페이지로 이동
            location.href = "/Members/login.jsp";
            return;
        }

        // 회원이면 ul 토글
        if (logMenu.style.display === "none") {
            // ul 내용 채우기
            logMenu.innerHTML = `
                <li><a href="/Members/mypage.jsp">마이페이지</a></li>
                <li><a href="#" id="logoutBtn">로그아웃</a></li>
            `;
            logMenu.style.display = "block";

            // 로그아웃 클릭 이벤트
            document.getElementById("logoutBtn").addEventListener("click", function () {
                if (!confirm("정말 로그아웃 하시겠습니까?")) return;

                sessionStorage.removeItem("isMember");
                sessionStorage.removeItem("memberNo");

                fetch("/member/logout", { method: "GET" })
                    .then((res) => res.json())
                    .then((data) => {
                        if (data === true || data === "true") {
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

    // -------------------------------
    // [2] 검색창 토글 처리
    // -------------------------------
    const searchBtn = document.getElementById("search-btn");
    const searchBar = document.getElementById("search-bar");
    const closeBtn = document.getElementById("close-search");

    if (searchBtn && searchBar && closeBtn) {
        // 검색 버튼 클릭 → 검색창 열기/닫기
        searchBtn.addEventListener("click", () => {
            searchBar.classList.toggle("active");
        });

        // 닫기 버튼 → 닫기
        closeBtn.addEventListener("click", () => {
            searchBar.classList.remove("active");
        });
    }
});

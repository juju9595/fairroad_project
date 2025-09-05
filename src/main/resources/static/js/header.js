document.addEventListener("DOMContentLoaded", function () {

    // ===============================
    // [1] 로그인 메뉴 처리
    // ===============================
    const menuLink = document.querySelector(".menu a");
    const logMenu = document.createElement("ul"); // 동적으로 생성
    logMenu.id = "log-menu";
    logMenu.style.display = "none"; // 처음에는 숨김
    document.querySelector(".menu").appendChild(logMenu);

    const isMember = sessionStorage.getItem("isMember") === "true";
    const memberNo = sessionStorage.getItem("memberNo");

    menuLink.addEventListener("click", function (e) {
        e.preventDefault();
        if (!isMember) return location.href = "/Members/login.jsp";

        if (logMenu.style.display === "none") {
            logMenu.innerHTML = `
                <li><a href="/Members/mypage.jsp">마이페이지</a></li>
                <li><a href="#" id="logoutBtn">로그아웃</a></li>
            `;
            logMenu.style.display = "block";

            document.getElementById("logoutBtn").addEventListener("click", function () {
                if (!confirm("정말 로그아웃 하시겠습니까?")) return;
                sessionStorage.removeItem("isMember");
                sessionStorage.removeItem("memberNo");

                fetch("/member/logout", { method:"GET" })
                    .then(res => res.json())
                    .then(data => {
                        if(data===true||data==="true") location.href="/index.jsp";
                        else alert("비정상 요청, 관리자에게 문의");
                    });
            });
        } else logMenu.style.display="none";
    });

    // ===============================
    // [2] 검색창 UI 토글 (헤더 공통)
    // ===============================
    const searchBtn = document.getElementById("search-btn");
    const searchBar = document.getElementById("search-bar");
    const closeBtn = document.getElementById("close-search");
    const searchInput = document.getElementById("search-input");

    if (searchBtn && searchBar && closeBtn) {
        searchBtn.addEventListener("click", () => {
            searchBar.classList.toggle("active");
            if (searchBar.classList.contains("active")) searchInput.focus();
        });

        closeBtn.addEventListener("click", () => searchBar.classList.remove("active"));
    }

});

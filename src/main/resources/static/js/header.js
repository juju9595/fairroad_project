document.addEventListener("DOMContentLoaded", function () {
    const menuElement = document.querySelector(".menu");
    if (!menuElement) return;

    const menuLink = menuElement.querySelector("a");
    if (!menuLink) return;

    // sessionStorage 기반 로그인 상태
    let isMemberVar = sessionStorage.getItem("isMember") === "true";
    let memberNoVar = sessionStorage.getItem("memberNo") || null;

    // 메뉴 UL 생성
    const logMenu = document.createElement("ul");
    logMenu.id = "log-menu";
    logMenu.style.display = "none";
    menuElement.appendChild(logMenu);

    // 메뉴 클릭 이벤트 (로그인/관리자 판단)
    menuLink.addEventListener("click", function (e) {
        e.preventDefault();

        isMemberVar = sessionStorage.getItem("isMember") === "true";
        memberNoVar = sessionStorage.getItem("memberNo") || null;

        if (!isMemberVar) {
            location.href = "/Members/login.jsp";
            return;
        }

        const isAdmin = Number(memberNoVar) === 1;

        if (logMenu.style.display === "none") {
            logMenu.innerHTML = isAdmin
                ? `
                    <li><a href="/Fair/fairWrite.jsp">등록</a></li>
                    <li><a href="#" id="logoutBtn">로그아웃</a></li>
                  `
                : `
                    <li><a href="/Members/mypage.jsp">마이페이지</a></li>
                    <li><a href="#" id="logoutBtn">로그아웃</a></li>
                  `;
            logMenu.style.display = "block";

            // 로그아웃 이벤트
            const logoutBtn = document.getElementById("logoutBtn");
            if (logoutBtn) {
                logoutBtn.addEventListener("click", function (e) {
                    e.preventDefault();
                    if (!confirm("정말 로그아웃 하시겠습니까?")) return;

                    fetch("/member/logout", { method: "GET" })
                        .then(res => res.json())
                        .then(data => {
                            if (data === true) {
                                sessionStorage.clear();
                                logMenu.style.display = "none";
                                location.href = "/index.jsp";
                            } else {
                                alert("로그아웃 실패, 관리자에게 문의하세요.");
                            }
                        })
                        .catch(err => {
                            console.error("로그아웃 실패:", err);
                            alert("네트워크 오류, 다시 시도해주세요.");
                        });
                });
            }
        } else {
            logMenu.style.display = "none";
        }
    });

    // ===============================
    // 검색창 토글
    // ===============================
    const searchIcon = document.querySelector(".search svg");
    const searchBar = document.getElementById("search-bar");
    const searchClose = document.getElementById("close-search");

    if (searchIcon && searchBar) {
        searchIcon.addEventListener("click", () => {
            searchBar.style.display = searchBar.style.display === "block" ? "none" : "block";
        });
    }

    if (searchClose && searchBar) {
        searchClose.addEventListener("click", () => {
            searchBar.style.display = "none";
        });
    }
});

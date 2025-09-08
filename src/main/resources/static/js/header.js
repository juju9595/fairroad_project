document.addEventListener("DOMContentLoaded", function () {
    const menuElement = document.querySelector(".menu");
    if (!menuElement) return;

    const menuLink = menuElement.querySelector("a");
    if (!menuLink) return;

    // sessionStorage 기준으로 로그인 상태 확인
    let isMemberVar = sessionStorage.getItem("isMember") === "true";
    let memberNoVar = sessionStorage.getItem("memberNo");

    // 메뉴 UL 생성
    const logMenu = document.createElement("ul");
    logMenu.id = "log-menu";
    logMenu.style.display = "none";
    menuElement.appendChild(logMenu);

    // 메뉴 클릭 이벤트
    menuLink.addEventListener("click", function (e) {
        e.preventDefault();

        // 클릭 시에도 항상 최신 sessionStorage 기준으로 상태 업데이트
        isMemberVar = sessionStorage.getItem("isMember") === "true";
        memberNoVar = sessionStorage.getItem("memberNo");

        // 회원이 아니면 로그인 페이지로 이동
        if (!isMemberVar) {
            location.href = "/Members/login.jsp";
            return;
        }

        const isAdmin = Number(memberNoVar) === 1;

        if (logMenu.style.display === "none") {
            // 메뉴 열기
            logMenu.innerHTML = isAdmin
                ? `
                    <li><a href="/Admin/register.jsp">등록</a></li>
                    <li><a href="/Admin/memberList.jsp">회원목록</a></li>
                    <li><a href="#" id="logoutBtn">로그아웃</a></li>
                  `
                : `
                    <li><a href="/Members/mypage.jsp">마이페이지</a></li>
                    <li><a href="#" id="logoutBtn">로그아웃</a></li>
                  `;
            logMenu.style.display = "block";

            // 로그아웃 버튼 이벤트 연결
            const logoutBtn = document.getElementById("logoutBtn");
            if (logoutBtn) {
                logoutBtn.addEventListener("click", function (e) {
                    e.preventDefault();
                    if (!confirm("정말 로그아웃 하시겠습니까?")) return;

                    fetch("/member/logout", { method: "GET" })
                        .then(res => res.json())
                        .then(data => {
                            if (data === true) {
                                // 서버 세션 + 클라이언트 sessionStorage 모두 초기화
                                sessionStorage.clear();

                                // 메뉴 닫기
                                logMenu.style.display = "none";

                                // 메인 페이지 이동
                                location.href = "/index.jsp";
                            } else {
                                alert("로그아웃 실패, 관리자에게 문의하세요.");
                            }
                        })
                        .catch(err => {
                            console.error("로그아웃 요청 실패:", err);
                            alert("네트워크 오류 발생, 다시 시도해주세요.");
                        });
                });
            }

        } else {
            // 메뉴 닫기
            logMenu.style.display = "none";
        }
    });
});

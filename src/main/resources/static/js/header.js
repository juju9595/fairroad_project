//document.addEventListener("DOMContentLoaded", function() {
//    const menuLink = document.querySelector(".menu a");
//    const logMenu = document.createElement("ul"); // 동적으로 생성
//    logMenu.id = "log-menu";
//    logMenu.style.display = "none"; // 처음에는 숨김
//    document.querySelector(".menu").appendChild(logMenu);
//
//    // 로그인 상태 확인
//    const isMember = sessionStorage.getItem("isMember") === "true";
//    const memberNo = sessionStorage.getItem("memberNo");
//
//    menuLink.addEventListener("click", function(e) {
//        e.preventDefault(); // 기본 링크 이동 방지
//
//        if (!isMember) {
//            // 비회원이면 로그인/회원가입 페이지로 이동
//            location.href = "/Members/login.jsp";
//            return;
//        }
//
//        // 회원이면 ul 토글
//        if (logMenu.style.display === "none") {
//            // ul 내용 채우기
//            logMenu.innerHTML = `
//                <li><a href="/Members/mypage.jsp">마이페이지</a></li>
//                <li><a href="#" id="logoutBtn">로그아웃</a></li>
//            `;
//            logMenu.style.display = "block";
//
//            // 로그아웃 클릭 이벤트
//            document.getElementById("logoutBtn").addEventListener("click", function() {
//                if (!confirm("정말 로그아웃 하시겠습니까?")) return; // 확인 안 누르면 종료
//
//                sessionStorage.removeItem("isMember");
//                sessionStorage.removeItem("memberNo");
//
//                fetch("/member/logout", { method: "GET" })
//                    .then(res => res.json())
//                    .then(data => {
//                        if(data === true || data === "true"){
//                            location.href = "/index.jsp";
//                        } else {
//                            alert("비정상 요청, 관리자에게 문의");
//                        }
//                    });
//            });
//
//        } else {
//            logMenu.style.display = "none";
//        }
//    });
//});

document.addEventListener("DOMContentLoaded", function () {
    const menuElement = document.querySelector(".menu");
    console.log("menuElement:", menuElement);  // 이게 null이면 선택 실패

    const menuLink = document.querySelector(".menu a");
    console.log("menuLink:", menuLink);

    // data attribute는 문자열이므로 적절히 변환
    const isMemberVar = menuElement.dataset.isMember === "true"; // boolean 변환
    const memberNoVar = menuElement.dataset.memberNo || null; // 빈 문자열일 경우 null 처리

    console.log("isMember from data-attr:", isMemberVar);
    console.log("memberNo from data-attr:", memberNoVar);

    console.log("초기 상태 -> isMemberVar:", isMemberVar, ", memberNoVar:", memberNoVar, ", typeof memberNoVar:", typeof memberNoVar);

    const logMenu = document.createElement("ul");
    logMenu.id = "log-menu";
    logMenu.style.display = "none";
    menuElement.appendChild(logMenu);

    menuLink.addEventListener("click", function (e) {
        e.preventDefault();

        console.log("클릭! 현재 isMemberVar:", isMemberVar, ", memberNoVar:", memberNoVar);

        if (!isMemberVar) {
            console.log("회원 아님 -> 로그인 페이지 이동");
            location.href = "/Members/login.jsp";
            return;
        }

        // memberNoVar는 문자열일 수 있으니 숫자로 변환 후 비교
        const isAdmin = Number(memberNoVar) === 1;
        console.log("isAdmin 판정:", isAdmin);

        if (logMenu.style.display === "none") {
            console.log("메뉴가 닫혀있음 -> 메뉴 열기");

            logMenu.innerHTML = isAdmin
                ? `
                    <li><a href="/Fair/fairWrite.jsp">등록</a></li>
                    <li><a href="/Admin/memberList.jsp">회원목록</a></li>
                    <li><a href="#" id="logoutBtn">로그아웃</a></li>
                `
                : `
                    <li><a href="/Members/mypage.jsp">마이페이지</a></li>
                    <li><a href="#" id="logoutBtn">로그아웃</a></li>
                `;
            logMenu.style.display = "block";
            console.log("메뉴 열림, innerHTML 세팅됨");

            setTimeout(() => {
                const logoutBtn = document.getElementById("logoutBtn");
                if (logoutBtn) {
                    logoutBtn.addEventListener("click", function (e) {
                        e.preventDefault();
                        if (!confirm("정말 로그아웃 하시겠습니까?")) return;

                        fetch("/member/logout", { method: "GET" })
                            .then(res => res.json())
                            .then(data => {
                                if (data === true || data === "true") {
                                    console.log("로그아웃 성공 -> 메인페이지 이동");
                                    location.href = "/index.jsp";
                                } else {
                                    alert("비정상 요청, 관리자에게 문의");
                                }
                            });
                    });
                    console.log("로그아웃 버튼 이벤트 연결 완료");
                } else {
                    console.log("로그아웃 버튼을 찾지 못함");
                }
            }, 10);
        } else {
            console.log("메뉴가 열려있음 -> 메뉴 닫기");
            logMenu.style.display = "none";
        }
    });
});


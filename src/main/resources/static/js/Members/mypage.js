console.log("mypage.js open")

// [1] 로그인 후 출력
const myinfo = async () => {
    const logMenu = document.querySelector('#log-menu');
    const isMember = sessionStorage.getItem("isMember") === "true";
    const memberNo = sessionStorage.getItem("memberNo");

    // 비회원이면 아예 안보이게
    if (!isMember || !memberNo) {
        logMenu.style.display = "none";
        return;
    }

    let html = '';
    try {
        const option = { method: "GET" }
        const response = await fetch("member/info", option);
        const data = await response.json(); 
        console.log(data);

        html += `<li><span>${data.mid}님</span></li>
                 <li><a href="/Members/mypage.jsp">마이페이지</a></li> 
                 <li><a href="#" onclick="logout()">로그아웃</a></li>`;
    } catch (error) {
        console.error('myinfo fetch 에러:', error);
    }
    logMenu.innerHTML = html;
    logMenu.style.display = "flex"; // 회원일 때 보이게
}
myinfo();

// [2] 로그아웃
const logout = async () => {
    try {
        // 로그아웃 확인
        if (!confirm("정말 로그아웃 하시겠습니까?")) return;

        // sessionStorage 삭제
        sessionStorage.removeItem("isMember");
        sessionStorage.removeItem("memberNo");

        const option = { method: "GET" }
        const response = await fetch("/member/logout", option);
        const data = await response.json();

        if (data === true || data === "true") {
            alert('로그아웃 했습니다.');
            location.href = "/index.jsp";
        } else {
            alert('비정상 요청 및 관리자에게 문의');
        }
    } catch (error) {
        console.error('로그아웃 에러:', error);
    }
}

/*  회의할것
    1. 로그인했을때 안했을때 드롭다운 HTML/CSS 여부
    2. 회원의 주소 사용 필요 여부??
    	1). 만일 내위치 기반 박람회 조회 이면 DB에 주소 없이 현지점 위치로 박람회 조회
    	2) 관심 주소(위치) 기반이면 SELECT 로 변경

*/
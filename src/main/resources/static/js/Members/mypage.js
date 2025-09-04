console.log("mypage.js open")

// [1] 로그인 후 출력
const myinfo = async () => {
    const logMenu = document.querySelector('#log-menu');
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
}
myinfo();

// [2] 로그아웃
const logout = async () => {
    try {
        // sessionStorage 삭제
        sessionStorage.removeItem("isMember");
        sessionStorage.removeItem("memberNo");

        const option = { method: "GET" }
        const response = await fetch("/member/logout", option);
        const data = await response.json();

        if (data === true) {
            alert('로그아웃 했습니다.');
            location.href = "/index.jsp";
        } else {
            alert('비정상 요청 및 관리자에게 문의');
        }
    } catch (error) {
        console.error('로그아웃 에러:', error);
    }
}

console.log("mypage.js open")


const myinfo = async() =>{
    const logMenu= document.querySelector('#log-menu');
    let html = '' //(2) 무엇을
    try{
        // ======================= 로그인 성공시 로직 ======================
        //1.fetch 실행
        const option = { method : "GET"}
        const response = await fetch( "member/info", option);
        const data = await response.json(); console.log(data);
        html += `<li><span>${data.mid}님</span></li>
                <li><a href="/Members/mypage.jsp">마이페이지</a></li> 
                <li><a href="#" onclick="logout()">로그아웃</a></li>
                `
        logMenu.innerHTML = html;
    }catch{
        // ======================= 로그인 실패시 로직 ======================

    }
    // ======================= 로그인 성공하든 실패하든 로직 ======================

}
myinfo();

//[2] 로그아웃
const logout = async () => {
    try {
        localStorage.removeItem("isMember");
        localStorage.removeItem("memberNo");

        const option = { method: "GET" }
        const response = await fetch("/member/logout", option);
        const data = await response.json();
        if(data == true){
            alert('로그아웃 했습니다.');
            location.href="/index.jsp";
        } else {
            alert('비정상 요청 및 관리자에게 문의')
        }
    } catch {}
}

/*  회의할것
    1. 로그인했을때 안했을때 드롭다운 HTML/CSS 여부
    2. 회원의 주소 사용 필요 여부??
    	1). 만일 내위치 기반 박람회 조회 이면 DB에 주소 없이 현지점 위치로 박람회 조회
    	2) 관심 주소(위치) 기반이면 SELECT 로 변경

*/
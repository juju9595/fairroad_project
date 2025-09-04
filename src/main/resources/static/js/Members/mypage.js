console.log("mypage.js open")


const myinfo = async() =>{
    const logMenu= document.querySelector('#log-menu');
    let html = '' //(2) 무엇을
    try{
        //1.fetch 실행
        const option = { method : "GET"}
        const response = await fetch( "member/info", option);
        const data = await response.json(); console.log(data);
        html += `<li><span>${data.mid}님</span></li>
                <li><a href="/Members/mypage.jsp">마이페이지</a></li> 
                <li><a href="#" onclick="logout()">로그아웃</a></li>
                `
    }catch{
    }
    logMenu.innerHTML = html;
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
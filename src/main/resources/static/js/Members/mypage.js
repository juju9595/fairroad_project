console.log("mypage.js open")


const myinfo = async() =>{
    const logMenu= document.querySelector('#log-menu');
    let html = '' //(2) 무엇을
    try{
        //1.fetch 실행
        const option = { method : "GET"}
        const response = await fetch( "member/update", option);
        const data = await response.json(); console.log(data);
        html += `<li><span>${data.mid}님</span></li>
                <li><a href="#" onclick="logout()로그아웃</a></li>
                `
    }catch{
        html += `<li><a href="/member/login.jsp">로그인</a></li>
            <li><a href="/member/signup.jsp">회원가입</a></li>`
    }
    logMenu.innerHTML = html;
}
myinfo();

//[2] 로그아웃
const logout = async() =>{
    try{
        const option = {method : "GET"}
        const response = await fetch ("/member/logout", option);
        const data = await response.json();
        if(data == true){
            alert('로그아웃 했습니다.');
            location.href="/index.jsp";
        }else{alert('비정상 요청 및 관리자에게 문의')}
    }catch{}
}
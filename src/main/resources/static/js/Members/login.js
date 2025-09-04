console.log("login.js open");

//[1] 로그인, login

const login = async() =>{

    const idInput = document.querySelector('.idInput');
    const pwdInput = document.querySelector('.pwdInput');
    //2. 가져온 마크업의 입력받은 값 가져오기
    const mid = idInput.value;
    const mpwd = pwdInput.value;
    //3.객체화
    const obj = {mid, mpwd};
    try{
        //fetch 실행
        const option = {
            method : "POST",
            headers : { "Content-Type" : "application/json"},
            body : JSON.stringify(obj)
        }
        const response = await fetch("/member/login", option);
        const data = await response.json();
    if(data > 0){ // data가 0보다 크면 로그인 성공 → data = mno
        alert('로그인 성공');

        // localStorage에 로그인 상태와 회원 번호 저장
        localStorage.setItem("isMember", "true");
        localStorage.setItem("memberNo", data);

        // 메인 페이지 이동
        location.href="/index.jsp";
    } else {
        alert('아이디 또는 비밀번호가 다릅니다.');
    }
    }catch{}
}//func e
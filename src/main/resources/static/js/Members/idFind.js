// 아이디 찾기
console.log("idFind.js open");

const findId = async() =>{
    const nameInput = document.querySelector('.nameInput');
    const phoneInput = document.querySelector('.phoneInput');

    const mname = nameInput.value;
    const mphone = phoneInput.value;

    const response = await fetch(`/member/findid?mname=${mname}&mphone=${mphone}`);
    const data = await response.json();

    const resultBox = document.querySelector("#resultId");

    if(!data === "해당 정보로 가입된 회원이 없습니다."){
        resultBox.innerHTML = `<p>해당 정보로 가입된 회원이 없습니다. 다시입력해주세요</p>`
    }else{
        resultBox.innerHTML = `<p>${data.msg}</p>
                                                    <a href="/Members/login.jsp">로그인하러 가기</a>`;}
}

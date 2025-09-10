// 아이디 찾기
console.log("idFind.js open");

const findId = async() =>{
    const nameInput = document.querySelector('.nameInput');
    const phoneInput = document.querySelector('.phoneInput');

    const mname = nameInput.value;
    const mphone = phoneInput.value;

    const response = await fetch(`/member/findid?mname=${mname}&mphone=${mphone}`);
    const data = await response.json();

    document.querySelector("#resultId").innerHTML = `<p>${data.msg}</p>
                                                    <a href="/Members/login.jsp">로그인하러 가기</a>`;
}

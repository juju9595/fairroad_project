console.log("pwdFind.js open");

const findPwd = async () => {
    const idInput = document.querySelector("#idInput");
    const phoneInput = document.querySelector("#phoneInput");

    const mid = idInput.value;
    const mphone = phoneInput.value;

    const response = await fetch(`/member/findpwd?mid=${mid}&mphone=${mphone}`);
    const data = await response.json();
    
     document.querySelector("#resultPwd").innerHTML = `<p>${data.msg}</p>
                                                    <a href="/Members/login.jsp">로그인하러 가기</a>`;
}
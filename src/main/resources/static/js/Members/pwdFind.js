console.log("pwdFind.js open");

const findPwd = async () => {
    const idInput = document.querySelector("#idInput");
    const phoneInput = document.querySelector("#phoneInput");

    const mid = idInput.value;
    const mphone = phoneInput.value;

    const response = await fetch(`/member/findpwd?mid=${mid}&mphone=${mphone}`);
    const data = await response.json();
    
     document.querySelector("#resultPwd").innerHTML = `<p>임시비밀번호는${data.msg}입니다. 비밀번호를 수정해주세요.</p>
                                                    <a href="/Members/login.jsp">로그인하러 가기</a>`;
}
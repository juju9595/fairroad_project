console.log("checkinfo.js open");

const checkAndGo = async () => {
    const pwdInput = document.querySelector('.pwdInput');
    const msgBox = document.querySelector('#checkMsg');

    const mpwd = pwdInput.value;


    try{
        const option = {method : "POST"}
        const response = await fetch(`/member/checkinfo?mpwd=${mpwd}`, option);
        const data = await response.json();
        if(data == true){
            location.href="/Members/info.jsp";
        }else{msgBox.textContent = '비밀번호가 일치하지 않습니다.';}
    }catch(error){console.log(error)}

};
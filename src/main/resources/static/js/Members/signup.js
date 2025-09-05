console.log('signup.js open')

const signup = async() =>{
    //마크업의 DOM 객체 가져오기
    const idInput = document.querySelector('.idInput');
    const pwdInput = document.querySelector('.pwdInput');
    const nameInput = document.querySelector('.nameInput');
    const birthInput = document.querySelector('.birthInput');
    const phoneInput = document.querySelector('.phoneInput');
    const emailInput = document.querySelector('.emailInput');
    const addressInput = document.querySelector('.addressInput');

    //DOM 객체에서 입력받은 값 가져오기
    const mid = idInput.value;
    const mpwd = pwdInput.value;
    const mname = nameInput.value;
    const mbirth = birthInput.value;
    const mphone = phoneInput.value;
    const memail = emailInput.value;
    const maddress = addressInput.value;

    const obj = {mid, mpwd, mname, mbirth, mphone, memail, maddress};

    try{
        const option = {
            method : "POST",
            headers : { "Content-Type" : "application/json" },
            body : JSON.stringify(obj)
        }
        const response = await fetch("/member/signup" , option);
        const data = await response.json();
        if(data > 0){alert('회원가입 성공'); location.href="/Members/login.jsp"}
        else{alert('회원가입 실패')}
    }catch(error){console.log(error);}
}

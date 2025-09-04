console.log('signup.js open')

const signPass = [false, false];

const signup = async() =>{

    if(signPass.includes(false)){alert('올바른 정보 입력 후 가능합니다.')}

    //1. 마크업 DOM 객체 가져오기
    const idInput = document.querySelector('.idInput');
    const pwdInput = document.querySelector('.pwdInput');
    const nameInput = document.querySelector('.nameInput');
    const birthInput = document.querySelector('.birthInput');
    const phoneInput = document.querySelector('.phoneInput');
    const emailInput = document.querySelector('.emailInput');
    const addressInput = document.querySelector('.addressInput');

    //2. DOM객체에서 입력받은 값 가졍오기
    const mid = idInput.value;
    const mpwd = pwdInput.value;
    const mname = nameInput.value;
    const mbirth = birthInput.value;
    const mphone = phoneInput.value;
    const memail = emailInput.value;
    const maddress = addressInput.value;

    //3. 객체화
    const obj = {mid, mpwd, mname, mbirth, mphone, memail, maddress};

    //4. fetch 실행
    try{
        const option = {
            method : "POST",
            headers : {"Content-Type" : "application/json"},
            body : JSON.stringify(obj)
        }
        const response = await fetch("/member/signup", option);
        const data = await response.json();
        //5. fetch 결과
        if(data > 0){alert('회원가입되었습니다.'); location.href="/Members/login.jsp"}
        else{alert('회원가입에 실패하였습니다.');}
    }catch(error){console.log(error);}
}//func e

//[2] 아이디중복검사
const idCheck = async() => {
    const mid = document.querySelector('.idInput').value;
    const idCheck = document.querySelector('.idCheck').value;
    if(mid.length < 4 || mid.length > 21 ){
        idCheck.innerHTML = "아이디는 5~20자로 사용 가능합니다"
        signPass[0] = false;
    return;}
    const option = { method : "GET"}
    const response = await fetch(`member/check?=type=mid&data=${mid}`, option);
    const data = await response.json();
    //fetch 결과
    if(data == true){
        idCheck.innerHTML = "사용중인 아이디 입니다."
        signPass[0] = false;
    }else{
        idCheck.innerHTML = "사용가능한 아이디 입니다."
        signPass[0] = true;
    }
}

//[3] 비밀번호 검사 : 입력할때마다 발동
const pwdCheck = async() => {
    const mpwd = document.querySelector('.pwdCheck').value;
    const pwdCheck = document.querySelector('.pwdCheck').value;
    if(mpwd.length < 4 || mpwd.length > 17 ){
        pwdCheck.innerHTML = "비밀번호는 8~16자로 사용 가능합니다"
        signPass[0] = false;
        return;
    }
}

//[4] 연락처중복검사

//[5] 이메일중복검사

console.log('signup.js open')

const signPass = [false, false, false];

const signup = async() =>{

    if(signPass.includes(false)){alert('올바른 정보를 입력 후 가능합니다.') 
        return;
    }
    // //마크업의 DOM 객체 가져오기
    // const idInput = document.querySelector('.idInput');
    // const idCheck = document.querySelector('.idCheck');

    //     // JS 정규표현식
    //     // 영문자로 시작하는 영문자 또는 숫자 6~20자  ///////////    var regExp = /^[a-z]+[a-z0-9]{5,19}$/g;

    // var regExp = /^[a-z]+[a-z0-9]{5,19}$/g;

    // if( regExp.test( idInput.value ) ){ // 정규표현식변수.test( 자료 ); 해당 자료를 정해진 정규표현식으로 검사
    //     alert('적합');
    // }else{
    //     alert('부적합');
    // }

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


//[2] 아이디중복검사 : 입력할때마다 발동
const idCheck = async() =>{
    const mid = document.querySelector('.idInput').value;
    const idCheck = document.querySelector('.idCheck');
    //유효성 검사
    var regExp = /^[a-z]+[a-z0-9]{5,19}$/g;
    if( !regExp.test( idInput.value ) ){ // 정규표현식변수.test( 자료 ); 해당 자료를 정해진 정규표현식으로 검사
    idCheck.innerHTML = "6-20자 소문자+숫자로 입력해주세요."
    signPass[0] = false;
    return;//함수 종료
    }
    //중복검사 
    try{const option = {method : "GET"}
    const response = await fetch(`/member/check?type=mid&data=${mid}`, option);
    const data = await response.json();
    //fetch 결과
    if(data == true){
        idCheck.innerHTML = "사용중인 아이디 입니다."
        signPass[0] = false;
    }else{
        idCheck.innerHTML = "사용가능한 아이디 입니다."
        signPass[0] = true;
    }}catch(error){console.log(error)}
}

//[3] 비밀번호 유효성 검사 : 입력할때마다 발동
const pwdCheck = async() =>{
    const pwdInput = document.querySelector('.pwdInput');
    const pwdCheck= document.querySelector('.pwdCheck');
    //입력된 비밀번호
    //유효성 검사
    var regPw = /^(?=.*[a-z])(?=.*[A-Z])(?=.*\d)[A-Za-z\d]{8,16}$/
    if(regPw.test(pwdInput.value)){
        pwdCheck.innerHTML = "사용가능한 비밀번호 입니다."
    }else{
        pwdCheck.innerHTML = "8-16자 대문자+소문자+숫자로 입력해주세요."
    }
}

//[3] 연락처 중복 검사 : 입력할때마다 발동
const phoneCheck = async() =>{
    const mphone = document.querySelector('.phoneInput').value;
    const phoneCheck = document.querySelector('.phoneCheck');
    //유효성 검사
    if(mphone.length != 13){
        phoneCheck.innerHTML = "-(하이픈) 포함한 13글자 연락처를 입력해주세요."
        signPass[1] = false;
        return;//함수 종료
    }
    //중복검사
    try{
        const option = { method : "GET"}
        const response = await fetch(`/member/check?type=mphone&data=${mphone}`, option);
        const data = await response.json();
    if(data == true)
    {phoneCheck.innerHTML = "사용중인 연락처 입니다.";
        signPass[1] = false;
    }else{phoneCheck.innerHTML = "사용가능한 연락처 입니다.";
        signPass[1] = true;
    }
}catch(error){console.log(error)}
}


//[4] 이메일 중복 검사 : 입력할때마다 발동
const emailCheck = async() =>{
    const memail = document.querySelector('.emailInput').value;
    const emailCheck = document.querySelector('.emailCheck');
    //유효성 검사
    var regEmail = /^[0-9a-zA-Z]([-_\.]?[0-9a-zA-Z])*@[0-9a-zA-Z]([-_\.]?[0-9a-zA-Z])*\.[a-zA-Z]{2,3}$/i;
    if(!regEmail.test(emailInput.value)){
        emailCheck.innerHTML = "이메일형식이 올바르지 않습니다.";
        signPass[2] = false;
        return;//함수 종료
    }
    //중복검사
    try{
        const option = {method : "GET"}
        const response = await fetch(`/member/check?type=memail&data=${memail}`, option);
        const data = await response.json();
        if(data == true)
        {emailCheck.innerHTML = "사용중인 이메일 입니다.";
            signPass[2] = false;
        }else{emailCheck.innerHTML = "사용가능한 이메일 입니다.";
            signPass[2] = true;
        }
    }catch(error){console.log('error')}
}

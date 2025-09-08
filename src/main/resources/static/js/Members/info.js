console.log("info.js open");

//[1] 내 정보 조회
const onInfo = async() =>{
    try{
        const option = {method : "GET"}
        const response = await fetch("/member/info", option);
        const data = await response.json();
        //fetch 결과
        document.querySelector('.mid').innerHTML = data.mid;
        document.querySelector('.mname').innerHTML = data.mname;
        document.querySelector('.mphone').innerHTML = data.mphone;
        document.querySelector('.mbirth').innerHTML = data.mbirth;
        document.querySelector('.memail').innerHTML = data.memail;
        document.querySelector('.maddress').innerHTML = data.maddress;
        document.querySelector('.joindate').innerHTML = data.joindate;
    }catch(error){console.log(error)
    }
}//func e
onInfo(); //최초 1번 실행

const onDelete = async () => {
    let result = confirm('정말 탈퇴 할까요?');
    if(result == false){return;}
    try{
        const option = {method : "DELETE"};
        const response = await fetch ("/member/delete", option);
        const data = await response.json();
    }catch(error){console.log(error);}
}
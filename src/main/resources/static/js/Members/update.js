console.log("update.js open");

//기존 정보 불러오기
const onInfo = async() =>{
    try{
        const option = {method : "GET"}
        const response = await fetch("/member/info", option);
        const data = await response.json();

        document.querySelector('.mid').innerHTML = data.mid;
        document.querySelector('.mname').innerHTML = data.mname;
    }catch(error){console.log(error)}
}
onInfo();//최초 1번 실행


//새로운 정보 불러오기
const onUpdate = async() =>{

    const mphone = document.querySelector('.mphone').value;
    const maddress = document.querySelector('.maddress').value;
    const obj = {mno, mphone, maddress};

    const response = await fetch(`/member/update?mno=${mno}`);

    try{
        const option ={
            method : "POST",
            headers : { "Content-Type" : "application/json"},
            body : JSON.stringify(obj)
        }
        const response = await fetch("/member/update", option);
        const data = await response.json();
    }catch(error){econsole.log('error');
    }
}
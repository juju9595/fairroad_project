console.log("update.js open");

//기존 정보 불러오기
const onInfo = async() =>{
    try{
        const option = {method : "GET"}
        const response = await fetch("/member/info", option);
        const data = await response.json();

        document.querySelector('.mid').innerHTML = data.mid;
        document.querySelector('.mname').innerHTML = data.mname;
        document.querySelector('.mphone').value = data.mphone;
        document.querySelector('.maddress').value = data.maddress;
    }catch(error){console.log(error)}
}
onInfo();//최초 1번 실행


//새로운 정보 불러오기
const onUpdate = async() =>{

    const mphone = document.querySelector('.mphone').value;
    const maddress = document.querySelector('.maddress').value;
    const obj = { mphone, maddress };

    try{
        const option ={
            method : "PUT",
            headers : { "Content-Type" : "application/json"},
            body : JSON.stringify(obj)
        }
        const response = await fetch("/member/update", option);
        const data = await response.json();

        if( data == true ){
            alert("성공");
              location.href= "/Members/info.jsp"
        }else{
            alert("실패");
        }
    }catch(error){econsole.log('error');
    }
}
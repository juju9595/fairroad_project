console.log("update.js open");

//기존 정보 불러오기
const onInfo = async() =>{}
onInfo();//최초 1번 실행


//새로운 정보 불러오기
const onUpdate = async() =>{
    const response = await fetch(`/member/update?mno=${mno}`);
    try{
        const option ={
            method : "POST",
            headers : { "Content-Type" : "application/json"},
            body : JSON.stringify(obj)
        }
        const response = await fetch("/member/update", option);
        const data = await response.json();
    }
}
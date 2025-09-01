console.log("fairWrite open");

$(document).ready(function () {
    $('#summernote').summernote({
        lang: 'ko-KR', // default: 'en-US'
        minHeight:300, //썸머노트 구역 최소 높이
    });

});

// 박람회 등록
const onFairWrite = async() => {
    let cno = document.querySelector('.cno').value;
    let fname = document.querySelector('.fname').value;
    let fplace = document.querySelector('.fplace').value;
    let furl = document.querySelector('.furl').value;
    let fprice = document.querySelector('.fprice').value;
    let finfo = document.querySelector('.finfo').value;
    let start_date = document.querySelector('.start_date').value;
    let end_date = document.querySelector('.end_date').value;

    let obj = {cno,fname,fplace,furl,fprice,finfo,start_date,end_date}; console.log(obj);
    let option ={
        method:"POST",
        headers:{"content-type":"application/json"},
        body:JSON.stringify(obj)
    }//option end
    try{
        const response = await fetch("/fair/write",option);
        const data = await response.json();
        if(data>0){
            alert("등록성공");
            location.href=`/index.jsp`;
        }else{
            alert("등록실패");
        }//if end
        
    }catch(error){console.log(error)}//catch end


}//func end


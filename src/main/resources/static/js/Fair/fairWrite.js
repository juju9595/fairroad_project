console.log("fairWrite open");

$(document).ready(function () {
    $('#summernote').summernote({
        lang: 'ko-KR', // default: 'en-US'
        minHeight:300, //썸머노트 구역 최소 높이
    });

});

// 박람회 등록
const onFairWrite = async() => {
    const fairForm = document.querySelector('#fairForm');
    const fairFormData = new FormData(fairForm);
    fairFormData.append('finfo',$('#summernote').summernote('code'))
    let option ={
        method:"POST",
        body:fairFormData
    }//option end
    try{
        const response = await fetch("/fair/write",option);
        const data = await response.json();
        console.log(data);
        if(data>0){
            alert("등록성공");
            location.href=`/index.jsp`;
        }else{
            alert("등록실패");
        }//if end
        
    }catch(error){console.log(error)}//catch end


}//func end


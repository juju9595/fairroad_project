console.log("fairUpdate open")

// URL 에서 fno 가져오기
const fno = new URL(location.href).searchParams.get('fno');

$(document).ready(function () {
    $('#summernote').summernote({
        lang: 'ko-KR', // default: 'en-US'
        minHeight:300, //썸머노트 구역 최소 높이
    });

});


//박람회 기존정보
const view = async( )=>{
const url = new URLSearchParams(location.search);
const fno = url.get('fno');
const response = await fetch(`/fair/getPost?fno=${fno}`);
const data = await response.json();
console.log(data)
document.querySelector('.fname').value = data.fname;
document.querySelector('.fplace').value = data.fplace;
document.querySelector('.fprice').value = data.fprice;
document.querySelector('.furl').value = data.furl;
document.querySelector('.finfo').value = data.finfo;
document.querySelector('.start_date').value = data.start_date;
document.querySelector('.end_date').value = data.end_date;
}
view();

//박람회 수정
const onFairUpdate = async () =>{
    const fairForm = document.querySelector('#fairForm');
    const fairFormData = new FormData(fairForm);

    fairFormData.append('finfo',$('#summernote').summernote('code'))
    let option ={
        method:"PUT",
        body:fairFormData
    }//option end
    try{
        const response = await fetch(`/fair/update?fno=${fno}`,option);
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
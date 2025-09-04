console.log( "reviewUpdate.js open" );

// [2] 수정처리 
const reviewUpdate = async () => {

    // URL에서 rno 가져오기
    const params = new URLSearchParams(window.location.search);
    const rno = params.get("rno");
    if (!rno) {
      console.error("리뷰 번호(rno)가 없습니다.");
      return;
    }

    // 1. 수정할 자료 준비 
    const rtitle = document.querySelector('.rtitle').value;
    const rcontent = document.querySelector('.rcontent').value;
    const obj = { rno , rtitle , rcontent };
    // 2. 
    const option = { 
        method : "PUT", 
        headers : { "content-type" : "application/json"} , 
        body : JSON.stringify(obj) 
    }
    const response = await fetch( '/fair/review/update' , option );
    const data = await response.json();
    if( data == 0 ){ alert('수정 실패했습니다.'); }
    else{ alert('수정 성공했습니다.'); location.href = `reviewDetail.jsp?rno=${rno}` }
}
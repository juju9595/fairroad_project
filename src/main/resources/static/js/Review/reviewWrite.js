console.log( "reviewWrite.js open" );


// 리뷰 등록
const reviewWrite  = async()=>{

    // URL에서 fno 가져오기
    const params = new URLSearchParams(window.location.search);
    const fno = params.get("fno");
    if (!fno) {
      console.error("리뷰 번호(fno)가 없습니다.");
      return;
    }
    // 1. 전달할 데이터 준비
    const rtitle = document.querySelector('.rtitle').value;
    const rcontent = document.querySelector('.rcontent').value;
    const obj = { fno , rtitle , rcontent }
    // 2. fetch 
    const option = {
        method : "POST" , 
        headers : { "content-type" : "application/json"},
        body : JSON.stringify( obj )
    }
    const response = await fetch( `/fair/review/write` , option );
    const data = await response.json();
    // 3. 
    if( data == 0 ){ alert('리뷰 등록에 실패했습니다'); }
    else{ 
        alert('리뷰가 등록되었습니다');
        location.href = `/Fair/getPost?fno=${fno}`;
    }
}
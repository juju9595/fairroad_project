console.log("reviewDetail.js open");

// 상세 리뷰 조회 함수
const reviewDetail = async () => {
  try {
    // URL에서 rno 가져오기
    const params = new URLSearchParams(window.location.search);
    const rno = params.get("rno");
    if (!rno) {
      console.error("리뷰 번호(rno)가 없습니다.");
      return;
    }

    // 단일 리뷰 조회 API 호출
    const res = await fetch(`/fair/review/print2?rno=${rno}`);
    if (!res.ok) {
      throw new Error("리뷰 상세조회 API 호출 실패");
    }

    const review = await res.json();

    // DOM에 출력
    const reviewDetailTbody = document.querySelector(".reviewDetailTbody");
    let html = `
      <tr>
        <td>${review.rno}</td>
        <td>${review.rtitle}</td>
        <td>${review.rcontent}</td>
        <td>${review.rdate}</td>
      </tr>
    `;

    reviewDetailTbody.innerHTML = html;
  } catch (err) {
    console.error("리뷰 상세조회 오류:", err);
  }
};

// 페이지 로드 시 자동 실행
reviewDetail();

// [1] 삭제처리
const reviewDelete = async()=>{

    // URL에서 rno 가져오기
    const params = new URLSearchParams(window.location.search);
    const rno = params.get("rno");   // ✅ 반드시 변수 선언
    if (!rno) {
      console.error("리뷰 번호(rno)가 없습니다.");
      return;
    }
    
    const option = { method : "DELETE" }
    const response = await fetch( `/fair/review?rno=${ rno }` , option );
    const data = await response.json(); 
    
    if( data == true ){
        alert('삭제 성공했습니다.');
        location.href = 'review.jsp';
    }else{
        alert('삭제 실패했습니다.');
    }
} // func end 


function goUpdatePage() {
    
    const params = new URLSearchParams(window.location.search);
    const rno = params.get("rno");

    if (!rno) {
      alert("리뷰 번호가 없습니다.");
      return;
    }

    // 리뷰 수정 페이지로 이동
    location.href = `/Review/reviewUpdate.jsp?rno=${rno}`;
  }



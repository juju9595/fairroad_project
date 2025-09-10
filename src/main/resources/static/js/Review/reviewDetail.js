console.log("reviewDetail.js open");

// 주석확인용
// 상세 리뷰 조회 함수
const reviewDetail = async () => {
  try {
    // URL에서 rno 가져오기
    const params = new URLSearchParams(window.location.search);
    const rno = params.get("rno");
    console.log( rno );
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
const reviewDelete = async () => {
  // URL에서 rno 가져오기
  const params = new URLSearchParams(window.location.search);
  const rno = params.get("rno");

  if (!rno) {
    console.error("파라미터 부족: rno 또는 fno 없음", { rno });
    alert("잘못된 접근입니다.");
    return;
  }
  
  const res = await fetch(`/fair/review?rno=${encodeURIComponent(rno)}`, {
    method: "DELETE",
    credentials: "include"
  });

  if (res.status === 401) { alert("로그인이 필요합니다."); return; }

  let ok = false;
  try {
    // 컨트롤러가 boolean을 JSON으로 주는 경우
    ok = await res.json();  // true 또는 false
  } catch {
    // 혹시 text로 내려오면
    const t = await res.text();
    ok = (t.trim() === "true");
  }

  if (ok === true) {
    alert("삭제되었습니다.");
    location.href = "/Review/review";
  setTimeout(() => window.location.replace(url), 0);
  } else {
    // 여기로 오면 '남의 글'이거나 존재하지 않음 등
    alert("본인 글만 삭제할 수 있거나, 삭제할 대상이 없습니다.");
  }
};


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



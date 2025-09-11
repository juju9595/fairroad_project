console.log("review.js open");

const findAllReview = async () => {
  try {
    const params = new URLSearchParams(window.location.search);
    const fno = params.get("fno");
    if (!fno) {
      console.error("리뷰 번호(fno)가 없습니다.");
      return;
    }

    // 단순히 fno만 넘겨서 전체 조회
    const response = await fetch(`/fair/review/print?fno=${fno}`);
    const reviews = await response.json();   // 바로 배열(List<ReviewDto>)
    console.log(reviews);

    const reviewTbody = document.querySelector('.reviewTbody');
    let html = '';

    reviews.forEach(review => {
      html += `
        <tr>
          <td>${review.rdate}</td>
          <td>
            <a href="/Review/reviewDetail.jsp?rno=${review.rno}&fno=${fno}">
              ${review.rtitle}
            </a>
          </td>
        </tr>`;
    });

    reviewTbody.innerHTML = html;
  } catch (error) {
    console.error(error);
  }
};

// 페이지 로드시 자동 실행
findAllReview();

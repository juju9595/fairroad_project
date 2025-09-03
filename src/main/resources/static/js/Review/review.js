console.log("review.js open");

const findAllReview = async () => {
  try {
    const res = await fetch('/fair/review/print'); // 전체 조회 API
    const data = await res.json();

    const reviewTbody = document.querySelector('.reviewTbody');
    let html = '';
    data.forEach(review => {
      html += `
        <tr>
          <td>${review.rno}</td>
          <td>
            <a href="/reviewDetail.jsp?rno=${review.rno}">
              ${review.rtitle}
            </a>
          </td>
        </tr>`;
    });

    reviewTbody.innerHTML = html;
  } catch (err) {
    console.error(err);
  }
};

// 페이지 로드시 자동 실행
findAllReview();




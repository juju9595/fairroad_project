console.log('wishlist.js open');

const wishlistFind = async() => { console.log('wishlistFind. exe')
    try{
    const option = { method :"GET"}
    const response = await fetch("/member/wishlist", option);
    const data = await response.json();
    const wishlistBox = document.querySelector('#wishlistBox')

    let html = '';
    for(let i = 0; i<data.length; i++){
        const wishlist = data[i];
        html += `
        <tr>
          <td style="text-align:center;">
            <input type="checkbox" class="wish" value="${wishlist.fno}">
          </td>
          <td>${wishlist.fname}</td>
        </tr>
      `;
    }
    wishlistBox.innerHTML = html;
    }catch(error){
        console.log(error);
        // location.href = "/member/login.jsp";
    };

} 
wishlistFind();

const wishlistRenderRows = async () => {
  const res = await fetch('/member/wishlist');
  const data = await res.json();   // [{fno, fname}, ...] 가정
  const tbody = document.querySelector('#wishlistBox');

  let rows = '';
  for (const w of data) {
    rows += `
      <tr>
        <td>
          <button type="button" onclick="wishlistDeleteOne(${w.fno})">삭제</button>
        </td>
        <td>${w.fname}</td>
      </tr>
    `;
  }
  tbody.innerHTML = rows || '<tr><td colspan="2">즐겨찾기가 없습니다.</td></tr>';
};

// // 단일 항목 삭제 → 다시 렌더
// const wishlistDeleteOne = async (fno) => {
//   await fetch('/member/wishlist/delete', {
//     method: 'POST',
//     headers: { 'Content-Type': 'application/json' },
//     // 서버가 ids 배열을 받는다고 가정. (만약 fno 단일로 받으면 { fno } 로 바꾸세요)
//     body: JSON.stringify({ ids: [fno] })
//   });
//   wishlistRenderRows();
// };

// // 페이지 진입 시 표 형태로 다시 그려주기
// document.addEventListener('DOMContentLoaded', wishlistRenderRows);


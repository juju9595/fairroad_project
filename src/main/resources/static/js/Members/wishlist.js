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

// --- 추가: 체크박스가 달린 목록 렌더 ---
const wishlistBox = async () => {
  const data = await (await fetch('/member/wishlist')).json();
  const box = document.querySelector('#wishlistBox');

  // id 필드는 wno/fno/id 중 프로젝트에 맞는 걸로 사용
  box.innerHTML = data.map(w => `
    <tr>
      <td style="text-align:center;">
      <input type="checkbox" class="wish" value="${w.fno}">
      </td>
          <td>${w.fname}</td>
        </tr>
      `).join('');
};
wishlistBox();


// --- 추가: 선택된 체크박스 삭제 ---
const wishlistDelete = async () => {
  const checked = [...document.querySelectorAll('.wish:checked')];
  if (checked.length === 0) return;

  // 컨트롤러 베이스 경로 확인하세요.
  // 아래는 @RequestMapping("/member") 기준입니다.
  for (const chk of checked) {
    const fno = chk.value;
    const option = { method: "DELETE" };
    // ★ 파라미터 이름 꼭 붙이기: ?fno=값
    const response = await fetch(`/member/wishlist/delete?fno=${encodeURIComponent(fno)}`, option);
    const ok = await response.json();
    // ok가 false면 필요 시 처리
  }
  // 삭제 후 목록 새로고침
  await wishlistBox();
};




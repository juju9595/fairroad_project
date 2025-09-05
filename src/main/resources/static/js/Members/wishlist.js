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
const wishlistRenderCheckboxes = async () => {
  const data = await (await fetch('/member/wishlist')).json();
  const box = document.querySelector('#wishlistBox');

  // id 필드는 wno/fno/id 중 프로젝트에 맞는 걸로 사용
  box.innerHTML = data.map(w => `
    <label>
      <input type="checkbox" class="wish" value="${w.wno ?? w.fno ?? w.id}">
      ${w.fname}
    </label><br/>
  `).join('');
};
wishlistRenderCheckboxes();


// --- 추가: 선택된 체크박스 삭제 ---
const wishlistDelete = async () => {
  const ids = [...document.querySelectorAll('.wish:checked')].map(el => el.value);
  if (ids.length === 0) return; // 선택 없으면 그냥 종료

  await fetch('/member/wishlist/delete', {
    method: 'POST',
    headers: { 'Content-Type': 'application/json' },
    body: JSON.stringify({ ids }) // 서버에서 ids 받아 일괄 삭제 처리
  });

  // 삭제 후 목록 다시 로드
  wishlistRenderCheckboxes();
};





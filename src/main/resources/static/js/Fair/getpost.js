const params = new URL(location.href).searchParams;
const fno = params.get('fno');

// 상세정보
const getPost = async () => {
    const response = await fetch(`/fair/getPost?fno=${fno}`);
    const data = await response.json();

    document.querySelector('.fname').innerHTML = data.fname;
    document.querySelector('.fcount').innerHTML = data.fcount;

    
// 관리자 세션 여부 확인
// 세션키가  관리자 세션키가 loginAdmin 일때
if(loginAdmin === true || loginAdmin === 'true') { 
    //getPost.jsp 패키지에 있는 <div class="etcBox"> 있는곳에 버튼 생성
    console.log(loginAdmin);
    document.querySelector('.etcBox').innerHTML = `
        <!-- 버튼 클릭 시 해당 박람회 수정 페이지로 이동 -->
        <button type="button" onclick="location.href='/Fair/fairUpdate.jsp?fno=${fno}'">수정</button>
    `;
}

    // 이미지 처리
    const img = document.createElement('img');

    // data.fimg가 경로 문자열이면, 경로를 조합
    img.src = data.fimg.startsWith('http') ? data.fimg : `/upload/${data.fimg}`;
    img.className = 'fimg';

    const container = document.querySelector('.fimg');
    container.innerHTML = '';  // 기존 이미지 제거
    container.appendChild(img);

    document.querySelector('.fprice').innerHTML = data.fprice;
    document.querySelector('.furl').innerHTML = data.furl;
    document.querySelector('.start_date').innerHTML = data.start_date;
    document.querySelector('.end_date').innerHTML = data.end_date;
    document.querySelector('.finfo').innerHTML = data.finfo;
};
getPost();

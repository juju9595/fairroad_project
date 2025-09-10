// ================================
// 박람회 상세페이지 JS (회원/비회원 방문 로그 통합)
// ================================

// ------------------------
// 1. 로그인 회원 번호 가져오기
//    - 세션스토리지, 쿠키 등에서 가져오도록 구현
//    - 없으면 null → 비회원 처리
// ------------------------
const getMemberNo = () => {
    const mno = sessionStorage.getItem('memberNo'); // 예: 세션스토리지 사용
    return mno ? parseInt(mno) : null;
};

// ------------------------
// 2. URL에서 fno 가져오기
// ------------------------
const fno = new URL(location.href).searchParams.get('fno');

// ------------------------
// 3. 박람회 상세정보 fetch + DOM 업데이트
// ------------------------
const fetchFairDetail = async (fno) => {
    try {
        const response = await fetch(`/fair/getPost?fno=${fno}`);
        if (!response.ok) throw new Error('박람회 정보 가져오기 실패');
        const data = await response.json();
        return data;
    } catch (error) {
        console.error('박람회 상세정보 fetch 에러:', error);
        return null;
    }
};

// ------------------------
// 4. DOM 업데이트 함수
// ------------------------
const renderFairDOM = (data) => {
    if (!data) return;

    document.querySelector('.fname').textContent = data.fname;
    document.querySelector('.fcount').textContent = data.fcount;
    document.querySelector('.fprice').textContent = data.fprice;
    document.querySelector('.furl').textContent = data.furl;
    document.querySelector('.start_date').textContent = data.start_date;
    document.querySelector('.end_date').textContent = data.end_date;
    document.querySelector('.finfo').innerHTML = data.finfo;

    // 관리자 세션 여부 확인
    // 세션키가 관리자 세션키가 loginAdmin 일때
    if (loginAdmin === true || loginAdmin === 'true') {
        // getPost.jsp 패키지에 있는 <div class="etcBox"> 있는곳에 버튼 생성
        console.log(loginAdmin);
        document.querySelector('.etcBox').innerHTML = `
            <!-- 버튼 클릭 시 해당 박람회 수정 페이지로 이동 -->
            <button type="button" onclick="location.href='/Fair/fairUpdate.jsp?fno=${fno}'">수정</button>
        `;
    }

    // 이미지 처리
    // 이미지가 들어갈 컨테이너 요소 선택
    const imgContainer = document.querySelector('.fimg');
    imgContainer.innerHTML = ''; // 새로 고침하면 중복 방지 위해 기존 이미지 제거

    if (data.fimg) {
        // img 태그 동적 생성
        const img = document.createElement('img');

        // data.fimg URL 확인 → http 로 시작하면 절대경로, 아니면 /upload/
        img.src = data.fimg.startsWith('http')
            ? data.fimg
            : `/upload/${data.fimg}`;

        img.className = 'fimg';
        // fimg 요소 컨테이너에 추가
        imgContainer.appendChild(img);
    }
};

// ------------------------
// 5. 방문 로그 기록 (회원/비회원 통합)
// ------------------------
const recordVisitLog = (fno) => {
    const memberNo = getMemberNo(); // 없으면 null
    fetch(`/visitlog/add?mno=${memberNo}&fno=${fno}`, {
        method: 'POST'
    })
        .then(res => res.text())
        .then(msg => console.log('방문 로그:', msg))
        .catch(err => console.error('방문 로그 저장 실패:', err));
};

// ------------------------
// 6. 페이지 초기화
//    - 상세정보 fetch → DOM 렌더 → 방문 로그 기록
// ------------------------
const initFairDetailPage = async () => {
    const data = await fetchFairDetail(fno);
    renderFairDOM(data);
    recordVisitLog(fno);
};

// ------------------------
// 7. 페이지 로드 시 실행
// ------------------------
document.addEventListener('DOMContentLoaded', initFairDetailPage);

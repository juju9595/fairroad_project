// 사용자가 요청한 URL 에서 CNO 가져오기 page 가져오기
const params = new URL(location.href).searchParams; // 현재 페이지의 URL 에서 매개변수 값 반환

const page = params.get('page')||1;     //page 존재하지 않으면 1
const key = params.get('key')||'';      //key 존재 하지 않으면''
const keyword = params.get('keyword')||'';

const allPostMain = async () =>{
    try{
        const url = `/fair/allPostMain?page=${page}&key=${key}&keyword=${keyword}`;
        const response = await fetch(url);
        const data = await response.json(); console.log(data);

        const fairMainTbody = document.querySelector('#fairMainTbody');
        const pageTitle = document.querySelector('#pageTitle');

        // 회원/비회원 구분
        if(data.data.length <= 10 ){ // 추천 알고리즘 임시로 10개 이하 지정
            pageTitle.textContent = "회원 추천 박람회";
        }else{
            pageTitle.textContent = "전체 박람회 조회"
        }

        let html=``;

// 서버에서 내려온 data.data 배열 순회
data.data.forEach((fair) => {
    // 기본 이미지 경로 (만약 fimg가 없을 때 보여줄 이미지)
    let imgPath = '/img/noimage.png'; 

    // fimg 값이 존재할 경우에만 경로를 다시 설정
    if (fair.fimg) {
        // fimg 값이 http(s)로 시작하면 → 외부 URL 그대로 사용
        // 아니라면 → /upload/ 디렉토리 밑의 파일로 경로를 조합
        imgPath = fair.fimg.startsWith('http')
            ? fair.fimg
            : `/upload/${fair.fimg}`;
    }

    // HTML 문자열 생성
    html += `
        <tr>
            <td><a href="/Fair/getpost.jsp?fno=${fair.fno}">${fair.fname}</a></td>
            <!-- 박람회 이미지 -->
            <td>
              <a href="/Fair/getpost.jsp?fno=${fair.fno}">
                <img src="${imgPath}" alt="${fair.fname}" class="fimg" style="max-width:100px;">
              </a>
            </td>
            <td><a href="/Fair/getpost.jsp?fno=${fair.fno}">${fair.fprice}</a></td>
        </tr>
    `;
});

        fairMainTbody.innerHTML = html;
        viewPageButtons(data); // 페이징 버튼 출력 함수 호출
    }catch(e){console.log(e)}//catch end
}//func end


const viewPageButtons = async(data) =>{

    let currentPage = parseInt(data.currentPage);
    let totalPage = data.totalPage;
    let startBtn = data.startBtn;
    let endBtn = data.endBtn;

    //페이징 처리시 검색 유지
    const searchURL = `&key=${key}&keyword=${keyword}`;
    const pageBtnBox = document.querySelector('.pageBtnBox');
    let html=``;
    //이전 버튼
    html +=  `<li>
                <a href="/Fair/allPostMain.jsp?page=${currentPage == 1 ? 1 : currentPage -1}${searchURL}">이전</a>
             </li>`
    //페이지 버튼
    for(let i=startBtn; i<=endBtn; i++){
        html +=  `<li>
                <a href="/Fair/allPostMain.jsp?page=${i}${searchURL}" style="${i==currentPage ? 'color:red':''}">${i}</a>
             </li>`
    }//if end

    //다음 버튼
    html +=  `<li>
                <a href="/Fair/allPostMain.jsp?page=${currentPage+1 >= totalPage ? totalPage : currentPage +1}${searchURL}">다음</a>
             </li>`
    pageBtnBox.innerHTML=html
}//func end

const onSearch = async( )=>{
    const newkey = document.querySelector('.key').value;
    const newkeyword = document.querySelector('.keyword').value;

    location.href=`allPostMain.jsp?page=${page}&key=${newkey}&keyword=${newkeyword}`;
}//func end

allPostMain();
console.log("fairPrint open");

// 사용자가 요청한 URL 에서 CNO 가져오기 page 가져오기
const params = new URL(location.href).searchParams; // 현재 페이지의 URL 에서 매개변수 값 반환

const cno = params.get('cno'); console.log(cno);
const page = params.get('page')||1;     //page 존재하지 않으면 1
const key = params.get('key')||'';      //key 존재 하지 않으면''
const keyword = params.get('keyword')||'';

const fairPrint = async () =>{
    console.log("fairPrint");
    try{
        const url = `/fair/print?cno=${cno}&page=${page}&key=${key}&keyword=${keyword}`;
        const response = await fetch(url);
        const data = await response.json(); console.log(data);

        const fairTbody = document.querySelector('#fairTbody');
        let html=``;

        data.data.forEach((fair) =>{
            html+=  `
                    <tr>
                        <td>${fair.fname}</td>
                        <td>${fair.fimg}</td>
                        <td>${fair.fprice}</td>
                        <td>${fair.fplace}</td>
                        <td>${fair.start_date}</td>
                        <td>${fair.end_date}</td>
                        <td>${fair.fcount}</td>
                    </tr>
                    `
        })
        fairTbody.innerHTML = html;
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
                <a href="/Fair/fairPrint.jsp?cno=${cno}&page=${currentPage == 1 ? 1 : currentPage -1}${searchURL}">이전</a>
             </li>`
    //페이지 버튼
    for(let i=startBtn; i<=endBtn; i++){
        html +=  `<li>
                <a href="/Fair/fairPrint.jsp?cno=${cno}&page=${i}${searchURL}" style="${i==currentPage ? 'color:red':''}">${i}</a>
             </li>`
    }//if end

    //다음 버튼
    html +=  `<li>
                <a href="/Fair/fairPrint.jsp?cno=${cno}&page=${currentPage+1 >= totalPage ? totalPage : currentPage +1}${searchURL}">다음</a>
             </li>`
    pageBtnBox.innerHTML=html
}//func end

const onSearch = async( )=>{
    const newkey = document.querySelector('.key').value;
    const newkeyword = document.querySelector('.keyword').value;

    location.href=`fairPrint.jsp?cno=${cno}&page=${page}&key=${newkey}&keyword=${newkeyword}`;
}//func end

fairPrint();
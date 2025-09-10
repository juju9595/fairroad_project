const wishParams = new URL(location.href).searchParams;
const wish = wishParams.get('fno');
console.log(wish);


// 버튼 DOM 가져오기 (박람회 상세 제목 옆 버튼)
const wishBtn = document.querySelector(".imgf .wish-btn");

//즐겨찾기 active
const wishActive = async (mno, fno) => {
    const response = await fetch(`/wish/active?mno=${mno}&fno=${fno}`);
    const data = await response.json();
    console.log("즐겨찾기active",data);
    if (data) {
        wishBtn.classList.add("active"); // 즐겨찾기 O
    } else {
        wishBtn.classList.remove("active"); // 즐겨찾기 X
    }//if end
}//func end

//즐겨찾기 추가/취소
const wishWrite = async () => {
    console.log("wishWrite.js open");

    let option = {
        method: "POST",
        headers: { "content-type": "application/json" },
        body: JSON.stringify({ fno: wish })
    };

    try {
        const response = await fetch(`/wish/write?fno=${wish}`, option);
        const text = await response.text();   // 서버에서 숫자 응답 받음
        const data = parseInt(text);          // 정수 변환
        console.log(data);

        if (data === 0) {
            alert("회원만 즐겨찾기 가능합니다.");
        } else if (data === -1) {
            alert("즐겨찾기 취소");
            wishBtn.classList.remove("active"); // 하트 비우기
        } else if (data > 0) {
            alert("즐겨찾기 등록 성공");
            wishBtn.classList.add("active");    // 하트 채움
        }
    } catch (e) {
        console.log(e);
    }
}; // func end

    // 페이지 로드 시 active 상태 확인
    document.addEventListener("DOMContentLoaded", () => {
    const mno = sessionStorage.getItem("memberNo");
    if (mno) {
        wishActive(mno, wish);
    }
});
const wishParams = new URL(location.href).searchParams;
const wish = wishParams.get('fno');
console.log(wish);

// 버튼 DOM 가져오기 (박람회 상세 제목 옆 버튼)
const wishBtn = document.querySelector(".imgf .wish-btn");

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

// [1] 로그인 함수
const login = async () => {
  const idInput = document.querySelector('.idInput');
  const pwdInput = document.querySelector('.pwdInput');
  // 1. 입력값 가져오기
  const mid = idInput.value;
  const mpwd = pwdInput.value;

  // 2. 객체화
  const obj = { mid, mpwd };
  try {
    // 3. fetch 실행
    const option = {
      method: "POST",
      headers: { "Content-Type": "application/json" },
      body: JSON.stringify(obj)
    };

    const response = await fetch("/member/login", option);
    const data = await response.json(); // 백엔드에서 mno 반환

    if (data > 0) { // 로그인 성공
      alert('로그인 성공');

      // 4. sessionStorage에 로그인 상태와 회원 번호 저장
      sessionStorage.setItem("isMember", "true");
      sessionStorage.setItem("memberNo", data); // <-- 여기 바뀜!

      // 5. 메인 페이지 이동
      location.href = "/index.jsp";
    } else {
      alert('아이디 또는 비밀번호가 다릅니다.');
    }

  } catch (error) {
    console.error("로그인 중 에러:", error);
  }
}
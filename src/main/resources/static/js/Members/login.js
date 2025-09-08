console.log("login.js open");

// [1] 로그인 함수
const login = async () => {
  const idInput = document.querySelector(".idInput");
  const pwdInput = document.querySelector(".pwdInput");

  const mid = idInput.value;
  const mpwd = pwdInput.value;
  const obj = { mid, mpwd };

  try {
    const option = {
      method: "POST",
      headers: { "Content-Type": "application/json" },
      body: JSON.stringify(obj),
    };

    const response = await fetch("/member/login", option);

    // 응답이 JSON이 아니거나 비어있을 수도 있으니 방어적으로 처리
    let payload = null;
    try {
      payload = await response.json();
    } catch {
      payload = null;
    }

    // ---- 다양한 응답 형태에 대응해서 mno 추출 ----
    // 가능성:
    // 1) 숫자 (ex: 123)
    // 2) 문자열 "123"
    // 3) 객체 { mno: 123 } 또는 { memberNo: 123 } 또는 { success:true, mno:123 } 등
    let mno = null;
    if (typeof payload === "number") {
      mno = payload;
    } else if (typeof payload === "string") {
      const n = parseInt(payload, 10);
      if (!Number.isNaN(n)) mno = n;
    } else if (payload && typeof payload === "object") {
      const cand = payload.mno ?? payload.memberNo ?? payload.id ?? null;
      if (cand != null) {
        const n = Number(cand);
        if (Number.isFinite(n)) mno = n;
      } else if (payload.success === true && payload.data && (payload.data.mno || payload.data.memberNo)) {
        const n = Number(payload.data.mno ?? payload.data.memberNo);
        if (Number.isFinite(n)) mno = n;
      }
    }

    // ---- 성공/실패 분기 ----
    if (Number.isFinite(mno) && mno > 0) {
      alert("로그인 성공");

      // 프런트 기준 로그인 상태 저장
      localStorage.setItem("isMember", "true");
      localStorage.setItem("memberNo", String(mno));

      location.href = "/index.jsp";
    } else {
      alert("아이디 또는 비밀번호가 다릅니다.");
      console.log("login fail payload:", payload);
    }
  } catch (error) {
    console.error("로그인 중 에러:", error);
    alert("로그인 중 오류가 발생했습니다.");
  }
}; // func e

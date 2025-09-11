console.log("reviewUpdate.js open");

//기존 정보 불러오기
const reviewinfo = async() =>{
  try{
    const rno = new URLSearchParams(location.search).get('rno'); //rno선언
    const option = {method : "GET"}
    const response = await fetch(`/fair/review/print2?rno=${rno}`, option);
    const data = await response.json();

    document.querySelector('.rtitle').value = data.rtitle;
    document.querySelector('.rcontent').value = data.rcontent;
  }catch(error){console.log(error)}
}
reviewinfo();//최초1번 실행

// [2] 수정처리 
const reviewUpdate = async () => {
  const params = new URLSearchParams(window.location.search);
  const rnoInUrl = params.get("rno");
  if (!rnoInUrl) {
    console.error("리뷰 번호(rno)가 없습니다.");
    return;
  }

  const rtitle = document.querySelector(".rtitle").value;
  const rcontent = document.querySelector(".rcontent").value;
  const obj = { rno: rnoInUrl, rtitle, rcontent };

  const option = {
    method: "PUT",
    headers: { "content-type": "application/json" },
    body: JSON.stringify(obj),
    // 다른 오리진에서 호출 중이면 아래 주석 해제
    // credentials: "include"
  };

  const response = await fetch("/fair/review/update", option);

  // 서버가 숫자(rno 또는 0)를 JSON으로 반환
  let n = 0;
  try {
    n = await response.json(); // 성공 시 양수 rno, 실패 시 0
  } catch (e) {
    n = 0;
  }

  if (Number(n) > 0) {
    alert("수정 성공했습니다.");
    // 서버가 돌려준 rno로 이동(혹은 rnoInUrl 사용해도 무방)
    location.href = `reviewDetail.jsp?rno=${encodeURIComponent(n)}`;
  } else {
    alert("본인 글만 수정할 수 있거나, 수정할 대상이 없습니다.");
  }
};


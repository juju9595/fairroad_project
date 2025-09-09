const wishParams = new URL(location.href).searchParams;
const wish = wishParams.get('fno'); console.log(wish);

const wishWrite = async () => {
    console.log("wishWrite.js open");
    const obj ={fno:wish};
    let option={
        method:"POST",
        headers:{"content-type":"application/json"},
        body:JSON.stringify(obj)
    }
    

    try {
        const response = await fetch(`/wish/write?fno=${wish}`,option);
        const data = await response.json();
        console.log(data);
        if (data === 0) {
            alert("회원만 즐겨찾기 가능합니다.");
        } else if (data === -1) {
            alert("즐겨찾기 취소");
        } else if (data > 0) {
            alert("즐겨찾기 등록 성공");
        }
    }catch(e){console.log(e)}//catch end
    
    
}//func end
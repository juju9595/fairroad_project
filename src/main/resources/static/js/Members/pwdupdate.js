console.log("pwdupdate.js open")

const onPwdUpdate = async()=>{


    const newpwd = document.querySelector('.newpwd').value;
    const newpwd2 = document.querySelector('.newpwd2').value;
    const oldpwd = document.querySelector('.oldpwd').value;

    // 새비밀번호 와 새비밀번호 일치 비교
    if( newpwd != newpwd2 ){ alert("새 비밀번호가 일치하지 않습니다."); return; }

    const obj = { newpwd, oldpwd };

    try{
        const option ={
            method : "PUT",
            headers : { "Content-Type" : "application/json"},
            body : JSON.stringify(obj)
        }
        const response = await fetch("/member/update/password", option);
        const data = await response.json();

        if( data == true ){
            alert("비밀번호 수정 성공~~ 다시 로그인해주세요.");
              location.href= "/Members/login.jsp"
        }else{
            alert("현재 비밀번호 가 일치하지 않습니다. ");
        }
    }catch(error){econsole.log('error');
    }

}



            ps.setString(1, map.get("newpwd"));
            ps.setInt(2, mno);
            ps.setString(3, map.get("oldpwd"));
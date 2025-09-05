<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset='utf-8'>
    <meta http-equiv='X-UA-Compatible' content='IE=edge'>
    <title>Page Title</title>
    <meta name='viewport' content='width=device-width, initial-scale=1'>

</head>
<body>
    
    <jsp:include page="/header.jsp"></jsp:include>
    <div id="container">
        <h3>회원가입</h3>
        아이디 : <input onkeyup="idCheck()" type="text" class="idInput" id="idInput"/>
        <div class="idCheck"></div>
        비밀번호 : <input onkeyup="pwdCheck()" type="password" class="pwdInput" id="pwdInput"/> <br/>
        <div class="pwdCheck"></div>
        이름 : <input type="text" class="nameInput" id="nameInput"/> <br/>
        생년월일 : <input type="text" class="birthInput" id="birthInput"/> <br/>
        연락처 : <input onkeyup="phoneCheck()" type="text" class="phoneInput" id="phoneInput"/> <br/>
        <div class="phoneCheck"></div>
        이메일 : <input onkeyup = "emailCheck()" type="text" class="emailInput" id="emailInput"/> <br/>
        <div class="emailCheck"></div>
        주소 : <input type="text" class="addressInput" id="addressInput"/> <br/>

        <button type="button" onclick="signup()">회원가입</button><br/>
        <div>
        <a href="/Members/idFind.jsp">아이디 찾기</a>
        <span>|</span>
        <a href="/Members/pwdFind.jsp">비밀번호 찾기</a>
        </div>
    </div>

    <jsp:include page="/footer.jsp"></jsp:include>
    <script src="/js/Members/signup.js"></script>




</body>
</html>
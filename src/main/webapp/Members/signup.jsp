<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
    <!DOCTYPE html>
    <html>

    <head>
        <meta charset='utf-8'>
        <meta http-equiv='X-UA-Compatible' content='IE=edge'>
        <title>Page Title</title>
        <meta name='viewport' content='width=device-width, initial-scale=1'>
        <link rel="stylesheet" href="/css/signup.css" />
        <link rel="stylesheet" href="/css/footer.css">
        

    </head>

    <body>

        <jsp:include page="/header.jsp"></jsp:include>

        <div id="container">

            <input onkeyup="idCheck()" type="text" class="idInput" id ="idInput" placeholder="아이디 입력">
            <div class="idCheck"></div>

            <input onkeyup="pwdCheck()" type="password" class="pwdInput" id="pwdInput" placeholder="비밀번호 입력">
            <div class="pwdCheck"></div>

            <input type="text" class="nameInput" id="nameInput" placeholder="이름 입력">

            <input type="text" class="birthInput" id="birthInput" placeholder="생년월일 (YYYYMMDD)">

            <input onkeyup="phoneCheck()" type="text" class="phoneInput" id="phoneInput" placeholder="연락처 입력">
            <div class="phoneCheck"></div>

            <input onkeyup="emailCheck()" type="text" class="emailInput" id="emailInput" placeholder="이메일 입력">
            <div class="emailCheck"></div>

            <input type="text" class="addressInput" id="addressInput" placeholder="주소 입력">

            <button type="button" onclick="signup()">회원가입</button>

            <div class="links">
                <a href="/Members/idFind.jsp">아이디 찾기</a>
                <span>|</span>
                <a href="/Members/pwdFind.jsp">비밀번호 찾기</a>
            </div>
        </div>


        <jsp:include page="/footer.jsp"></jsp:include>
        <script src="/js/Members/signup.js"></script>




    </body>

    </html>
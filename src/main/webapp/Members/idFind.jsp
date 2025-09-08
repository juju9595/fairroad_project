<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
    <!DOCTYPE html>
    <html>

    <head>
        <meta charset='utf-8'>
        <meta http-equiv='X-UA-Compatible' content='IE=edge'>
        <title>Page Title</title>
        <meta name='viewport' content='width=device-width, initial-scale=1'>
        <link rel="stylesheet" href="/css/idFind.css" />
        <script src='main.js'></script>
    </head>

    <body>

        <jsp:include page="/header.jsp"></jsp:include>
        <div id="container">

            <input type="text" class="nameInput" id="nameInput" placeholder="이름 입력" /><br />
            <input type="text" class="phoneInput" id="phoneInput" placeholder="연락처 입력"><br />
            <button type="button" onclick="findId()">아이디찾기</button>

            <div class="links">
                <a href="/Members/pwdFind.jsp">비밀번호 찾기</a>
                <span>|</span>
                <a href="/Members/signup.jsp">회원가입</a>
            </div>
        </div>

        <div id="resultId"></div>

        <jsp:include page="/footer.jsp"></jsp:include>
        <script src="/js/header.js"></script>
        <script src="/js/Members/idFind.js"></script>
    </body>

    </html>
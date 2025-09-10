<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
    <!DOCTYPE html>
    <html>

    <head>
        <meta charset='utf-8'>
        <meta http-equiv='X-UA-Compatible' content='IE=edge'>
        <title>Page Title</title>
        <link rel="stylesheet" href="/css/pwdFind.css" />
        <link rel="stylesheet" href="/css/footer.css">
    </head>

    <body>
        <jsp:include page="/header.jsp"></jsp:include>
        <div id="container">

            <input type="text" id="idInput" placeholder="아이디 입력">
            <input type="text" id="phoneInput" placeholder="연락처 입력">

            <button type="button" onclick="findPwd()">비밀번호 찾기</button>


            <div class="links">
                <a href="/Members/idFind.jsp">아이디 찾기</a>
                <span>|</span>
                <a href="/Members/signup.jsp">회원가입</a>
            </div>

            <div id="resultPwd"></div>
        </div>

        <jsp:include page="/footer.jsp"></jsp:include>
        <script src="/js/Members/pwdFind.js"></script>

    </body>

    </html>
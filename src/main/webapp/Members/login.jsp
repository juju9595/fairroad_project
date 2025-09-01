<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset='utf-8'>
    <meta http-equiv='X-UA-Compatible' content='IE=edge'>
    <title>Page Title</title>
    <meta name='viewport' content='width=device-width, initial-scale=1'>
    <link rel="stylesheet" href="/css/header.css" />
    <link rel="stylesheet" href="/css/footer.css" />
</head>

<body>

    <jsp:include page="/header.jsp"></jsp:include>
    <div id="container">
        <label>아이디</label>
        <input type="text" class="idInput" id="idInput" placeholder="Value"/>
        <div class="idCheck"></div>

        <label>비밀번호</label>
        <input type="password" class="pwdInput" id="pwdInput" placeholder="Value"/>

        <button type="button" onclick="login()" class="btn-primary">로그인</button>

        <div class="links">
        <a href="/member/idFind.jsp">아이디 찾기</a>
        <span>|</span>
        <a href="/member/pwdFind.jsp">아이디 찾기</a>
        <span>|</span>
         <a href="/member/signup.jsp">회원가입</a>
        </div>
    </div>

    <jsp:include page="/footer.jsp"></jsp:include>

    <script src="/js/member/login.js"></script></title>
    
</body>
</html>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset='utf-8'>
    <meta http-equiv='X-UA-Compatible' content='IE=edge'>
    <title>Page Title</title>
    <meta name='viewport' content='width=device-width, initial-scale=1'>
    <link rel='stylesheet' type='text/css' media='screen' href='main.css'>
    <script src='main.js'></script>
</head>
<body>
    <jsp:include page="/header.jsp"></jsp:include>
    <div id="container">
        <h3>비밀번호 찾기</h3>
    </div>
    아이디 : <input type="text" class="idInput" id="idInput" placeholder="아이디입"/><br/>
    연락처 : <input type="text" class="phoneInput" id="phoneInput" placeholder="연락처입력"><br/>
    <button type="button" onclick="findPwd()">비밀번호찾기</button>
    </div>

    <div id="resultPwd"></div>

    <jsp:include page="/footer.jsp"></jsp:include>
    <script src="/js/Members/pwdFind.js"></script>
    
</body>
</html>
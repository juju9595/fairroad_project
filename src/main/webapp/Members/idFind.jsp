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
        <h3>아이디 찾기</h3>
        이름 : <input type="text" class="nameInput" id="nameInput" placeholder="이름입력"/><br/>
        연락처 : <input type="text" class="phoneInput" id="phoneInput" placeholder="연락처입력"><br/>
        <button type="button" onclick="findId()">아이디찾기</button>
    </div>

    <div id="resultId"></div>

    <jsp:include page="/footer.jsp"></jsp:include>
    <script src="/js/Members/idFind.js"></script>
</body>
</html>
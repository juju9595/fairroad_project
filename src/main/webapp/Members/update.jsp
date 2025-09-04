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
        <h3> 회원정보 수정 페이지 </h3>
        <div> 회원번호 : <span class="mno"></span></div>
        <div> 아이디 : <span class="mid"></span></div>
        <div> 이름 : <input class="mname"></div>
        <div> 연락처 : <input class="mphone"></div>
        <button type="button" onclick="onUpdate()"> 정보 변경 </button>
    </div>

    <script src="/js/Members/update.js"></script>
    
</body>
</html>
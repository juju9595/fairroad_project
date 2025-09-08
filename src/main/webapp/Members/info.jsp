<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset='utf-8'>
    <meta http-equiv='X-UA-Compatible' content='IE=edge'>
    <title>Page Title</title>
    <link rel="stylesheet" href="/css/info.css" />
</head>
<body>
    
    <jsp:include page="/header.jsp"></jsp:include>

    <div id="container">
        <h3> 마이페이지 </h3>
        <div> 아이디 : <span class="mid"></span></div>
        <div> 이름 : <span class="mname"></span></div>
        <div> 연락처 : <span class="mphone"></span></div>
        <div> 생년월일 : <span class="mbirth"></span></div>
        <div> 이메일 : <span class="memail"></span></div>
        <div> 주소 : <span class="maddress"></span></div>
        <div> 가입일 : <span class="joindate"></span></div>
        <a href="/Members/update.jsp"> 회원정보 수정</a>
        <a href="/Members/pwdupdate.jsp"> 비밀번호 수정</a>
        <a href="#" onclick="onDelete()"> 회원탈퇴 </a> <br/>
     </div>

     <script src="/js/Members/info.js"></script>

    <jsp:include page="/footer.jsp"></jsp:include>
</body>
</html>
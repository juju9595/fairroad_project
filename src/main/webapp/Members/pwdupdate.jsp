<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset='utf-8'>
    <meta http-equiv='X-UA-Compatible' content='IE=edge'>
    <title>Page Title</title>
    <link rel="stylesheet" href="/css/pwdupdate.css" />
    <link rel="stylesheet" href="/css/footer.css">
</head>
<body>

    <jsp:include page="/header.jsp"></jsp:include>
    <div class="container">
        <h3> 비밀번호 변경</h3>
        <div> 현재 비밀번호 : <input type="password" class="oldpwd" /></div>
        <div> 새 비밀번호 : <input type="password" class="newpwd" /></div>
        <div> 새 비밀번호 확인 : <input type="password" class="newpwd2"  /></div>
        <button type="button" onclick="onPwdUpdate()"> 확인 </button>
        <div><a href="/Members/info.jsp">취소</a></div>
    </div>

     <jsp:include page="/footer.jsp"></jsp:include>
     <script src="/js/Members/pwdupdate.js"></script>

</body>
</html>
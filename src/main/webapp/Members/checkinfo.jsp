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
    
    <h3>내 정보 확인을 위해 비밀번호를 입력하세요</h3>

    <div class="container">
        <input type="password" class="pwdInput" placeholder="비밀번호 입력" />
        <button type="button" onclick="checkAndGo()">확인</button>
        <div id="checkMsg" style="margin-top:8px;color:#d33;"></div>
    </div>


    <jsp:include page="/footer.jsp"></jsp:include>
    <script src="/js/Members/checkinfo.js"></script>
</body>
</html>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset='utf-8'>
    <meta http-equiv='X-UA-Compatible' content='IE=edge'>
    <title>Page Title</title>
    <link rel="stylesheet" href="/css/update.css" />
    <link rel="stylesheet" href="/css/footer.css">
</head>
<body>

    <jsp:include page="/header.jsp"></jsp:include>

    <div id="container">
        <h3> 회원정보 수정 페이지 </h3>
        <div> 아이디 : <span class="mid"> </span></div>
        <div> 이름 : <span class="mname"></span></div>
        <div> 연락처 : <input class="mphone" /> </div>
        <div> 주소 : <input class="maddress"/> </div>
        <button type="button" onclick="onUpdate()"> 정보 변경 </button>
        <div><a href="/Members/info.jsp">취소</a></div>
    </div>

    <jsp:include page="/footer.jsp"></jsp:include>
    <script src="/js/Members/update.js"></script>
    
</body>
</html>
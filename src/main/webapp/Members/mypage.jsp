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
    <div id="container">
        <h3> 마이페이지 </h3>
        <ul class="listBox">
            <li><a href="/Members/info.jsp">내정보</a></li>
            <li><a href="/Members/wishList.jsp">즐겨찾기 목록</a></li>
        </ul>
    </div>

    <jsp:include page="/footer.jsp"></jsp:include>
    
</body>
</html>
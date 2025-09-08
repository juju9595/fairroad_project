<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset='utf-8'>
    <meta http-equiv='X-UA-Compatible' content='IE=edge'>
    <title>Page Title</title>
    <meta name='viewport' content='width=device-width, initial-scale=1'>
    <link rel="stylesheet" href="/css/mypage.css" />

</head>
<body>

    <jsp:include page="/header.jsp"></jsp:include>
    <div id="container">
        <h3> 마이페이지 </h3>
        <ul class="listBox">
            <li><a href="/Members/checkinfo.jsp">내정보</a></li>
            <li><a href="/Members/wishlist.jsp">즐겨찾기 목록</a></li>
        </ul>
    </div>

    <script src="/js/header.js"></script>
    <jsp:include page="/footer.jsp"></jsp:include>



</body>
</html>
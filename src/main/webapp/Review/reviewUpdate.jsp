<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset='utf-8'>
    <meta http-equiv='X-UA-Compatible' content='IE=edge'>
    <title>Page Title</title>
    <meta name='viewport' content='width=device-width, initial-scale=1'>
    <link rel="stylesheet" href="/css/reviewUpdate.css" />
    <link rel="stylesheet" href="/css/footer.css">

</head>
<body>
    <jsp:include page="/header.jsp"></jsp:include>
        <div class="updateBox">
        제목
        <input type="text" class="rtitle"><br/>
        내용
        <textarea class="rcontent"></textarea><br/>

        <button onclick="reviewUpdate()"> 수정 </button>
    </div>
        

    <jsp:include page="/footer.jsp"></jsp:include>
    <script src="/js/Review/reviewUpdate.js"></script>
    
</body>
</html>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset='utf-8'>
    <meta http-equiv='X-UA-Compatible' content='IE=edge'>
    <title>Page Title</title>
    <meta name='viewport' content='width=device-width, initial-scale=1'>
    <link rel="stylesheet" href="/css/index.css">

</head>
<body>

    <jsp:include page="/header.jsp"></jsp:include>

    <div class="category">
        <ul>
            <li><a href="/Fair/fairWrite.jsp">등록용테스트</a></li>
            <li> <a href="/Fair/allPostMain.jsp?page=1"> 임시 전체조회 </a> </li>
            <li> <a href="/Fair/allPostCategory.jsp?cno=1"> 웨딩 </a> </li>
            <li> <a href="/Fair/allPostCategory.jsp?cno=2"> 베이비 </a> </li>
            <li> <a href="/Fair/allPostCategory.jsp?cno=3"> 취업 </a> </li>
            <li> <a href="/Fair/allPostCategory.jsp?cno=4"> 애완 </a> </li>
            <li> <a href="/Fair/allPostCategory.jsp?cno=5"> 캠핑 </a> </li>
            <li> <a href="#"> 인기순(비회원 가능) </a> </li>
            <li> <a href="#"> 지역별 박람회(비회원 가능) </a> </li>
            <li> <a href="#"> 최근 본 박람회(회원제) </a> </li>
            <li> <a href="#"> 즐겨찾기 목록(회원제) </a> </li>
        </ul>
        <ul id="log-menu">
            <!----로그인 안했을때-->
        </ul>
    </div>
//
    <div class="banner">

    </div>
    
    <div class="content">

    </div>

    <script src="/js/Members/mypage.js"></script>
    <jsp:include page="/footer.jsp"></jsp:include>
    
</body>
</html>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset='utf-8'>
    <meta http-equiv='X-UA-Compatible' content='IE=edge'>
    <title>Page Title</title>
    <link rel="stylesheet" href="/css/info.css" />
    <link rel="stylesheet" href="/css/footer.css">
</head>
<body>
    
    <jsp:include page="/header.jsp"></jsp:include>

    <div id="container">
    <h3>마이페이지</h3>

    <table class="info-table">
        <tr>
            <th>아이디</th>
            <td><span class="mid"></span></td>
        </tr>
        <tr>
            <th>이름</th>
            <td><span class="mname"></span></td>
        </tr>
        <tr>
            <th>연락처</th>
            <td><span class="mphone"></span></td>
        </tr>
        <tr>
            <th>생년월일</th>
            <td><span class="mbirth"></span></td>
        </tr>
        <tr>
            <th>이메일</th>
            <td><span class="memail"></span></td>
        </tr>
        <tr>
            <th>주소</th>
            <td><span class="maddress"></span></td>
        </tr>
        <tr>
            <th>가입일</th>
            <td><span class="joindate"></span></td>
        </tr>
    </table>

    <div class="btn-group">
        <a href="/Members/update.jsp" class="btn">회원정보 수정</a>
        <a href="/Members/pwdupdate.jsp" class="btn">비밀번호 수정</a>
        <a href="#" onclick="onDelete()" class="btn danger">회원탈퇴</a>
    </div>
</div>

     <script src="/js/Members/info.js"></script>

    <jsp:include page="/footer.jsp"></jsp:include>
</body>
</html>
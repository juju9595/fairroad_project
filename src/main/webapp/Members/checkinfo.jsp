<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
    <!DOCTYPE html>
    <html>

    <head>
        <meta charset='utf-8'>
        <meta http-equiv='X-UA-Compatible' content='IE=edge'>
        <title>Page Title</title>
        <meta name='viewport' content='width=device-width, initial-scale=1'>
        <link rel="stylesheet" href="/css/checkinfo.css" />
    </head>

    <body>

        <jsp:include page="/header.jsp"></jsp:include>

        <div id="container">
            <!-- 아이디 입력 없고 비밀번호 입력만 -->
            <input type="password" class="pwdInput" id="pwdInput" placeholder="비밀번호 입력">

            <!-- 로그인 버튼 스타일 통일 -->
            <button type="button" onclick="checkAndGo()" class="btn-primary">확인</button>

            <!-- 링크 영역 추가 -->
            <div class="links">
                <a href="/Members/pwdFind.jsp">비밀번호 찾기</a>
            </div>

            <!-- 체크 메시지 영역 -->
            <div id="checkMsg" style="margin-top:8px;color:#d33;"></div>
        </div>


        <jsp:include page="/footer.jsp"></jsp:include>
        <script src="/js/Members/checkinfo.js"></script>
    </body>

    </html>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset='utf-8'>
    <meta http-equiv='X-UA-Compatible' content='IE=edge'>
    <title>Footer with Alarm</title>
    <meta name='viewport' content='width=device-width, initial-scale=1'>
    <link rel="stylesheet" href="/css/footer.css">
</head>
<body>

    <!-- 하단 전체 컨테이너 -->
<div class="bottom-bar">
    <!-- 알림 -->
    <c:if test="${not empty sessionScope.loginMno}">
        <div id="alarmBox">📭 아직 도착한 알림이 없습니다.</div>
        <script>
            const loginMno = "${sessionScope.loginMno}";
            window.loginMno = loginMno;
        </script>
        <script src="/js/Alarm/alarm.js"></script>
    </c:if>

    <!-- 푸터 메뉴 -->
    <div class="footer">
        <ul>
            <li><a href="#">회사소개</a></li>
            <li><a href="#">이용약관</a></li>
            <li><a href="#">개인정보처리방침</a></li>
            <li><a href="#">이메일무단수집거부</a></li>
        </ul>
    </div>

    <!-- 카피라이트 -->
    <div class="copy">@COPYCAT.All Rights Reserved</div>
    
</div>

</body>
</html>

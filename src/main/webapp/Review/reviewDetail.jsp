
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>리뷰 상세 조회</title>

</head>
<body>
    <jsp:include page="/header.jsp"></jsp:include>

    <h3>리뷰 상세 조회</h3>

    <table border="1" cellpadding="6" cellspacing="0">
        <thead>
            <tr>
                <th>번호</th>
                <th>제목</th>
                <th>내용</th>
                <th>작성일</th>
            </tr>
        </thead>
        <tbody class="reviewDetailTbody">

            
        </tbody>
    </table>

    <button class="delete" onclick="reviewDelete()">삭제</button>
    <button type="button" onclick="goUpdatePage()">수정</button>
    
    
    <jsp:include page="/footer.jsp"></jsp:include>

    <script src="/js/Review/reviewDetail.js"></script>
</body>
</html>
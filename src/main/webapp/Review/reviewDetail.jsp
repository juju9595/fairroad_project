
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>리뷰 상세 조회</title>
    <link rel="stylesheet" href="/css/reviewDetail.css" />
    <link rel="stylesheet" href="/css/footer.css">
</head>
<body>

    <jsp:include page="/header.jsp"></jsp:include>
    <div class="content-container">
        <div class="page-head">
            <h3>리뷰 상세 조회</h3>
            <div class="meta" id="reviewMeta"><!-- JS로 작성일/조회수 등을 넣어도 됨 --></div>
        </div>

        <div class="review-detail">
            <table>
                <thead>
                    <tr>
                        <th>번호</th>
                        <th>제목</th>
                        <th>내용</th>
                        <th>작성일</th>
                    </tr>
                </thead>
                <tbody class="reviewDetailTbody"><!-- reviewDetail.js 가 여기에 tr/td 채움 --></tbody>
            </table>
        </div>

        <div class="etcBox">
            <button type="button" onclick="reviewDelete()">삭제</button>
            <button type="button" onclick="goUpdatePage()">수정</button>
        </div>
    </div>

    <jsp:include page="/footer.jsp"></jsp:include>

    <script src="/js/Review/reviewDetail.js"></script>
</body>
</html>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
    <!DOCTYPE html>
    <html>

    <head>
        <meta charset='utf-8'>
        <meta http-equiv='X-UA-Compatible' content='IE=edge'>
        <title>박람회 상세 페이지</title>
        <meta name='viewport' content='width=device-width, initial-scale=1'>
        <link rel="stylesheet" href="/css/getPost.css" />

    </head>

    <body>
        <jsp:include page="/header.jsp"></jsp:include>
        <div class="getpostname">
            <div class="title-container">

                <img src="/img/icon.jpg" alt="아이콘" />
                <div class="fname"> </div>

                <button type="button" onclick="wishWrite()">즐겨찾기</button>
            </div>
            <div class="countf">조회수: <span class="fcount"></span></div>
            <div class="imgf"><span class="fimg"></span></div>
            <div class="pricef">가격 : <span class="fprice"></span></div>
            <div class="urlf">URL : <span class="furl"></span></div>
            <div class="date-container">
                <div class="date_start">
                    <span class="label">개최일</span>
                    <span class="start_date"></span>
                </div>
                <div class="date_end">
                    <span class="label">마감일</span>
                    <span class="end_date"></span>
                </div>
            </div>

            <div class="finfo">
                <!--상세정보-->
            </div>
            <div class="etcBox">
                <!-- 수정 버튼 생기는곳-->
            </div>

        </div>


        <div class="reviewname">
            <h3> 방문 리뷰 전체 조회 </h3>
            <a href="/Review/reviewWrite.jsp?fno=${param.fno}"> 등록페이지로 이동하기 </a>
            <table border="1" cellpadding="6" cellspacing="0">
                <thead>
                    <tr>
                        <th> 작성날짜 </th>
                        <th> 리뷰제목 </th>
                    </tr>
                </thead>
                <tbody class="reviewTbody">

                </tbody>
            </table>

        </div>
        <jsp:include page="/footer.jsp"></jsp:include>
        <script>
            // 세션에서 loginAdmin 관리자 키를 호출 후 관리자 로그인하면 null -> true 로 변경
            const loginAdmin = <%= Boolean.TRUE.equals(session.getAttribute("loginAdmin")) %>;
        </script>
        <script src='/js/Fair/getPost.js'></script>
        <script src='/js/Review/review.js'></script>
        <script src='/js/wishList/wishWrite.js'></script>

    </body>

    </html>
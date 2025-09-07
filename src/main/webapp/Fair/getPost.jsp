<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
    <!DOCTYPE html>
    <html>

    <head>
        <meta charset='utf-8'>
        <meta http-equiv='X-UA-Compatible' content='IE=edge'>
        <title>Page Title</title>
        <meta name='viewport' content='width=device-width, initial-scale=1'>


    </head>

    <body>
        <div class="getpostname">
            <button type="button" onclick="wishWrite()">즐겨찾기</button>
            <div>제목 : <span class="fname"></span>/조회수:<span class="fcount"></span></div>
            <div>이미지 : <span class="fimg"></span></div>
            <div>가격 : <span class="fprice"></span></div>
            <div>URL : <span class="furl"></span></div>
            <div>개최일 : <span class="start_date"></span></div>
            <div>마감일 : <span class="end_date"></span></div>
            <div class="finfo"> <!--상세정보-->
            </div>
            <div class="etcBox">
                <!-- 수정 버튼 생기는곳-->
            </div>
            
        </div>
        

            <div class="reviewname">
                <h3> 방문 리뷰 전체 조회 </h3>
                <a href="/Review/reviewWrite.jsp"> 등록페이지로 이동하기 </a>
                <table border="1" cellpadding="6" cellspacing="0">
                    <thead>
                        <tr>
                            <th></a> 작성날짜 </th>
                            <th> 리뷰제목 </th>
                        </tr>
                    </thead>
                    <tbody class="reviewTbody">

                    </tbody>
                </table>

            </div>
            <script>
           loginAdmin = <%= Boolean.TRUE.equals(session.getAttribute("loginAdmin")) %>;
            // 세션에서 loginAdmin 관리자 키를 호출 후 관리자 로그인하면 null -> true 로 변경
            </script>
            <script src='/js/Fair/getPost.js'></script>
            <script src='/js/Review/review.js'></script>
            <script src='/js/wishList/wishWrite.js'></script>
        
    </body>

    </html>
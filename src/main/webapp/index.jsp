<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
    <!DOCTYPE html>
    <html>

    <head>
        <meta charset='utf-8'>
        <meta http-equiv='X-UA-Compatible' content='IE=edge'>
        <title>Page Title</title>
        <meta name='viewport' content='width=device-width, initial-scale=1'>
        <link rel="stylesheet" href="/css/index.css">
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.1/css/all.min.css">


    </head>

    <body>

        <jsp:include page="/header.jsp"></jsp:include>

        <div class="category">
            <ul>
                <li><a href="/Fair/fairWrite.jsp">등록용테스트</a></li>
                <li><a href="#" data-cno="1"><i class="fa-solid fa-ring"></i> 웨딩</a></li>
                <li><a href="#" data-cno="2"><i class="fa-solid fa-baby"></i> 베이비</a></li>
                <li><a href="#" data-cno="3"><i class="fa-solid fa-briefcase"></i> 취업</a></li>
                <li><a href="#" data-cno="4"><i class="fa-solid fa-paw"></i> 애완</a></li>
                <li><a href="#" data-cno="5"><i class="fa-solid fa-campground"></i> 캠핑</a></li>
                <li><a href="#" data-url="/fair/visitlog/fcount" data-type="popular">인기순(비회원 가능)</a></li>
                <li><a href="#" data-url="/fair/visitlog/fregion" data-type="region">지역별 박람회(비회원 가능)</a></li>

                <!-- 회원용 기능 -->
                <li><a href="#" data-url="/visitlog/recent" data-type="recent">최근 본 박람회(회원제)</a></li>
                <li><a href="#" data-url="/wish/member?mno=1" data-type="favorite">즐겨찾기 목록(회원제)</a></li>
            </ul>
        </div>

        <div class="banner-slider">
            <div class="slider-track" id="slider-track">
                <div class="slide"><img src="https://placehold.co/1200x300/F582A0/FFFFFF?text=광고1" alt="광고 이미지 1"></div>
                <div class="slide"><img src="https://placehold.co/1200x300/52B2BF/FFFFFF?text=광고2" alt="광고 이미지 2"></div>
                <div class="slide"><img src="https://placehold.co/1200x300/F4D03F/FFFFFF?text=광고3" alt="광고 이미지 3"></div>
            </div>
            <div class="pagination-dots" id="pagination-dots"></div>
        </div>



    




    <div class="content-wrapper">
        <div class="search-bar">
            <select id="searchKey">
                <option value="fname"> 박람회 제목 </option>
                <option value="fplace">장소</option>
                <option value="finfo">상세정보</option>
            </select>
            <input type="text" id="searchInput" placeholder="검색어 입력">
            <button id="searchBtn">검색</button>
        </div>

        <h2 id="pageTitle">박람회 목록</h2>
        <div class="content" id="content">
            <!-- JS가 fetch로 가져온 리스트 렌더링 -->
        </div>
        <div id="pagination" class="pagination"></div>
    </div>

    <script src="/js/Members/mypage.js"></script>
    <jsp:include page="/footer.jsp"></jsp:include>

    <!-- JS -->
    <script src="/js/index.js"></script>
    <script src="/js/Alarm/alarm.js"></script>
    <script src="/js/Members/position.js"></script>

    </body>

    </html>
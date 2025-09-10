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
                <li><a href="#" data-cno="1"><i class="fa-solid fa-ring"></i> 웨딩</a></li>
                <li><a href="#" data-cno="2"><i class="fa-solid fa-baby"></i> 베이비</a></li>
                <li><a href="#" data-cno="3"><i class="fa-solid fa-briefcase"></i> 취업</a></li>
                <li><a href="#" data-cno="4"><i class="fa-solid fa-paw"></i> 애완</a></li>
                <li><a href="#" data-cno="5"><i class="fa-solid fa-campground"></i> 캠핑</a></li>
                <li><a href="#" data-url="/fair/visitlog/fcount" data-type="popular"><i class="fa-solid fa-star"></i>
                        인기순</a></li>
                <li><a href="#" data-url="/fair/visitlog/fregion" data-type="region"><i class="fa-solid fa-map"></i>
                        지역</a></li>
                <li><a href="#" data-url="/visitlog/recent" data-type="recent"><i class="fa-solid fa-clock"></i> 최근
                        본</a></li>
                <li><a href="#" data-url="/wish/member?mno=1" data-type="favorite"><i class="fa-solid fa-heart"></i> 내
                        찜</a></li>
            </ul>
        </div>
        
        <div class="banner-slider">
            <div class="slider-track" id="slider-track">
                <div class="slide">
                    <a href="https://bighorn.co.kr/">
                        <img src="/img/빅혼3.png">
                    </a>
                </div>
                <div class="slide">
                    <a href="https://gdppcat.com/">
                        <img src="/img/궁디팡팡2.png">
                    </a>
                </div>
                <div class="slide">
                    <a href="https://www.coex.co.kr/exhibitions/%EC%A0%9C48%ED%9A%8C-%EB%B2%A0%ED%8E%98-%EB%B2%A0%EC%9D%B4%EB%B9%84%ED%8E%98%EC%96%B4/">
                        <img src="/img/베이비페어2.png">
                    </a>
                </div>
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
        <script src="/js/Members/position.js"></script>

    </body>

    </html>
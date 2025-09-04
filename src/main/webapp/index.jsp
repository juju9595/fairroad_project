<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
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
                <li><a href="/Fair/allPostMain.jsp?page=1">임시 전체조회</a></li>
                <li><a href="/Fair/allPostCategory.jsp?cno=1">웨딩</a></li>
                <li><a href="/Fair/allPostCategory.jsp?cno=2">베이비</a></li>
                <li><a href="/Fair/allPostCategory.jsp?cno=3">취업</a></li>
                <li><a href="/Fair/allPostCategory.jsp?cno=4">애완</a></li>
                <li><a href="/Fair/allPostCategory.jsp?cno=5">캠핑</a></li>

                <!-- 인기순 / 지역별 / 회원용 기능 -->
                <li><a href="#" data-url="/fair/visitlog/fcount" data-type="popular">인기순(비회원 가능)</a></li>
                <li><a href="#" data-url="/fair/visitlog/fregion" data-type="region">지역별 박람회(비회원 가능)</a></li>
                <li><a href="#" data-url="/visitlog/recent" data-type="recent">최근 본 박람회(회원제)</a></li>
                <li><a href="#" data-url="/wish/member?mno=1" data-type="favorite">즐겨찾기 목록(회원제)</a></li>
            </ul>
            <ul id="log-menu">
                <!----로그인 안했을때-->
            </ul>
        </div>

        <div class="banner">
            <!-- 필요시 배너 영역 -->
        </div>

        <div class="content-wrapper">
            <div class="search-bar">
                <select id="searchKey">
                    <option value="fname">박람회 이름</option>
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

    </body>

    </html>
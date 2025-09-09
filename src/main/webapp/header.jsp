<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset='utf-8'>
    <meta http-equiv='X-UA-Compatible' content='IE=edge'>
    <title>Header</title>
    <meta name='viewport' content='width=device-width, initial-scale=1'>
    <link rel="stylesheet" href="/css/header.css">
</head>
<body>

    <div class="top">
        <!-- 메뉴 버튼 -->
        <div class="menu">
            <a href="/Members/login.jsp">
                <svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="currentColor"
                    class="bi bi-person-lines-fill" viewBox="0 0 16 16">
                    <path d="M6 8a3 3 0 1 0 0-6 3 3 0 0 0 0 6m-5 6s-1 0-1-1
                            1-4 6-4 6 3 6 4-1 1-1 1zM11 3.5a.5.5 0 0
                            1 .5-.5h4a.5.5 0 0 1 0 1h-4a.5.5
                            0 0 1-.5-.5m.5 2.5a.5.5 0 0 0
                            0 1h4a.5.5 0 0 0 0-1zm2 3a.5.5
                            0 0 0 0 1h2a.5.5 0 0 0 0-1zm0
                            3a.5.5 0 0 0 0 1h2a.5.5 0 0 0 0-1z"/>
                </svg>
            </a>

            <!-- 로그인 메뉴 -->
            <ul id="log-menu">
                <!-- JS에서 fetch로 로그인 상태 받아서 내용 채움 -->
            </ul>
        </div>

        <!-- 로고 -->
        <div class="logo">
            <a href="/index.jsp">
                <img src="/img/logo.png" alt="Logo">
            </a>
        </div>

        <!-- 검색 버튼 -->
        <div class="search">
            <svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="currentColor"
                class="bi bi-search" viewBox="0 0 16 16">
                <path d="M11.742 10.344a6.5 6.5 0 1 0-1.397 1.398h-.001q.044.06.098.115l3.85 3.85a1 1 0 0 0 1.415-1.414l-3.85-3.85a1 1 0 0 0-.115-.1zM12 6.5a5.5 5.5 0 1 1-11 0 5.5 5.5 0 0 1 11 0"/>
            </svg>
        </div>
    </div>

    <!-- 검색창 -->
    <div class="search-bar" id="search-bar">
        <select id="search-key">
            <option value="fname">제목</option>
            <option value="fplace">장소</option>
            <option value="fprice">가격</option>
            <option value="finfo">설명</option>
        </select>
        <input type="text" id="search-input" placeholder="검색어를 입력하세요..." />
        <button type="button" id="search-submit">검색</button>
        <button type="button" id="close-search">✖</button>
    </div>


    <div class="banner2">
        <img src="../resources/static/img/아이콘.png" alt="아이콘">
    </div>

    <script src="/js/header.js" defer></script>
</body>
</html>

<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
    <!DOCTYPE html>
    <html>

    <head>
        <meta charset='utf-8'>
        <meta http-equiv='X-UA-Compatible' content='IE=edge'>
        <title>박람회 상세 페이지</title>
        <meta name='viewport' content='width=device-width, initial-scale=1'>
        <link rel="stylesheet" href="/css/getPost.css" />
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" rel="stylesheet">
    </head>

    <body>
        <jsp:include page="/header.jsp"></jsp:include>

        <div class="getpostname">
            <div class="title-container">
                <img src="/img/icon.jpg" alt="아이콘" />
                <div class="fname"></div>
                <button type="button" onclick="wishWrite()"></button>
            </div>

            <div class="countf">
                조회수: <span class="fcount"></span>
            </div>

            <div class="imgf">
                <span class="fimg"></span>
            </div>
            
            <div class="price_url_container">
                <div class="pricef">
                    가격 : <span class="fprice"></span>
                    <span class="currency">원</span>

                    <div class="urlf">
                        <span class="furl"></span>
                    </div>
                </div>
            </div>


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

                <div class="etcBox">
                    <!-- 수정 버튼 생기는곳 -->
                </div>
            </div>

            <!-- 부트스트랩 탭 시작 -->
            <ul class="nav nav-tabs" id="myTab" role="tablist">
                <li class="nav-item" role="presentation">
                    <button class="nav-link active" id="home-tab" data-bs-toggle="tab" data-bs-target="#home-tab-pane"
                        type="button" role="tab" aria-controls="home-tab-pane" aria-selected="true">상세정보</button>
                </li>
                <li class="nav-item" role="presentation">
                    <button class="nav-link" id="profile-tab" data-bs-toggle="tab" data-bs-target="#profile-tab-pane"
                        type="button" role="tab" aria-controls="profile-tab-pane" aria-selected="false">리뷰</button>
                </li>
            </ul>

            <div class="tab-content" id="myTabContent">
                <!-- 상세정보 탭 -->
                <div class="tab-pane fade show active" id="home-tab-pane" role="tabpanel" aria-labelledby="home-tab"
                    tabindex="0">
                    <div class="finfo">
                        <!-- 상세정보 내용 -->
                    </div>
                </div>

                <!-- 리뷰 탭 -->
                <div class="tab-pane fade" id="profile-tab-pane" role="tabpanel" aria-labelledby="profile-tab"
                    tabindex="0">
                    <div class="reviewname">
                        <div class="review-btn-wrap">
                            <a href="/Review/reviewWrite.jsp?fno=${param.fno}">등록페이지로 이동하기</a>
                        </div>
                        <table border="1" cellpadding="6" cellspacing="0">
                            <thead>
                                <tr>
                                    <th>작성날짜</th>
                                    <th>리뷰제목</th>
                                </tr>
                            </thead>
                            <tbody class="reviewTbody">
                                <!-- 리뷰 데이터 들어가는 자리 -->
                            </tbody>
                        </table>
                    </div>
                </div>
            </div>

            <jsp:include page="/footer.jsp"></jsp:include>

            <script>
                // 세션에서 loginAdmin 관리자 키를 호출 후 관리자 로그인하면 null -> true 로 변경
                const loginAdmin = <%= Boolean.TRUE.equals(session.getAttribute("loginAdmin")) %>;
            </script>
            <script src='/js/Fair/getPost.js'></script>
            <script src='/js/Review/review.js'></script>
            <script src='/js/wishList/wishWrite.js'></script>
            <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/js/bootstrap.bundle.min.js"></script>
    </body>

    </html>
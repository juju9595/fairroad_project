<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <head>
        <meta charset='utf-8'>
        <meta http-equiv='X-UA-Compatible' content='IE=edge'>
        <title>Page Title</title>
        <meta name='viewport' content='width=device-width, initial-scale=1'>
        
        <!-- 썸머노트 용 js/css : CDN : 별도의 설치 없이 URL 웹페이지로 라이브러리 코드 불러오기-->         
        <!-- include libraries(jQuery, bootstrap) -->

        <!-- 부트스트랩 최신 버전-->
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.8/dist/css/bootstrap.min.css" rel="stylesheet">
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.8/dist/js/bootstrap.bundle.min.js" ></script>


        <!-- jquery 최신버전-->
        <script  src="http://code.jquery.com/jquery-latest.min.js"></script>
        

        <!--썸머노트 0.9.1 최신버전 CSS/JS-->
        <!-- include summernote css/js -->
        <link href="https://cdnjs.cloudflare.com/ajax/libs/summernote/0.9.1/summernote-bs5.min.css" rel="stylesheet">
        <script src="https://cdnjs.cloudflare.com/ajax/libs/summernote/0.9.1/summernote-bs5.min.js"></script>
        <!--썸머노트 0.9.1 한글 버전-->
        <script src="https://cdnjs.cloudflare.com/ajax/libs/summernote/0.9.1/lang/summernote-ko-KR.min.js"></script>

    </head>
</head>
    <body>
        <dvi id="container">
            <select class="cno">
                <option value="1">웨딩</option>
                <option value="2">베이비</option>
                <option value="3">취업</option>
                <option value="4">애완</option>
                <option value="5">캠핑</option>
            </select>
            제목:<input class="fname" />
            장소:<input class="fplace" />
            가격:<input class="fprice" />
            링크:<input class="furl" />
            개최일:<input class="start_date" />
            마감일:<input class="end_date" />
            <textarea class="finfo" id="summernote" name="editordata" ></textarea>
            <button type="button" onclick="onFairWrite()">등록</button>
        </dvi>
        <script src='/js/Fair/fairWrite.js'></script>
    </body>
</html>
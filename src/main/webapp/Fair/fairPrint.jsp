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
        <table>
            <thead>
                <tr>
                    <th> 제목 </th>
                    <th> 사진 </th>
                    <th> 가격 </th>
                    <th> 장소 </th>
                    <th> 개최일 </th>
                    <th> 마감일 </th>
                    <th> 조회수 </th>
                </tr>
            </thead>
            <tbody id="fairTbody">
                <tr>
                    
                </tr>
            </tbody>
        </table>


        <!-- 페이징 버튼 구역-->
        <div style="width: 300px;">
            <ul class="pageBtnBox" style="display: flex; justify-content: space-between;">
                <li><a href="fairPrint.jsp?cno=1&page=1">1</a></li>
                <li><a href="fairPrint.jsp?cno=1&page=2">2</a></li>
                <li><a href="fairPrint.jsp?cno=1&page=3">3</a></li>
                <li><a href="fairPrint.jsp?cno=1&page=4">4</a></li>
                <li><a href="fairPrint.jsp?cno=1&page=5">5</a></li>
            </ul>
        </div>

        <!-- 검색 버튼 구역-->
        <div style="display: flex;">
            <select class="key"> <!-- select에서 선택한 value값을 js로 이동할 예정 -->
                <option value="fname"> 제목 </option>
                <option value="finfo"> 내용 </option>
            </select>

            <div>
                <input type="text" class="keyword" placeholder="검색어 입력하세요" />
            </div>
            <div>
                <button type="button" onclick="onSearch()" ;>검색</button>
            </div>
        </div>

        <script src='/js/Fair/fairPrint.js'></script>
    </body>

    </html>
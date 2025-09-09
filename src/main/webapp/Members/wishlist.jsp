<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
    <!DOCTYPE html>
    <html>

    <head>
        <meta charset='utf-8'>
        <meta http-equiv='X-UA-Compatible' content='IE=edge'>
        <title>Page Title</title>
        <link rel="stylesheet" href="/css/wishlist.css" />
    </head>

    <body>
        <jsp:include page="/header.jsp"></jsp:include>

        <div id="container">
            <h3>즐겨찾기 목록</h3>
            <table>
                <thead>
                    <tr>
                        <th style="width:50px">선택</th>
                        <th>박람회 이름</th>
                    </tr>
                </thead>
                <tbody id="wishlistBox">
                    <tr>
                        <td colspan="2">불러오는 중...</td>
                    </tr>
                </tbody>
            </table>
            <button type="button" onclick="wishlistDelete()">삭제</button>
        </div>
        
        


        <jsp:include page="/footer.jsp"></jsp:include>
        <script src="/js/Members/wishlist.js"></script>

        <style>

        </style>
    </body>

    </html>
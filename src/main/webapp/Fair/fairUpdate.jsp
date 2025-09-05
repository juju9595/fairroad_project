<!DOCTYPE html>
<html>
<head>
    <meta charset='utf-8'>
    <meta http-equiv='X-UA-Compatible' content='IE=edge'>
    <title>Page Title</title>
    <meta name='viewport' content='width=device-width, initial-scale=1'>
</head>
     <dvi id="container">
            <form id="fairForm">
                <select name="cno">
                    <option value="1">웨딩</option>
                    <option value="2">베이비</option>
                    <option value="3">취업</option>
                    <option value="4">애완</option>
                    <option value="5">캠핑</option>
                </select>

                제목:<input name="fname" />
                장소:<input name="fplace" />
                가격:<input name="fprice" />
                링크:<input name="furl" />
                이미지:<input type="file" multiple name="uploads" />
                개최일:<input name="start_date" />
                마감일:<input name="end_date" />

                <button type="button" onclick="onFairUpdate()">수정</button>
                <textarea class="finfo" id="summernote" name="editordata"></textarea> <!--상세정보-->
            </form>
        </dvi>
        <script src='/js/Fair/fairUpdate.js'></script>
</body>
</html>
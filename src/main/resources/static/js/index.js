document.addEventListener("DOMContentLoaded", function(){
    const isMember = false; // 임시 회원/비회원 구분
    const contentEl = document.getElementById("content");

    // ----------------------------
    // 1. fetch HTML
    // ----------------------------
    function fetchHTML(url, target) {
        fetch(url)
            .then(res => res.text())
            .then(html => target.innerHTML = html)
            .catch(err => {
                target.innerHTML = "<p>데이터를 불러올 수 없습니다.</p>";
                console.error(err);
            });
    }

    // ----------------------------
    // 2. fetch JSON
    // ----------------------------
    function fetchJSON(url, renderFunction) {
        fetch(url)
            .then(res => res.json())
            .then(data => renderFunction(data))
            .catch(err => {
                contentEl.innerHTML = "<p>데이터를 불러올 수 없습니다.</p>";
                console.error(err);
            });
    }

    // ----------------------------
    // 3. 인기순 박람회 렌더링
    // ----------------------------
    function renderPopularFairs(data) {
        let html = "<h2>인기순 박람회</h2><ul class='fair-list'>";
        data.forEach(fair => {
            html += `
                <li>
                    <a href="${fair.furl}" target="_blank">${fair.fname}</a>
                    <div>장소: ${fair.fplace}</div>
                    <div>가격: ${fair.fprice}원</div>
                    <div>조회수: ${fair.fcount}</div>
                </li>
            `;
        });
        html += "</ul>";
        contentEl.innerHTML = html;
    }

    // ----------------------------
    // 4. 지역별 select + 렌더링
    // ----------------------------
    function renderRegionSelect(dataMap) {
        let html = `<h2>지역별 박람회</h2>`;
        html += `<label for="regionSelect">지역 선택:</label>`;
        html += `<select id="regionSelect"><option value="">--지역 선택--</option></select>`;
        html += `<div id="regionContent"></div>`;
        contentEl.innerHTML = html;

        const select = document.getElementById("regionSelect");
        const regionContainer = document.getElementById("regionContent");

        // select options 채우기
        Object.keys(dataMap).forEach(region => {
            const option = document.createElement("option");
            option.value = region;
            option.textContent = region;
            select.appendChild(option);
        });

        // change 이벤트 등록
        select.addEventListener("change", function(){
            const selectedRegion = this.value;
            if(!selectedRegion){
                regionContainer.innerHTML = "";
                return;
            }
            renderRegionFairs(dataMap[selectedRegion], regionContainer);
        });
    }

    // ----------------------------
    // 5. 특정 지역 박람회 렌더링
    // ----------------------------
    function renderRegionFairs(regionList, target) {
        if(!regionList || regionList.length === 0){
            target.innerHTML = "<p>해당 지역 박람회가 없습니다.</p>";
            return;
        }

        let html = "<ul class='fair-list'>";
        regionList.forEach(fair => {
            html += `
                <li>
                    <a href="${fair.furl}" target="_blank">${fair.fname}</a>
                    <div>장소: ${fair.fplace}</div>
                    <div>가격: ${fair.fprice || "정보없음"}원</div>
                </li>
            `;
        });
        html += "</ul>";
        target.innerHTML = html;
    }

    // ----------------------------
    // 6. category 클릭 이벤트 초기화
    // ----------------------------
    function initCategoryEvents() {
        document.querySelectorAll(".category a[data-type]").forEach(a => {
            a.addEventListener("click", function(e){
                e.preventDefault();
                const url = this.dataset.url;
                const type = this.dataset.type;

                if(!url) return;

                if(type === "popular"){
                    fetchJSON(url, renderPopularFairs);
                } else if(type === "region"){
                    fetchJSON(url, renderRegionSelect);
                } else {
                    fetchHTML(url, contentEl);
                }
            });
        });
    }

    // ----------------------------
    // 7. 초기 로딩
    // ----------------------------
    function init() {
        initCategoryEvents();

        // 비회원이면 임시 전체조회 자동 렌더링
        if(!isMember){
            const allEl = document.querySelector('[data-type="all"]');
            if(allEl){
                fetchHTML(allEl.dataset.url, contentEl);
            }
        }
    }

    init();
});

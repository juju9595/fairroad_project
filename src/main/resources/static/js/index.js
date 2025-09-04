document.addEventListener("DOMContentLoaded", function(){
    // ----------------------------
    // 0. 전역 변수
    // ----------------------------
    const isMember = false; // 임시 회원/비회원 구분
    const memberNo = 1;    // 임시 회원 번호 (로그인 정보에서 실제 값으로 대체)
    const contentEl = document.getElementById("content"); // 메인 콘텐츠 영역

    // ----------------------------
    // 1. HTML Fetch
    // ----------------------------
    // 지정 URL에서 HTML을 가져와 target 요소에 삽입
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
    // 2. JSON Fetch
    // ----------------------------
    // 지정 URL에서 JSON 데이터를 가져와 콜백 함수에 전달
    function fetchJSON(url, callback) {
        fetch(url)
            .then(res => res.json())
            .then(data => callback(data))
            .catch(err => {
                contentEl.innerHTML = "<p>데이터를 불러올 수 없습니다.</p>";
                console.error(err);
            });
    }

    // ----------------------------
    // 3. 공통 박람회 렌더링 함수
    // ----------------------------
    // 배열 여부 확인 + 객체도 배열로 변환 후 카드 형태로 렌더링
    function renderFairs(data, target, title = "") {
        if(!data){ // 데이터 없으면 안내문 표시
            target.innerHTML = `<p>${title} 박람회가 없습니다.</p>`;
            return;
        }

        // 배열이 아니면 객체 처리 (예: {key: [...]} 형태)
        if(!Array.isArray(data)){
            if(typeof data === 'object'){
                data = Object.values(data).flat();
            } else {
                target.innerHTML = `<p>${title} 박람회가 없습니다.</p>`;
                return;
            }
        }

        if(data.length === 0){ // 배열이 비어있으면 안내문 표시
            target.innerHTML = `<p>${title} 박람회가 없습니다.</p>`;
            return;
        }

        // 최대 10개까지만 자르기
        data = data.slice(0, 10);

        // HTML 생성
        let html = title ? `<h2>${title}</h2>` : "";
        html += "<ul class='fair-list'>";

        // 데이터 길이만큼만 반복 → 남는 빈 칸 없음
        data.forEach(fair => {
            html += `
                <li class="fair-item">
                    <img src="${fair.fimg || '/img/default.png'}" alt="${fair.fname}" class="fair-img">
                    <div class="fair-info">
                        <a href="${fair.furl}" target="_blank" class="fair-name">${fair.fname}</a>
                        <div class="fair-place">장소: ${fair.fplace}</div>
                        <div class="fair-price">가격: ${fair.fprice ? fair.fprice + '원' : '정보없음'}</div>
                        ${fair.fcount !== undefined ? `<div class="fair-count">조회수: ${fair.fcount}</div>` : ""}
                    </div>
                </li>
            `;
        });

        html += "</ul>";
        target.innerHTML = html;
    }

    // ----------------------------
    // 4. 지역별 박람회 렌더링
    // ----------------------------
    // 지역 선택 select와 선택 시 해당 지역 박람회 렌더링
    function renderRegionSelect(dataMap) {
        let html = `<h2>지역별 박람회</h2>`;
        html += `<label for="regionSelect">지역 선택:</label>`;
        html += `<select id="regionSelect"><option value="">--지역 선택--</option></select>`;
        html += `<div id="regionContent"></div>`;
        contentEl.innerHTML = html;

        const select = document.getElementById("regionSelect");
        const regionContainer = document.getElementById("regionContent");

        // select 옵션 채우기
        Object.keys(dataMap).forEach(region => {
            const option = document.createElement("option");
            option.value = region;
            option.textContent = region;
            select.appendChild(option);
        });

        // 선택 변경 시 해당 지역 박람회 렌더링
        select.addEventListener("change", function(){
            const selectedRegion = this.value;
            if(!selectedRegion){
                regionContainer.innerHTML = "";
                return;
            }

            const regionData = dataMap[selectedRegion];
            renderFairs(regionData, regionContainer, `${selectedRegion} 지역`);
        });
    }

    // ----------------------------
    // 5. 카테고리 클릭 이벤트 초기화
    // ----------------------------
    // 인기순 / 지역별 / 최근 본 / 즐겨찾기 / 기타 HTML 링크 처리
    function initCategoryEvents() {
        document.querySelectorAll(".category a[data-type]").forEach(a => {
            a.addEventListener("click", function(e){
                e.preventDefault();
                const url = this.dataset.url;
                const type = this.dataset.type;

                if(!url) return;

                if(type === "popular"){ // 인기순
                    fetchJSON(url, data => renderFairs(data, contentEl, "인기순"));
                } else if(type === "region"){ // 지역별
                    fetchJSON(url, renderRegionSelect);
                } else if(type === "recent"){ // 최근 본
                    fetchJSON(url, data => renderFairs(data, contentEl, "최근 본 박람회"));
                } else if(type === "favorite"){ // 즐겨찾기
                    fetchJSON(`/wish/member?mno=${memberNo}`, data => {
                        console.log("즐겨찾기 데이터:", data);
                        renderFairs(data.wishfair, contentEl, "즐겨찾기 목록");
                    });
                } else { // 일반 HTML 링크
                    fetchHTML(url, contentEl);
                }
            });
        });
    }

    // ----------------------------
    // 6. 초기 로딩
    // ----------------------------
    function init() {
        initCategoryEvents(); // 클릭 이벤트 초기화

        // 회원이면 페이지 로드 시 최근 본 박람회 자동 출력
        if(isMember){
            fetchJSON("/recent", data => renderFairs(data, contentEl, "최근 본 박람회"));
        }

        // 비회원이면 임시 전체조회 자동 렌더링
        if(!isMember){
            const allEl = document.querySelector('[data-type="all"]');
            if(allEl){
                fetchHTML(allEl.dataset.url, contentEl);
            }
        }
    }

    init(); // 초기화 실행
});
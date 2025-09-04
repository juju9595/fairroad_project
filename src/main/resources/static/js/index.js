document.addEventListener("DOMContentLoaded", function(){
    // ----------------------------
    // 전역 변수
    // ----------------------------
    const contentEl = document.getElementById("content");
    const pageTitleEl = document.getElementById("pageTitle");
    const paginationEl = document.getElementById("pagination");
    const searchKeyEl = document.getElementById("searchKey");
    const searchInputEl = document.getElementById("searchInput");
    const searchBtnEl = document.getElementById("searchBtn");

    const isMember = localStorage.getItem("isMember") === "true";
    const memberNo = localStorage.getItem("memberNo") ? parseInt(localStorage.getItem("memberNo")) : null;

    let currentKey = "";
    let currentKeyword = "";

    // ----------------------------
    // Fetch JSON
    // ----------------------------
    function fetchJSON(url, callback){
        fetch(url)
            .then(res => res.json())
            .then(data => callback(data))
            .catch(err => {
                contentEl.innerHTML = "<p>데이터를 불러올 수 없습니다.</p>";
                console.error(err);
            });
    }

    // ----------------------------
    // 공통 박람회 렌더링
    // container 인자를 추가해서 regionContent에도 렌더 가능
    // ----------------------------
    function renderFairs(data, container = contentEl){
        if(!data || data.length === 0){
            container.innerHTML = "<p>박람회가 없습니다.</p>";
            return;
        }

        let html = "<ul class='fair-list'>";
        data.forEach(fair => {
            html += `
                <li class="fair-item">
                    <a href="/Fair/getpost.jsp?fno=${fair.fno}">
                        <img src="${fair.fimg ? (fair.fimg.startsWith('http') ? fair.fimg : '/upload/'+fair.fimg) : '/img/default.png'}" class="fair-img" alt="${fair.fname}">
                        <div class="fair-info">
                            <div class="fair-name">${fair.fname}</div>
                            <div class="fair-place">장소: ${fair.fplace || '정보없음'}</div>
                            <div class="fair-price">가격: ${(fair.fprice != null && fair.fprice > 0) ? fair.fprice+'원' : '정보없음'}</div>
                            ${fair.fcount !== undefined ? `<div class="fair-count">조회수: ${fair.fcount}</div>` : ""}
                        </div>
                    </a>
                </li>
            `;
        });
        html += "</ul>";
        container.innerHTML = html;
    }

    // ----------------------------
    // 페이징 버튼 생성
    // ----------------------------
    function renderPagination(currentPage, startBtn, endBtn, totalPage, key, keyword){
        paginationEl.innerHTML = "";
        if(totalPage <= 1) return;

        // 이전 버튼
        if(startBtn > 1){
            const prev = document.createElement("button");
            prev.textContent = "<";
            prev.addEventListener("click", () => fetchFairs(startBtn - 1, key, keyword));
            paginationEl.appendChild(prev);
        }

        // 페이지 번호 버튼
        for(let i = startBtn; i <= endBtn; i++){
            const btn = document.createElement("button");
            btn.textContent = i;
            if(i === currentPage) btn.disabled = true;
            btn.addEventListener("click", () => fetchFairs(i, key, keyword));
            paginationEl.appendChild(btn);
        }

        // 다음 버튼
        if(endBtn < totalPage){
            const next = document.createElement("button");
            next.textContent = ">";
            next.addEventListener("click", () => fetchFairs(endBtn + 1, key, keyword));
            paginationEl.appendChild(next);
        }
    }

    // ----------------------------
    // 박람회 가져오기 (회원/비회원 구분 + 검색 + 페이지)
    // ----------------------------
    function fetchFairs(page = 1, key = "", keyword = ""){
        const count = isMember ? 6 : 6; // 회원/비회원 수 동일(필요시 조정)
        let url = `/fair/allPostMain?page=${page}&count=${count}`;
        if(key && keyword) url += `&key=${key}&keyword=${keyword}`;

        fetchJSON(url, data => {
            pageTitleEl.textContent = isMember ? "추천 박람회" : "전체 박람회";
            renderFairs(data.data); // 기본 content 영역 렌더링
            renderPagination(data.currentPage, data.startBtn, data.endBtn, data.totalPage, key, keyword);
        });
    }

    // ----------------------------
    // 검색 이벤트
    // ----------------------------
    searchBtnEl.addEventListener("click", () => {
        currentKey = searchKeyEl.value;
        currentKeyword = searchInputEl.value.trim();
        fetchFairs(1, currentKey, currentKeyword);
    });

    // ----------------------------
    // 카테고리 클릭 이벤트
    // ----------------------------
    function initCategoryEvents(){
        document.querySelectorAll(".category a[data-type]").forEach(a => {
            a.addEventListener("click", function(e){
                e.preventDefault();
                const url = this.dataset.url;
                const type = this.dataset.type;
                if(!url) return;

                // ----------------------------
                // 인기순 박람회
                // ----------------------------
                if(type === "popular"){
                const count = 6; // 한 페이지당 보여줄 수
                function fetchPopular(page = 1){
                    fetchJSON(`${url}?page=${page}&count=${count}`, data => {
                        pageTitleEl.textContent = "인기순 박람회";
                        renderFairs(data.data); // JSON에서 data 배열
                        renderPagination(
                            data.currentPage,
                            data.startBtn,
                            data.endBtn,
                            data.totalPage,
                            null,
                            null,
                            fetchPopular // 버튼 클릭 시 이 함수 호출
                        );
                    });
                }
                fetchPopular(); // 최초 1페이지 로딩
                // ----------------------------
                // 지역별 박람회
                // ----------------------------
                } else if(type === "region"){
                    fetchJSON(url, dataMap => {
                        pageTitleEl.textContent = "지역별 박람회";

                        // 1. select + regionContent 분리
                        contentEl.innerHTML = `
                            <div id="regionWrapper">
                                <label for="regionSelect">지역 선택:</label>
                                <select id="regionSelect">
                                    <option value="">-- 지역 선택 --</option>
                                </select>
                                <div id="regionContent"></div>
                            </div>
                        `;

                        const select = document.getElementById("regionSelect");
                        const regionContainer = document.getElementById("regionContent");

                        // 2. select 옵션 생성
                        Object.keys(dataMap).forEach(region => {
                            const option = document.createElement("option");
                            option.value = region;
                            option.textContent = region;
                            select.appendChild(option);
                        });

                        // 3. select change 이벤트
                        select.addEventListener("change", function(){
                            const selectedRegion = this.value;

                            if(!selectedRegion){
                                regionContainer.innerHTML = ""; // 카드만 지우기
                                return;
                            }

                            // 선택된 지역 박람회만 regionContent에 렌더링
                            renderFairs(dataMap[selectedRegion], regionContainer);
                        });

                        paginationEl.innerHTML = "";
                    });

                // ----------------------------
                // 최근 본 박람회
                // ----------------------------
                } else if(type === "recent"){
                    fetchJSON(url, data => {
                        pageTitleEl.textContent = "최근 본 박람회";
                        renderFairs(data.lastvisitfair);
                        paginationEl.innerHTML = "";
                    });

                // ----------------------------
                // 즐겨찾기 목록
                // ----------------------------
                } else if(type === "favorite"){
                    fetchJSON(`/wish/member?mno=${memberNo}`, data => {
                        pageTitleEl.textContent = "즐겨찾기 목록";
                        renderFairs(data.wishfair);
                        paginationEl.innerHTML = "";
                    });
                }
            });
        });
    }

    // ----------------------------
    // 초기 로딩
    // ----------------------------
    initCategoryEvents();
    fetchFairs();
});

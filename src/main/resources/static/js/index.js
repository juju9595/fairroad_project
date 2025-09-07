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

    // ✅ 세션스토리지 기반으로 로그인 상태 확인
    const isMember = sessionStorage.getItem("isMember") === "true";
    const memberNo = sessionStorage.getItem("memberNo") ? parseInt(sessionStorage.getItem("memberNo")) : null;

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
                    <a href="/Fair/getPost.jsp?fno=${fair.fno}">
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
    function renderPagination(currentPage, totalCount, countPerPage, callback){
        paginationEl.innerHTML = "";
        const totalPage = Math.ceil(totalCount / countPerPage);
        if(totalPage <= 1) return;

        if(currentPage > 1){
            const prev = document.createElement("button");
            prev.textContent = "<";
            prev.addEventListener("click", () => callback(currentPage - 1));
            paginationEl.appendChild(prev);
        }

        for(let i = 1; i <= totalPage; i++){
            const btn = document.createElement("button");
            btn.textContent = i;
            if(i === currentPage) btn.disabled = true;
            btn.addEventListener("click", () => callback(i));
            paginationEl.appendChild(btn);
        }

        if(currentPage < totalPage){
            const next = document.createElement("button");
            next.textContent = ">";
            next.addEventListener("click", () => callback(currentPage + 1));
            paginationEl.appendChild(next);
        }
    }

    // ----------------------------
    // 박람회 가져오기 (회원/비회원 구분 + 검색)
    // ----------------------------
    function fetchFairs(page = 1, key = "", keyword = ""){
        const count = 6;
        let url = `/fair/allPostMain?page=${page}&count=${count}`;
        if(key && keyword) url += `&key=${key}&keyword=${keyword}`;

        fetchJSON(url, data => {
            pageTitleEl.textContent = isMember ? "추천 박람회" : "전체 박람회";
            renderFairs(data.data);
            renderPagination(data.currentPage, data.totalPage * count, count, (p) => fetchFairs(p, key, keyword));
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
        document.querySelectorAll(".category a[data-type], .category a[data-cno]").forEach(a => {
            const type = a.dataset.type;
            const cno = a.dataset.cno;

            // ✅ 회원 전용 카테고리 숨김
            if((type === "recent" || type === "favorite") && !isMember){
                a.parentElement.style.display = "none";
                return;
            }

            a.addEventListener("click", function(e){
                e.preventDefault();
                const url = this.dataset.url;

                // ======================
                // 📌 cno 카테고리 (웨딩, 취업, 베이비 등)
                // ======================
                if(cno){
                    const categoryName = this.textContent;
                    const count = 6;

                    function loadCategory(page = 1){
                        fetchJSON(`/fair/allPostCategory?cno=${cno}&page=${page}&count=${count}`, data => {
                            pageTitleEl.textContent = categoryName + " 박람회";
                            renderFairs(data.data);
                            renderPagination(data.currentPage, data.totalCount, data.perCount, loadCategory);
                        });
                    }
                    loadCategory();
                    return;
                }

                // ======================
                // 📌 인기순
                // ======================
                if(type === "popular"){
                    const count = 6;
                    function fetchPopular(page = 1){
                        fetchJSON(`${url}?page=${page}&count=${count}`, data => {
                            pageTitleEl.textContent = "인기순 박람회";
                            renderFairs(data.data);
                            renderPagination(data.currentPage, data.totalCount, count, fetchPopular);
                        });
                    }
                    fetchPopular();

                // ======================
                // 📌 지역별
                // ======================
                } else if(type === "region"){
                    fetchJSON(url, dataMap => {
                        pageTitleEl.textContent = "지역별 박람회";
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

                        Object.keys(dataMap).forEach(region => {
                            const option = document.createElement("option");
                            option.value = region;
                            option.textContent = region;
                            select.appendChild(option);
                        });

                        select.addEventListener("change", function(){
                            const selectedRegion = this.value;
                            if(!selectedRegion){
                                regionContainer.innerHTML = "";
                                return;
                            }
                            renderFairs(dataMap[selectedRegion], regionContainer);
                        });
                        paginationEl.innerHTML = "";
                    });

                // ======================
                // 📌 최근 본 (회원)
                // ======================
                } else if(type === "recent"){
                    const count = 6;
                    let currentPage = 1;

                    function loadRecent(page = 1){
                        currentPage = page;
                        fetchJSON(`${url}?mno=${memberNo}&page=${page}&count=${count}`, data => {
                            pageTitleEl.textContent = "최근 본 박람회";
                            renderFairs(data.lastvisitfair);
                            renderPagination(currentPage, data.totalCount, count, loadRecent);
                        });
                    }

                    loadRecent();

                // ======================
                // 📌 즐겨찾기 (회원)
                // ======================
                } else if(type === "favorite"){
                    const count = 6;
                    let currentPage = 1;

                    function loadFavorite(page = 1){
                        currentPage = page;
                        fetchJSON(`/wish/member?mno=${memberNo}&page=${page}&count=${count}`, data => {
                            pageTitleEl.textContent = "즐겨찾기 목록";
                            renderFairs(data.wishList);
                            renderPagination(currentPage, data.totalCount, count, loadFavorite);
                        });
                    }

                    loadFavorite();
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

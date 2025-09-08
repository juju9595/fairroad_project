document.addEventListener("DOMContentLoaded", function(){
    // ===============================
    // [0] 전역 변수
    // ===============================
    const contentEl = document.getElementById("content");
    const pageTitleEl = document.getElementById("pageTitle");
    const paginationEl = document.getElementById("pagination");
    const searchKeyEl = document.getElementById("search-key");   // 검색 필드 선택
    const searchInputEl = document.getElementById("search-input");
    const searchBtnEl = document.getElementById("search-submit");

    const isMember = sessionStorage.getItem("isMember") === "true";
    const memberNo = sessionStorage.getItem("memberNo") ? parseInt(sessionStorage.getItem("memberNo")) : null;

    let currentKey = "";
    let currentKeyword = "";

    // ===============================
    // [1] Fetch JSON 유틸
    // ===============================
    function fetchJSON(url, callback){
        fetch(url)
            .then(res => res.json())
            .then(data => callback(data))
            .catch(err => {
                contentEl.innerHTML = "<p>데이터를 불러올 수 없습니다.</p>";
                console.error(err);
            });
    }

    // ===============================
    // [2] 박람회 렌더링
    // ===============================
    function renderFairs(data, container = contentEl){
        if(!data || data.length === 0){
            container.innerHTML = "<p>박람회가 없습니다.</p>";
            return;
        }

        let html = "<ul class='fair-list'>";
        data.forEach(fair => {
            // 이미지와 텍스트를 분리하는 새로운 HTML 구조
            html += `
                <li class="fair-item">
                    <a href="/Fair/getPost.jsp?fno=${fair.fno}">
                        <img src="${fair.fimg ? (fair.fimg.startsWith('http') ? fair.fimg : '/upload/'+fair.fimg) : 'https://placehold.co/300x200?text=No+Image'}" class="fair-img" alt="${fair.fname}">
                        <div class="fair-info">
                            <div class="fair-name">${fair.fname}</div>
                            <!-- 새로운 디자인에 맞춰 날짜 정보 추가 -->
                            <!-- 데이터에 날짜 정보(fstartdate, fenddate)가 있다면 이 부분을 수정해 주세요 -->
                            <div class="fair-date">25.09.04 - 09.10</div>
                        </div>
                    </a>
                </li>
            `;
        });
        html += "</ul>";
        container.innerHTML = html;
    }

    // ===============================
    // [3] 페이징 렌더링
    // ===============================
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

    // ===============================
    // [4] 박람회 가져오기 (회원/비회원 + 검색)
    // ===============================
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

    // ===============================
    // [5] 검색 이벤트
    // ===============================
    if(searchBtnEl && searchInputEl && searchKeyEl){
        searchBtnEl.addEventListener("click", () => {
            currentKey = searchKeyEl.value;
            currentKeyword = searchInputEl.value.trim();
            fetchFairs(1, currentKey, currentKeyword);
        });

        searchInputEl.addEventListener("keypress", e => {
            if(e.key === "Enter"){
                currentKey = searchKeyEl.value;
                currentKeyword = searchInputEl.value.trim();
                fetchFairs(1, currentKey, currentKeyword);
            }
        });
    }

    // ===============================
    // [6] 카테고리 이벤트 (cno / 인기 / 지역 / 최근 / 즐겨찾기)
    // ===============================
    function initCategoryEvents(){
        document.querySelectorAll(".category a[data-type], .category a[data-cno]").forEach(a => {
            const type = a.dataset.type;
            const cno = a.dataset.cno;

            // 회원 전용 숨김 처리
            if((type === "recent" || type === "favorite") && !isMember){
                a.parentElement.style.display = "none";
                return;
            }

            a.addEventListener("click", function(e){
                e.preventDefault();
                const url = this.dataset.url;

                // ---------------------------
                // 1) cno 카테고리
                // ---------------------------
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

                // ---------------------------
                // 2) 인기순
                // ---------------------------
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
                    return;
                }

                // ---------------------------
                // 3) 지역별
                // ---------------------------
                if(type === "region"){
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
                    return;
                }

                // ---------------------------
                // 4) 최근 본 (회원)
                // ---------------------------
                if(type === "recent"){
                    const count = 6;
                    function loadRecent(page = 1){
                        fetchJSON(`${url}?mno=${memberNo}&page=${page}&count=${count}`, data => {
                            pageTitleEl.textContent = "최근 본 박람회";
                            renderFairs(data.lastvisitfair);
                            renderPagination(page, data.totalCount, count, loadRecent);
                        });
                    }
                    loadRecent();
                    return;
                }

                // ---------------------------
                // 5) 즐겨찾기 (회원)
                // ---------------------------
                if(type === "favorite"){
                    const count = 6;
                    function loadFavorite(page = 1){
                        fetchJSON(`/wish/member?mno=${memberNo}&page=${page}&count=${count}`, data => {
                            pageTitleEl.textContent = "즐겨찾기 목록";
                            renderFairs(data.wishList);
                            renderPagination(page, data.totalCount, count, loadFavorite);
                        });
                    }
                    loadFavorite();
                    return;
                }

            });
        });
    }

    // ===============================
    // [7] 초기 실행
    // ===============================
    initCategoryEvents();
    fetchFairs();

    // ===============================
    // [8] 배너 슬라이더 기능
    // ===============================
    const sliderTrack = document.getElementById('slider-track');
    const paginationDots = document.getElementById('pagination-dots');
    
    // 배너에 사용할 이미지 URL 목록
    // 여기의 이미지를 실제 광고 이미지로 변경하세요.
    const images = [
        'https://placehold.co/1200x300/F582A0/FFFFFF?text=광고1',
        'https://placehold.co/1200x300/52B2BF/FFFFFF?text=광고2',
        'https://placehold.co/1200x300/F4D03F/FFFFFF?text=광고3'
    ];
    
    // 이미지에 따라 점(dot) 생성
    images.forEach((_, index) => {
        const dot = document.createElement('span');
        dot.className = 'dot';
        if (index === 0) dot.classList.add('active');
        paginationDots.appendChild(dot);
    });

    const dots = document.querySelectorAll('.dot');
    let currentIndex = 0;
    const totalSlides = images.length;

    function updateSlider() {
        const transformValue = -currentIndex * 100;
        sliderTrack.style.transform = `translateX(${transformValue}%)`;
        
        dots.forEach(dot => dot.classList.remove('active'));
        dots[currentIndex].classList.add('active');
    }

    function nextSlide() {
        currentIndex = (currentIndex + 1) % totalSlides;
        updateSlider();
    }
    
    // 5초마다 자동으로 다음 슬라이드로 이동
    setInterval(nextSlide, 5000);

    // 점(dot) 클릭 이벤트
    dots.forEach((dot, index) => {
        dot.addEventListener('click', () => {
            currentIndex = index;
            updateSlider();
        });
    });


});

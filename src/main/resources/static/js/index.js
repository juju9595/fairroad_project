document.addEventListener("DOMContentLoaded", function(){
    const contentEl = document.getElementById("content");
    const pageTitleEl = document.getElementById("pageTitle");

    const searchKeyEl = document.getElementById("search-key");
    const searchInputEl = document.getElementById("search-input");
    const searchBtnEl = document.getElementById("search-submit");

    const isMember = sessionStorage.getItem("isMember") === "true";
    const memberNo = sessionStorage.getItem("memberNo") ? parseInt(sessionStorage.getItem("memberNo")) : null;

    let currentPage = 1;
    const countPerPage = 6;
    let currentKey = "";
    let currentKeyword = "";
    let currentCategoryUrl = "";
    let loading = false;
    const shownFnoSet = new Set();

    // ===============================
    // fetch JSON
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
    // 박람회 렌더링
    // ===============================
    function renderFairs(data, container = contentEl){
        if(!data || data.length === 0){
            if(currentPage === 1) container.innerHTML = "<p>박람회가 없습니다.</p>";
            return;
        }

        let html = "";
        data.forEach(fair => {
            if(shownFnoSet.has(fair.fno)) return;
            shownFnoSet.add(fair.fno);

            html += `
                <li class="fair-item">
                    <a href="/Fair/getPost.jsp?fno=${fair.fno}">
                        <img src="${fair.fimg ? (fair.fimg.startsWith('http') ? fair.fimg : '/upload/'+fair.fimg) : 'https://placehold.co/300x200?text=No+Image'}" 
                             class="fair-img" alt="${fair.fname}">
                        <div class="fair-info">
                            <div class="fair-name">${fair.fname}</div>
                            <div class="fair-date">${fair.fstartdate} - ${fair.fenddate}</div>
                        </div>
                    </a>
                </li>
            `;
        });

        container.insertAdjacentHTML('beforeend', html);
    }

    // ===============================
    // 무한스크롤 박람회 로드
    // ===============================
    function loadFairs(){
        if(loading) return;
        loading = true;

        // 회원 비회원 url 경로 동일
        let url = currentCategoryUrl || `/fair/allPostMain?page=${currentPage}&count=${countPerPage}`;
        if(currentKey && currentKeyword) url += `&key=${currentKey}&keyword=${currentKeyword}`;

        fetchJSON(url, data => {
            if(currentPage === 1) {
                contentEl.innerHTML = "";
                shownFnoSet.clear();
            }

            // 즐겨찾기, 최근 API 대응
            const fairsData = data.data || data.wishList || data.lastvisitfair || [];
            renderFairs(fairsData);

            // 페이지 제목 업데이트
            if(data.pageTitle) pageTitleEl.textContent = data.pageTitle;

            // 다음 페이지 없으면 observer 해제
            if(!fairsData || fairsData.length < countPerPage) observer.disconnect();
            else currentPage++;

            loading = false;
        });
    }

    // ===============================
    // 무한스크롤 옵저버
    // ===============================
    const scrollAnchor = document.createElement('div');
    contentEl.parentNode.appendChild(scrollAnchor);
    const observer = new IntersectionObserver(entries => {
        if(entries[0].isIntersecting) loadFairs();
    }, { threshold: 1.0 });
    observer.observe(scrollAnchor);

    // ===============================
    // 검색 이벤트
    // ===============================
    if(searchBtnEl && searchInputEl && searchKeyEl){
        function handleSearch(){
            currentKey = searchKeyEl.value;
            currentKeyword = searchInputEl.value.trim();
            currentPage = 1;
            currentCategoryUrl = "";
            loadFairs();
        }

        searchBtnEl.addEventListener("click", handleSearch);
        searchInputEl.addEventListener("keypress", e => { if(e.key === "Enter") handleSearch(); });
    }

    // ===============================
    // 카테고리 / 추천 / 인기 / 최근 / 즐겨찾기 / 지역
    // ===============================
    document.querySelectorAll(".category a[data-type], .category a[data-cno]").forEach(a => {
        const type = a.dataset.type;
        const cno = a.dataset.cno;

        if((type === "recent" || type === "favorite") && !isMember){
            a.parentElement.style.display = "none";
            return;
        }

        a.addEventListener("click", function(e){
            e.preventDefault();
            currentPage = 1;
            shownFnoSet.clear();
            currentKey = "";
            currentKeyword = "";

            // 카테고리
            if(cno){
                currentCategoryUrl = `/fair/allPostCategory?cno=${cno}&page=${currentPage}&count=${countPerPage}`;
                pageTitleEl.textContent = this.textContent + " 박람회";
                loadFairs();
                return;
            }

            // 인기/추천/전체
            if(type === "popular" || type === "recommend"){
                currentCategoryUrl = this.dataset.url + `?page=${currentPage}&count=${countPerPage}`;
                if(isMember) currentCategoryUrl += `&mno=${memberNo}`;
                pageTitleEl.textContent = this.textContent;
                loadFairs();
                return;
            }

            // 최근 본 박람회
            if(type === "recent"){
                currentCategoryUrl = `/visitlog/recent?mno=${memberNo}&page=${currentPage}&count=${countPerPage}`;
                pageTitleEl.textContent = this.textContent;
                loadFairs();
                return;
            }

            // 즐겨찾기
            if(type === "favorite"){
                currentCategoryUrl = `/wish/member?mno=${memberNo}&page=${currentPage}&count=${countPerPage}`;
                pageTitleEl.textContent = this.textContent;
                loadFairs();
                return;
            }

            // 지역
            if(type === "region"){
                fetchJSON(this.dataset.url, dataMap => {
                    pageTitleEl.textContent = "지역별 박람회";
                    contentEl.innerHTML = `
                        <div id="regionWrapper">
                            <label for="regionSelect">지역 선택:</label>
                            <select id="regionSelect"><option value="">-- 지역 선택 --</option></select>
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
                        regionContainer.innerHTML = ""; // 이전 결과 초기화
                        const selectedRegion = this.value;
                        if(selectedRegion) renderFairs(dataMap[selectedRegion], regionContainer);
                    });

                    observer.disconnect(); // 지역 선택에서는 무한스크롤 사용 안 함
                });
                return;
            }
        });
    });

    // 초기 로드
    loadFairs();
    initCategoryEvents();

    // ===============================
    // 배너 슬라이더 (기존 그대로)
    // ===============================
    const sliderTrack = document.getElementById('slider-track');
    const paginationDots = document.getElementById('pagination-dots');
    if (sliderTrack && paginationDots) {
        const images = [
            'https://placehold.co/1200x300/F582A0/FFFFFF?text=광고1',
            'https://placehold.co/1200x300/52B2BF/FFFFFF?text=광고2',
            'https://placehold.co/1200x300/F4D03F/FFFFFF?text=광고3'
        ];

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
            sliderTrack.style.transform = `translateX(${-currentIndex * 100}%)`;
            dots.forEach(dot => dot.classList.remove('active'));
            dots[currentIndex].classList.add('active');
        }

        function nextSlide() {
            currentIndex = (currentIndex + 1) % totalSlides;
            updateSlider();
        }

        setInterval(nextSlide, 5000);

        dots.forEach((dot, index) => {
            dot.addEventListener('click', () => {
                currentIndex = index;
                updateSlider();
            });
        });
    }
});

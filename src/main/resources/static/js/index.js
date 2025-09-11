document.addEventListener("DOMContentLoaded", function () {
    const contentEl = document.getElementById("content");
    const pageTitleEl = document.getElementById("pageTitle");

    const searchKeyEl = document.getElementById("search-key");
    const searchInputEl = document.getElementById("search-input");
    const searchBtnEl = document.getElementById("search-submit");

    const isMember = sessionStorage.getItem("isMember") === "true";
    const memberNo = sessionStorage.getItem("memberNo") ? parseInt(sessionStorage.getItem("memberNo")) : null;

    let currentPage = 1;
    let countPerPage = isMember ? 5 : 10;
    let currentKey = "";
    let currentKeyword = "";
    let currentCategoryUrl = "";
    let loading = false;
    const shownFnoSet = new Set();

    // ===============================
    // fetch JSON
    // ===============================
    function fetchJSON(url, callback) {
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
    function renderFairs(data, container = contentEl) {
        if (!data || data.length === 0) {
            if (currentPage === 1) container.innerHTML = "<p>박람회가 없습니다.</p>";
            return;
        }

        if (container.tagName !== "UL") {
            const ul = document.createElement("ul");
            container.appendChild(ul);
            container = ul;
        }

        let html = "";
        const isRecommend = currentCategoryUrl.includes("recommend=true");
        const isRecent = currentCategoryUrl.includes("/visitlog/recent");
        const isRegion = container.id === "regionContent";

        data.forEach(fair => {
            // ✅ 회원 추천만 중복 체크 무시, 나머지는 체크
            if (!isRecommend && !isRecent && !isRegion) {
                if (shownFnoSet.has(fair.fno)) return;
                shownFnoSet.add(fair.fno);
            }

            html += `
        <li class="fair-item">
            <a href="/Fair/getPost.jsp?fno=${fair.fno}">
                <img src="${fair.fimg ? (fair.fimg.startsWith('http') ? fair.fimg : '/upload/' + fair.fimg) : 'https://placehold.co/300x200?text=No+Image'}" 
                     class="fair-img" alt="${fair.fname}">
                <div class="fair-info">
                    <div class="fair-name">${fair.fname}</div>
                    <div class="fair-date">${fair.start_date || ''} - ${fair.end_date || ''}</div>
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
    function loadFairs() {
        console.log('무한스크롤시작');
        if (loading) return;
        loading = true;
        console.log('무한스크롤중');
        let url = currentCategoryUrl || `/fair/allPostMain?page=${currentPage}&count=${countPerPage}`;
        if (currentKey && currentKeyword) url += `&key=${currentKey}&keyword=${currentKeyword}`;

        fetchJSON(url, data => {
            let fairsData = data.data || data.wishList || data.lastvisitfair || [];

            if (currentPage === 1) {
                contentEl.innerHTML = "";
                shownFnoSet.clear();
            }

            renderFairs(fairsData);

            // 무한스크롤 종료 판단
            if (data.lastPage || fairsData.length === 0) {
                observer.disconnect();
            } else {
                currentPage++;
            }

            loading = false;
        });
    }

    // ===============================
    // 무한스크롤 옵저버
    // ===============================
    const scrollAnchor = document.createElement('div');
    contentEl.parentNode.appendChild(scrollAnchor);
    const observer = new IntersectionObserver(entries => {
        if (entries[0].isIntersecting) loadFairs();
    }, { threshold: 1.0 });
    observer.observe(scrollAnchor);

    // ===============================
    // 검색 이벤트
    // ===============================
    if (searchBtnEl && searchInputEl && searchKeyEl) {
        function handleSearch() {
            currentKey = searchKeyEl.value;
            currentKeyword = searchInputEl.value.trim();
            currentPage = 1;
            currentCategoryUrl = "";
            loadFairs();
        }
        searchBtnEl.addEventListener("click", handleSearch);
        searchInputEl.addEventListener("keypress", e => { if (e.key === "Enter") handleSearch(); });
    }

    // ===============================
    // 카테고리 / 추천 / 인기 / 최근 / 즐겨찾기 / 지역
    // ===============================
    document.querySelectorAll(".category a[data-type], .category a[data-cno]").forEach(a => {
        const type = a.dataset.type;
        const cno = a.dataset.cno;

        if ((type === "recent" || type === "favorite") && !isMember) {
            a.parentElement.style.display = "none";
            return;
        }

        a.addEventListener("click", function (e) {
            e.preventDefault();
            currentPage = 1;
            currentKey = "";
            currentKeyword = "";

            // 카테고리
            if (cno) {
                currentCategoryUrl = `/fair/allPostCategory?cno=${cno}&page=${currentPage}&count=${countPerPage}`;
                pageTitleEl.textContent = this.textContent + " 박람회";
                loadFairs();
                return;
            }

            // 인기/추천
            if (type === "popular" || type === "recommend") {
                currentCategoryUrl = this.dataset.url + `?page=${currentPage}&count=${countPerPage}`;
                if (isMember) currentCategoryUrl += `&mno=${memberNo}`;
                pageTitleEl.textContent = this.textContent;
                loadFairs();
                return;
            }

            // 최근 본 박람회
            if (type === "recent") {
                currentCategoryUrl = `/visitlog/recent?mno=${memberNo}&page=${currentPage}&count=${countPerPage}`;
                pageTitleEl.textContent = this.textContent;
                loadFairs();
                return;
            }

            // 즐겨찾기
            if (type === "favorite") {
                currentCategoryUrl = `/wish/member?mno=${memberNo}&page=${currentPage}&count=${countPerPage}`;
                pageTitleEl.textContent = this.textContent;
                loadFairs();
                return;
            }

            // 지역
            if (type === "region") {
                fetchJSON(this.dataset.url, dataMap => {
                    pageTitleEl.textContent = "지역별 박람회";
                    contentEl.innerHTML = `
                        <div id="regionWrapper">
                            <label for="regionSelect">지역 선택:</label>
                            <select id="regionSelect"><option value="">-- 지역 선택 --</option></select>
                            <ul id="regionContent"></ul>
                        </div>
                    `;
                    const select = document.getElementById("regionSelect");
                    const regionContainer = document.getElementById("regionContent");

                    // select 옵션 생성
                    Object.keys(dataMap).forEach(region => {
                        const option = document.createElement("option");
                        option.value = region;
                        option.textContent = region;
                        select.appendChild(option);
                    });

                    // 선택 시 렌더링
                    select.addEventListener("change", function () {
                        regionContainer.innerHTML = "";
                        const selectedRegion = this.value;
                        if (selectedRegion && dataMap[selectedRegion]) {
                            renderFairs(dataMap[selectedRegion], regionContainer);
                        }
                    });

                    // 기본 첫 지역 자동 렌더링
                    const firstRegion = Object.keys(dataMap)[0];
                    if (firstRegion) {
                        select.value = firstRegion;
                        renderFairs(dataMap[firstRegion], regionContainer);
                    }

                    observer.disconnect();
                });
                return;
            }
        });
    });

    // ===============================
    // 초기 로드
    // ===============================
    if (isMember) {
        currentCategoryUrl = `/fair/allPostMain?page=${currentPage}&count=${countPerPage}&mno=${memberNo}&recommend=true`;
        pageTitleEl.textContent = "추천 박람회";
    } else {
        currentCategoryUrl = `/fair/allPostMain?page=${currentPage}&count=${countPerPage}`;
        pageTitleEl.textContent = "전체 박람회";
    }

    loadFairs();

    // ===============================
    // 배너 슬라이더
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

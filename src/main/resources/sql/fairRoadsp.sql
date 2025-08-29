drop database if exists fairRoad;
create database fairRoad;
use fairRoad;

-- ----------------------- 회원 목록 테이블 -------------------

create table members(
   mno int auto_increment ,             -- 회원 고유번호
    mid varchar(30) unique not null ,       -- 회원 아이디
    mpwd varchar(100) not null ,         -- 회원 비밀번호
    mname varchar(30) not null ,          -- 회원 이름
    mbirth date not null ,                -- 회원 생년월일
    mphone varchar(30) not null ,         -- 회원 연락처
    memail varchar(100) not null ,          -- 회원 이메일
    joindate date default(current_date()),   -- 가입일
    maddress varchar(50) ,               -- 회원 주소
    constraint primary key(mno)
);

insert into members ( mid , mpwd , mname , mbirth , mphone , memail , joindate , maddress ) values
('qwe123', 'asd1234', '유재석', '1972-08-14', '010-1111-2222', 'yjs001@example.com', '2025-03-01', '서울특별시 마포구 상암동 100'),
('rty234', 'fgh5678', '이순재', '1944-04-02', '010-2222-3333', 'ljs002@example.com', '2025-03-05', '서울특별시 종로구 삼청동 50'),
('uio345', 'jkl9012', '김수현', '1988-02-16', '010-3333-4444', 'ksh003@example.com', '2025-03-10', '서울특별시 강남구 청담동 77'),
('pas456', 'zxc3456', '박서준', '1988-12-16', '010-4444-5555', 'pjs004@example.com', '2025-03-12', '서울특별시 용산구 이태원동 22'),
('dfg567', 'vbn6789', '송중기', '1985-09-19', '010-5555-6666', 'sns005@example.com', '2025-03-15', '서울특별시 강남구 논현동 88'),
('hjk678', 'mnb1234', '정해인', '1988-04-01', '010-6666-7777', 'jhy006@example.com', '2025-03-18', '서울특별시 성동구 성수동 12'),
('klz789', 'qaz5678', '이종석', '1989-09-14', '010-7777-8888', 'ljs007@example.com', '2025-03-20', '서울특별시 서초구 반포동 33'),
('xsw890', 'wsx6789', '한소희', '1996-11-18', '010-8888-9999', 'hsj008@example.com', '2025-03-22', '서울특별시 강서구 화곡동 44'),
('edc901', 'rfv1234', '김다미', '1995-04-09', '010-9999-0000', 'kdy009@example.com', '2025-03-25', '서울특별시 송파구 잠실동 55'),
('rfv012', 'tgb5678', '서지혜', '1984-10-24', '010-1010-2020', 'sjl010@example.com', '2025-03-28', '서울특별시 마포구 합정동 66');

select * from members;

-- ----------------------- 박람회 목록 , 카테고리 목록 테이블 -------------------

create table category(
   cno int auto_increment ,            -- 카테고리 고유번호
    cname varchar(50) not null,            -- 카테고리 이름
    constraint primary key(cno)
);

insert into category (cname) values
('웨딩') , ('베이비') , ('취업') , ('애완') , ('캠핑'); 

create table fair(
   fno int auto_increment ,            -- 박람회 고유번호
    cno int,                         -- 카테고리 고유번호
    fname varchar(100) not null ,          -- 박람회 이름
    fimg varchar(255),                  -- 박람회 대표 이미지
    fplace varchar(50) ,               -- 박람회 개최 장소
    fprice int default 0 ,               -- 박람회 입장권 가격
    furl varchar(255),                  -- 박람회 URL
    finfo longtext,                     -- 박람회 부가정보
    start_date date not null ,            -- 박람회 개최일
    end_date date not null ,            -- 박람회 종료일
    fcount int default 0 ,               -- 박람회 사이트 조회수
    constraint primary key(fno),
    foreign key(cno) references category(cno)
);

insert into fair ( cno , fname , fimg , fplace , fprice , furl , finfo , start_date , end_date , fcount) values
(1, '서울 국제 게임 전시회 2025', 'https://example.com/images/gamefair2025.jpg', '서울 코엑스', 15000, 'https://www.seoulgamefair2025.com', '최신 게임과 VR 체험, e스포츠 경기 등을 즐길 수 있는 대규모 전시회입니다.', '2025-09-10', '2025-09-15', 0),
(2, '부산 커피&디저트 박람회', 'https://example.com/images/coffeeexpo2025.jpg', '부산 벡스코', 12000, 'https://www.busancoffeeexpo.com', '국내외 다양한 커피와 디저트 브랜드가 참가하는 박람회입니다.', '2025-06-05', '2025-06-07', 0),
(3, '대구 국제 뷰티 박람회', 'https://example.com/images/beautyexpo2025.jpg', '대구 EXCO', 10000, 'https://www.daegubeautyexpo.com', '최신 뷰티 제품과 화장품 체험 프로그램 제공.', '2025-07-15', '2025-07-18', 0),
(4, '광주 친환경 농업 박람회', 'https://example.com/images/agroexpo2025.jpg', '광주 김대중컨벤션센터', 8000, 'https://www.gwangjuagroexpo.com', '친환경 농산물과 농업 기술을 소개하는 박람회.', '2025-05-20', '2025-05-23', 0),
(5, '인천 스마트 시티 전시회', 'https://example.com/images/smartcity2025.jpg', '인천 송도컨벤시아', 20000, 'https://www.incheonsmartcity.com', '스마트 시티 관련 최신 기술과 솔루션 전시.', '2025-10-01', '2025-10-05', 0),
(1, '서울 국제 자동차 박람회', 'https://example.com/images/car2025.jpg', '서울 삼성동 코엑스', 18000, 'https://www.seoulcarfair2025.com', '최신 자동차와 모빌리티 기술 전시.', '2025-08-10', '2025-08-15', 0),
(2, '부산 해양 스포츠 박람회', 'https://example.com/images/marinesport2025.jpg', '부산 해운대 해변', 15000, 'https://www.busanmarinesport.com', '해양 스포츠 체험과 장비 전시.', '2025-07-01', '2025-07-03', 0),
(3, '대전 로봇&AI 박람회', 'https://example.com/images/robotai2025.jpg', '대전 컨벤션센터', 12000, 'https://www.daejeonrobotai.com', '최신 로봇 기술과 AI 솔루션 전시.', '2025-09-20', '2025-09-22', 0),
(4, '광주 문화 예술 박람회', 'https://example.com/images/cultureexpo2025.jpg', '광주 비엔날레 전시장', 10000, 'https://www.gwangjucultureexpo.com', '국내외 예술 작품과 문화 체험.', '2025-06-10', '2025-06-14', 0),
(5, '인천 국제 항공 박람회', 'https://example.com/images/airexpo2025.jpg', '인천 송도컨벤시아', 25000, 'https://www.incheonairexpo.com', '항공기 전시와 최신 항공 기술 소개.', '2025-11-05', '2025-11-10', 0),
(1, '서울 IT 스타트업 박람회', 'https://example.com/images/itstartup2025.jpg', '서울 코엑스', 10000, 'https://www.seoulitstartup.com', '스타트업과 IT 기술을 연결하는 박람회.', '2025-04-10', '2025-04-12', 0),
(2, '부산 국제 와인 박람회', 'https://example.com/images/wineexpo2025.jpg', '부산 벡스코', 20000, 'https://www.busanwineexpo.com', '전세계 다양한 와인을 시음하고 체험할 수 있는 박람회.', '2025-05-15', '2025-05-18', 0),
(3, '대구 건강&웰니스 박람회', 'https://example.com/images/healthwellness2025.jpg', '대구 EXCO', 8000, 'https://www.daeguhealthwellness.com', '건강 관련 제품과 웰니스 체험 프로그램.', '2025-08-05', '2025-08-08', 0),
(4, '광주 전통 공예 박람회', 'https://example.com/images/craftexpo2025.jpg', '광주 김대중컨벤션센터', 5000, 'https://www.gwangjucraftexpo.com', '전통 공예품 전시 및 체험 행사.', '2025-09-01', '2025-09-03', 0),
(5, '인천 국제 전자기기 박람회', 'https://example.com/images/electroexpo2025.jpg', '인천 송도컨벤시아', 18000, 'https://www.incheonelectro.com', '최신 전자제품과 스마트 기기 전시.', '2025-10-20', '2025-10-25', 0);

select * from fair;
select * from category;

-- ----------------------- 즐겨찾기 목록 테이블 -------------------

create table wishlist(
   wno int auto_increment ,             -- 즐겨찾기 고유번호
    mno int ,                         -- 회원 고유번호
    fno int ,                        -- 박람회 고유번호
    constraint primary key(wno),
    foreign key(mno) references members(mno) on delete cascade,
    foreign key(fno) references fair(fno) on delete cascade , 
    unique(mno,fno)                     -- 같은 회원이 같은 박람회 중복 즐겨찾기 불가
    -- on delete cascade : 부모 데이터가 삭제되면, 자식 데이터도 같이 삭제됨
);

insert into wishlist ( mno , fno ) values
(1, 1), (1, 5), (2, 2), (2, 7), (3, 3), (3, 10), (4, 4), (4, 12), 
(5, 5), (5, 9), (6, 6), (6, 14), (7, 7), (8, 8),(9, 11), (10, 15);

select * from wishlist;

-- ----------------------- 박람회 후기 테이블 -------------------

create table review(
   rno int auto_increment ,            -- 리뷰 고유번호
    mno int ,                         -- 회원 고유번호
    fno int ,                        -- 박람회 고유번호
    rcontent text not null ,            -- 리뷰 내용
    rdate date default (current_date()),   -- 리뷰 작성일자
    constraint primary key(rno) ,
    foreign key(mno) references members(mno) on delete cascade ,
    foreign key(fno) references fair(fno) on delete cascade 
);

insert into review ( mno , fno , rcontent , rdate ) values
(3, 7, '다양한 체험과 볼거리가 많아서 즐거웠어요.', '2025-03-05'),
(1, 12, 'VR과 최신 기술 체험이 인상적이었음.', '2025-03-06'),
(5, 3, '화장품 체험이 많아서 시간 가는 줄 몰랐습니다.', '2025-03-07'),
(8, 1, '게임 박람회라 신기한 것들이 많았어요!', '2025-03-08'),
(2, 9, '문화 예술 작품이 다양하고 흥미로웠습니다.', '2025-03-09'),
(10, 4, '친환경 농업 박람회, 가족과 함께 다녀오기 좋았어요.', '2025-03-10'),
(6, 14, '스마트 시티 전시회, 최신 기술을 직접 볼 수 있었음.', '2025-03-11'),
(7, 11, '해양 스포츠 체험이 재미있었어요.', '2025-03-12'),
(4, 6, '자동차 박람회, 최신 모델 구경이 즐거웠습니다.', '2025-03-13'),
(9, 15, '항공 박람회, 다양한 항공기 전시가 인상적이었어요.', '2025-03-14');

select * from review;

-- ----------------------- 사이트 방문 로그 테이블 -------------------

create table visitlog(
   vno int auto_increment ,               -- 방문로그 고유번호
    mno int null ,                       -- 비회원도 방문 가능하니 null 허용
    fno int null ,                       -- 특정 박람회 상세 페이지 방문시 기록
    vdate datetime default current_timestamp, -- 사이트 방문일자 (추후 시간별 분석 가능)
    constraint primary key(vno),
    foreign key(mno) references members(mno) on delete set null,
    foreign key(fno) references fair(fno) on delete set null
    -- on delete set null : 부모 데이터 삭제 시, 자식 FK 값을 NULL로 만들어서 데이터는 유지하되 관계만 끊는다
    -- 회원(members)이 삭제되면 visitlog.mno = null , (로그 기록은 유지) 
    -- 박람회(fair)가 삭제되면 visitlog.fno = null , (방문 로그 남김)
);

insert into visitlog ( mno , fno , vdate ) values
(1, 3, '2025-03-01 10:05:23'),
(2, 7, '2025-03-01 11:12:45'),
(3, 1, '2025-03-02 09:45:12'),
(4, 10, '2025-03-02 14:32:50'),
(5, 5, '2025-03-03 16:20:11'),
(6, 12, '2025-03-03 17:05:33'),
(7, 2, '2025-03-04 10:55:47'),
(8, 9, '2025-03-04 13:18:22'),
(9, 6, '2025-03-05 15:42:19'),
(10, 14, '2025-03-05 18:10:05'),
(1, 8, '2025-03-06 11:25:44'),
(2, 11, '2025-03-06 12:55:30'),
(3, 4, '2025-03-07 09:40:17'),
(4, 13, '2025-03-07 14:12:08'),
(5, 1, '2025-03-08 10:05:55'),
(6, 15, '2025-03-08 16:33:44'),
(7, 3, '2025-03-09 09:50:21'),
(8, 7, '2025-03-09 13:05:16'),
(9, 2, '2025-03-10 11:22:30'),
(NULL, 5, '2025-03-10 12:45:55');  -- 비회원 방문 예시

select * from visitlog;
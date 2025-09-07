package web.controller;


import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import web.model.dto.PageDto;
import web.model.dto.ReviewDto;
import web.service.ReviewService;


@RestController
@RequiredArgsConstructor
@RequestMapping("/fair/review")
public class ReviewController { // class start

    private final ReviewService reviewService;

    // [1] 방문 리뷰 등록 (로그인 필수)
    @PostMapping("/write")
    public ResponseEntity<?> reviewWrite(@RequestBody ReviewDto reviewDto, HttpSession session) {
        // 1. 세션에서 로그인 회원번호 확인
        Integer loginMno = (Integer) session.getAttribute("loginMno");
        if (loginMno == null) {
            // 로그인 안 된 경우 -> 401 Unauthorized
            return ResponseEntity
                    .status(HttpStatus.UNAUTHORIZED)
                    .body("로그인이 필요합니다.");
        }

        // 2. 로그인 회원번호 주입
        reviewDto.setMno(loginMno);

        // 3. rdate가 비어있으면 오늘 날짜로
        if (reviewDto.getRdate() == null) {
            reviewDto.setRdate(java.time.LocalDate.now().toString());
        }

        // 4. 서비스 호출
        int rno = reviewService.reviewWrite(reviewDto);
        if (rno > 0) {
            return ResponseEntity.ok(rno);
        }
        return ResponseEntity.status( HttpStatus.INTERNAL_SERVER_ERROR ).body("등록 실패");
    }
    // [2] 방문 리뷰 전체 조회
    // spec: GET /fair/review/print (queryString: 없음)
    @GetMapping("/print")
    public PageDto reviewPrint(
            @RequestParam int fno,
            @RequestParam(defaultValue = "1") int page,       // 현재 페이지 번호
            @RequestParam(defaultValue = "5") int count       // 페이지당 개수
    ) {
        return reviewService.reviewPrint(fno, page, count);
    }


    // [3] 방문 리뷰 개별 조회 (안전하게 반환)
    @GetMapping("/print2")
    public ResponseEntity<ReviewDto> reviewPrint2( @RequestParam(required = false) Integer rno) {
        // 1. rno가 없거나 잘못 들어온 경우
        if (rno == null) {
            // ResponseEntity :  Spring의 HTTP 응답(Response) 을 감싸는 객체
            return ResponseEntity.badRequest().build(); // 400 Bad Request
        }

        // 2. 서비스 호출
        ReviewDto dto = reviewService.reviewPrint2(rno);

        // 3. 결과가 있으면 200 OK, 없으면 404 Not Found
        if (dto != null) {
            return ResponseEntity.ok(dto);
        } else {
            return ResponseEntity.notFound().build();
        }
    }


    // [4] 방문 리뷰 수정
    // [PUT 요청] 클라이언트에서 "/update" 주소로 PUT 방식 요청이 들어오면 실행
    @PutMapping("/review/update")
    public int reviewUpdate(@RequestBody ReviewDto reviewDto, HttpSession session) {
        Integer loginMno = (Integer) session.getAttribute("loginMno"); // 로그인한 회원번호
        if (loginMno == null) return 0; // 로그인 안됨
        return reviewService.reviewUpdate(reviewDto.getRno(), loginMno, reviewDto.getRtitle(), reviewDto.getRcontent());
    }

    // [5] 방문 리뷰 삭제
    @DeleteMapping("/review/delete")
    public boolean reviewDelete(@RequestParam int rno, HttpSession session) {
        Integer loginMno = (Integer) session.getAttribute("loginMno");
        if (loginMno == null) return false;
        return reviewService.reviewDelete(rno, loginMno);
    }




} // class end

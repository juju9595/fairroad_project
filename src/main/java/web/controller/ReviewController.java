package web.controller;


import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import web.model.dto.ReviewDto;
import web.service.ReviewService;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/fair/review")
public class ReviewController { // class start

    private final ReviewService reviewService;

    // [1] 방문 리뷰 등록 (로그인 필수)
    @PostMapping("/write")
    public ResponseEntity<?> reviewWrite(@RequestBody ReviewDto reviewDto , HttpSession session ) {
        Integer loginMno = (Integer) session.getAttribute("loginMno");
        if (loginMno == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("LOGIN_REQUIRED");
        }

        // 로그인 회원번호 주입
        reviewDto.setMno(loginMno);

        // rdate가 비어있으면 오늘 날짜로
        if (reviewDto.getRdate() == null) {
            reviewDto.setRdate(java.time.LocalDate.now().toString());
        }

        int rno = reviewService.reviewWrite(reviewDto);
        if (rno > 0) {
            return ResponseEntity.status(HttpStatus.CREATED).body(Map.of("rno", rno));
        }
        return ResponseEntity.badRequest().body("FAIL");
    }

    // [2] 방문 리뷰 전체 조회
    // spec: GET /fair/review/print (queryString: 없음)
    @GetMapping("/print")
    public List< ReviewDto > reviewPrint(){
        return reviewService.reviewPrint();
    }


    // [3] 방문 리뷰 개별 조회 (안전하게 반환)
    @GetMapping("/print2")
    public ResponseEntity<ReviewDto> reviewPrint2( @RequestParam(required = false) Integer rno) {
        // 1. rno가 없거나 잘못 들어온 경우
        if (rno == null) {
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
    @PutMapping("/update")
    public int reviewUpdate( @RequestBody ReviewDto reviewDto ) {
        if ( reviewDto.getRno() <= 0 ) return 0;
        if ( reviewDto.getRcontent() == null || reviewDto.getRcontent().isBlank() ) return 0;

        return reviewService.reviewUpdate(reviewDto.getRno(), reviewDto.getRtitle() , reviewDto.getRcontent() );
    }

    // [5] 방문 리뷰 삭제
    @DeleteMapping("")
    public boolean reviewDelete( int rno ){
        return reviewService.reviewDelete( rno );
    }




} // class end

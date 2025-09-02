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
    private final HttpSession session;

    // [1] 방문 리뷰 등록 (로그인 필수)
    @PostMapping("/write")
    public ResponseEntity<?> reviewWrite(@RequestBody ReviewDto reviewDto) {
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



    // [5] 방문 리뷰 삭제
    @DeleteMapping("")
    public boolean reviewDelete( int rno ){
        return reviewService.revieweDelete( rno );
    }




} // class end

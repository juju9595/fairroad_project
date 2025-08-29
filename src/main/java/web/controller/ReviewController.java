package web.controller;


import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;
import web.service.ReviewService;

@RestController
@RequiredArgsConstructor
public class ReviewController { // class start

    private final ReviewService reviewService;
} // class end

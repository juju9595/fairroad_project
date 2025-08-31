package web.controller;


import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;
import web.service.ReviewService;

@RestController
@RequiredArgsConstructor
public class ReviewController { // class start
    @Autowired
    private ReviewService reviewService;
} // class end

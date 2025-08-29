package web.controller;


import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;
import web.service.FairService;

@RestController
@RequiredArgsConstructor
public class FairController { // class start


    private final FairService fairService;
} // class end

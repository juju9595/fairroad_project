package web.controller;


import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;
import web.service.FairService;

@RestController
@RequiredArgsConstructor
public class FairController { // class start

    @Autowired
    private FairService fairService;
} // class end

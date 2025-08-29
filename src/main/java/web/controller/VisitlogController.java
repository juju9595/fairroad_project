package web.controller;


import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;
import web.service.VisitlogService;

@RestController
@RequiredArgsConstructor
public class VisitlogController { // class start

    private final VisitlogService visitlogService;
} // class end

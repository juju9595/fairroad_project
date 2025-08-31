package web.controller;


import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import web.model.dto.VisitlogDto;
import web.service.VisitlogService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/visitlog")
public class VisitlogController { // class start

    private final VisitlogService visitlogService;

    // 방문 로그 저장
    @PostMapping("")
    public boolean insertVisitLog(@RequestBody VisitlogDto visitlogDto){
        boolean result = visitlogService.insertVisitLog(visitlogDto);
        return result;
    } // func e

} // class end

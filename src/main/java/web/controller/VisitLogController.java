package web.controller;


import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import web.model.dto.VisitLogDto;
import web.service.VisitLogService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/visitlog")
public class VisitLogController { // class start

    @Autowired
    private VisitLogService visitlogService;

    // 방문 로그 저장
    @PostMapping("")
    public boolean insertVisitLog(@RequestBody VisitLogDto visitlogDto){
        boolean result = visitlogService.insertVisitLog(visitlogDto);
        return result;
    } // func e

} // class end

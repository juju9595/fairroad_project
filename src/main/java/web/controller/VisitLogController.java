package web.controller;


import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import web.model.dto.LastVisitDto;
import web.model.dto.VisitLogDto;
import web.service.VisitLogService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

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

    // 최근 본 박람회 조회
    @GetMapping("/last")
    public Map<String , Object> lastVisitList(@RequestParam int mno){
        List<LastVisitDto> lastList = visitlogService.lastVisitList(mno);

        // 회원별 최근 본 박람회 리스트 출력해야 해서 Map 포장
        Map<String , Object> result = new HashMap<>();
        result.put("mno" , mno);
        result.put("lastvisitfair" , lastList);

        return result;
    } // func e

} // class end

package web.controller;


import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import web.model.dto.LastVisitDto;
import web.model.dto.VisitLogDto;
import web.service.VisitLogService;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@RequestMapping("/visitlog")
public class VisitLogController { // class start

    @Autowired
    private VisitLogService visitlogService;

    /* 방문 기록 DB 처리 시 썼던 코드
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
    */

    // --------------- CSV 파일 처리 할때 쓰는 코드 ---------------- //
    // 방문 로그 저장
    @PostMapping("/add")
    public String insertVisitLog(@RequestParam(required = false) String mno ,
                                 @RequestParam int fno ){
        Integer memberNo = (mno == null || mno.equals("null")) ? null : Integer.valueOf(mno);
        VisitLogDto log = new VisitLogDto();
        log.setMno(memberNo);   // 비회원이면 null 가능
        log.setFno(fno);
        log.setVdate(LocalDateTime.now());

        visitlogService.addVisitLogAsync(log);
        return "방문로그 등록 완료(비동기)";
    } // func e

    // 회원별 방문로그 조회
    @GetMapping("/member")
    public List<VisitLogDto> getLogsByMember(@RequestParam int mno){
        return visitlogService.getLogsByMember(mno);
    } // func e

    // 회원별 최근 방문 10개
    @GetMapping("/recent")
    public List<VisitLogDto> getRecentLogs(HttpSession session){
        Integer loginMno = (Integer) session.getAttribute("loginMno");
        if(loginMno == null) loginMno = 101;// return Collections.emptyList();

        return visitlogService.getLogsByMember(loginMno).stream()
                .limit(10)
                .collect(Collectors.toList());
    } // func e

} // class end

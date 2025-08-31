package web.controller;


import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import web.model.dto.FairDto;
import web.model.dto.PageDto;
import web.service.FairService;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/fair")
public class FairController { // class start
    private final FairService fairService;

    //박람회 등록
    @PostMapping("/write")
    public int fairWrite(@RequestBody FairDto fairDto){
        System.out.println("FairController.fairWrite");
        System.out.println("fairDto = " + fairDto);
        int result = fairService.fairWrite(fairDto);
        return result;
    }//func end

    //박람회 전체
    @GetMapping("/print")
    public PageDto fairPrint(@RequestParam int cno,
                             @RequestParam int page,
                             @RequestParam int count,
                             @RequestParam (required = false) String key,
                             @RequestParam (required = false) String keyword){
        PageDto result= fairService.fairPrint(cno,page,count,key,keyword);
        return result;
    }//func end


} // class end

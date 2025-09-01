package web.controller;


import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import web.model.dto.FairDto;
import web.model.dto.PageDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import web.model.dto.FairCountDto;
import web.model.dto.FairRegionDto;
import web.service.FairService;

import java.util.List;

import java.util.List;
import java.util.Map;

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



    @Autowired
    //private FairService fairService;

    // 조회수별 박람회 조회
    @GetMapping("/visitlog/fcount")
    public List<FairCountDto> fcountList(){
        List<FairCountDto> result = fairService.fcountList();
        return result;
    } // func e

    // 지역별 박람회 조회
    @GetMapping("/visitlog/fregion")
    public Map<String , List<FairRegionDto>> fregionList(){
        Map<String , List<FairRegionDto>> result = fairService.fregionList();
        return result;
    } // func e

} // class end

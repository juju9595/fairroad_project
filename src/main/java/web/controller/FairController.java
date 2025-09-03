package web.controller;


import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import web.model.dto.FairDto;
import web.model.dto.PageDto;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import web.model.dto.FairCountDto;
import web.model.dto.FairRegionDto;
import web.service.FairService;
import web.service.FileService;

import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/fair")
public class FairController { // class start
    private final FairService fairService;
    private final FileService fileService; //업로드 Service

    //박람회 등록
    @PostMapping("/write")
    public int fairWrite(@ModelAttribute FairDto fairDto){
        System.out.println("FairController.fairWrite");
        System.out.println("fairDto = " + fairDto);
        int result = fairService.fairWrite(fairDto);
        //제품 등록했으면서 첨부파일이 비어있지 않고 첨부파일 목록에 첫번째 첨부파일이 비어 있지 않으면
        if( result > 0 && !fairDto.getUploads().isEmpty() && !fairDto.getUploads().get(0).isEmpty() ){
            //파일업로드,LIST타입을 반복문 이용하여 여러번 업로드
            for(MultipartFile multipartFile : fairDto.getUploads()){
                String fimg=fileService.fileUpload(multipartFile);
                if(fimg==null)break;
                boolean result2 = fairService.fairImg(fimg,result);
                if(result2==false)return 0;
            }//for end
        }//if end
        return result;
    }//func end

    //박람회 카테고리별 전체 조회(카테고리/페이징/검색)
    @GetMapping("/allPostCategory")
    public PageDto fairPrint(@RequestParam (defaultValue = "1")int cno,
                             @RequestParam (defaultValue = "1")int page,
                             @RequestParam (defaultValue = "5")int count,
                             @RequestParam (required = false) String key,
                             @RequestParam (required = false) String keyword){
        PageDto result= fairService.fairPrint(cno,page,count,key,keyword);
        return result;
    }//func end

    //박람회 메인 전체 조회(페이징/검색)
    @GetMapping("/allPostMain")
    public PageDto fairPrintMain(@RequestParam (defaultValue = "1")int page,
                                 @RequestParam (defaultValue = "5")int count,
                                 @RequestParam (required = false) String key,
                                 @RequestParam (required = false) String keyword){
        PageDto result = fairService.fairMainPrint(page,count,key,keyword);
        return result;
    }

    // 박람회 상세 조회
    @GetMapping("/getpost")
    public FairDto fairInfo(@RequestParam int fno){
        FairDto result = fairService.fairInfo(fno);
        return result;
    }//func end

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

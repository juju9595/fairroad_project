package web.controller;


import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
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
import web.service.RecommendService;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/fair")
public class FairController { // class start
    private final FairService fairService;
    private final FileService fileService; //업로드 Service

    @Autowired
    private RecommendService recommendService;

    //박람회 등록
    @PostMapping("/write")
    public int fairWrite(@ModelAttribute FairDto fairDto, HttpSession session){
        // 세션에서  관리자 정보 가져오기
//        Object loginAdmin = session.getAttribute(("loginAdmin")); // 관리자 로그인 세션
//
//        //관리자 아니면 수정 불가 박람회
//        if(loginAdmin==null||!(boolean)loginAdmin){
//            return 0;
//        }//if end

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
        }//for end
        return result;
    }//func end

    //박람회 수정
    @PutMapping("/update")
    public int fairUpdate(@ModelAttribute FairDto fairDto,HttpSession session){
        // 세션에서  관리자 정보 가져오기
        Object loginAdmin = session.getAttribute(("loginAdmin")); // 관리자 로그인 세션

        //관리자 아니면 수정 불가 박람회
        if(loginAdmin==null||!(boolean)loginAdmin){
            return 0;
        }//if end
        int result = fairService.fairUpdate(fairDto);
        if (result > 0 && !fairDto.getUploads().isEmpty() && !fairDto.getUploads().get(0).isEmpty()) {
            for (MultipartFile multipartFile : fairDto.getUploads()) {
                String fimg = fileService.fileUpload(multipartFile);
                if (fimg == null) break;
                boolean result2 = fairService.fairImg(fimg, result);
                if (!result2) return 0;
            }//for end
        }//if end
        return result;
    }//func end

    //박람회 카테고리별 전체 조회(카테고리/페이징/검색)
    @GetMapping("/allPostCategory")
    public PageDto fairPrint(@RequestParam (defaultValue = "1")int cno,
                             @RequestParam (defaultValue = "1")int page,
                             @RequestParam (defaultValue = "10")int count,
                             @RequestParam (required = false) String key,
                             @RequestParam (required = false) String keyword){
        PageDto result= fairService.fairPrint(cno,page,count,key,keyword);
        return result;
    }//func end

    //박람회 메인 전체 조회(페이징/검색)
    @GetMapping("/allPostMain")
    public PageDto fairPrintMain(@RequestParam (defaultValue = "1")int page,
                                 @RequestParam (defaultValue = "10")int count,
                                 @RequestParam (required = false) String key,
                                 @RequestParam (required = false) String keyword,
                                 @RequestParam (required = false) List<Integer> showFno ,
                                 HttpSession session){
        // 세션에서 회원번호(mno) 확인
        Integer mno = (Integer) session.getAttribute("loginMno");
        boolean isMember = (mno != null);
        // 회원이면 알고리즘 , 비회원이면 전체 조회
        if(mno != null ){
            // 회원이면
            return recommendService.getRecommendationsPaged(mno , page , count , key ,keyword , showFno);
        }else {
            // 회원 여부를 서비스로 전달
            return fairService.fairMainPrint(page, count, key, keyword, showFno, isMember);
        } // if e

    } // func e

    // 박람회 상세 조회
    @GetMapping("/getPost")
    public FairDto fairInfo(@RequestParam int fno,HttpSession session){
        Object object = session.getAttribute("viewHistory");
        Map<Integer,String> viewHistory;
        if(object==null){
            viewHistory = new HashMap<>();
        }else{
            viewHistory = (Map<Integer, String>)object;
        }//if end
        String today = LocalDate.now().toString();
        String check = viewHistory.get(fno);
        if(check==null||!check.equals(today)){
            fairService.increment(fno);
            viewHistory.put(fno,today);
            session.setAttribute("viewHistory",viewHistory);
        }//if end
        FairDto fairDto = fairService.fairInfo(fno);
        return fairDto;
    }//func end

    // 조회수별 박람회 조회
    @GetMapping("/visitlog/fcount")
    @ResponseBody
    public Map<String, Object> fcountList(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "6") int count) {

        List<FairCountDto> result = fairService.fcountList(page, count);
        int totalCount = fairService.getTotalFcount();
        int totalPage = (int) Math.ceil(totalCount / (double) count);

        int startBtn = Math.max(1, page - 2);
        int endBtn = Math.min(totalPage, page + 2);

        Map<String, Object> map = new HashMap<>();
        map.put("data", result);
        map.put("currentPage", page);
        map.put("totalPage", totalPage);
        map.put("startBtn", startBtn);
        map.put("endBtn", endBtn);

        return map;
    } // func e

    // 지역별 박람회 조회
    @GetMapping("/visitlog/fregion")
    public Map<String , List<FairRegionDto>> fregionList(){
        Map<String , List<FairRegionDto>> result = fairService.fregionList();
        return result;
    } // func e

} // class end

package web.controller;


import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import web.model.dto.FairCountDto;
import web.service.FairService;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/visitlog")
public class FairController { // class start

    @Autowired
    private FairService fairService;

    // 조회수별 박람회 조회
    @GetMapping("/fcount")
    public List<FairCountDto> fcountList(){
        List<FairCountDto> result = fairService.fcountList();
        return result;
    } // func e

} // class end

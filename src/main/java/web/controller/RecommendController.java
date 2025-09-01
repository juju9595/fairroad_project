package web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import web.model.dto.FairDto;
import web.service.RecommendService;

import java.util.List;

@RestController
@RequestMapping("/visitlog")
public class RecommendController {
    @Autowired
    RecommendService recommendService;

    // 추천 알고리즘
    @GetMapping("/recommend")
    public List<FairDto> recommendList(@RequestParam int mno){
        List<FairDto> result = recommendService.getRecommendations(mno);
        return result;
    } // func e

} // class e

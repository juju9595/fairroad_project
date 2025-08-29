package web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import web.service.FairService;

@RestController
@RequestMapping("/fair")
public class FairController {
    @Autowired FairService fairService;

}//class end

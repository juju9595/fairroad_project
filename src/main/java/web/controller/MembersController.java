package web.controller;


import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;
import web.service.MembersService;

@RestController
@RequiredArgsConstructor
public class MembersController { // class start

    @Autowired
    private MembersService membersService;
} // class end

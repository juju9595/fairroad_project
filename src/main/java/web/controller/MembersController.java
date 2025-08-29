package web.controller;


import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;
import web.service.MembersService;

@RestController
@RequiredArgsConstructor
public class MembersController { // class start

    private final MembersService membersService;
} // class end

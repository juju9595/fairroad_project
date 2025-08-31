package web.controller;


import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;
import web.service.WishListService;

@RestController
@RequiredArgsConstructor
public class WishController { // class start
    @Autowired
    private WishListService wishlistService;
} // class end

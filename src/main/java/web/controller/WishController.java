package web.controller;


import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;
import web.service.WishListService;

@RestController
@RequiredArgsConstructor
public class WishController { // class start

    private final WishListService wishlistService;
} // class end

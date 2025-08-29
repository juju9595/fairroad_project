package web.controller;


import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;
import web.service.WishlistService;

@RestController
@RequiredArgsConstructor
public class WishListController { // class start

    private final WishlistService wishlistService;
} // class end

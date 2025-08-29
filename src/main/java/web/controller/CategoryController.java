package web.controller;


import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;
import web.service.CategoryService;

@RestController
@RequiredArgsConstructor
public class CategoryController { // class start

    private final CategoryService categoryService;

} // class end

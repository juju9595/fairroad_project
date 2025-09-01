package web.controller;


import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;
import web.service.CategoryService;

@RestController
@RequiredArgsConstructor
public class CategoryController { // class start

    @Autowired
    private CategoryService categoryService;

} // class end

package web.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import web.model.dao.CategoryDao;

@Service
public class CategoryService {
    @Autowired CategoryDao categoryDao;
}

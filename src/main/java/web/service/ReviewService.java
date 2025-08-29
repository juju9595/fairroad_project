package web.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import web.model.dao.ReviewDao;

@Service
public class ReviewService {
    @Autowired
    ReviewDao reviewDao;
}

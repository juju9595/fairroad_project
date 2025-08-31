package web.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import web.model.dao.WishListDao;

@Service
public class WishListService {
    @Autowired
    WishListDao wishlistDao;
}

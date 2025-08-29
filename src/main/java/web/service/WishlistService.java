package web.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import web.model.dao.WishlistDao;

@Service
public class WishlistService {
    @Autowired
    WishlistDao wishlistDao;
}

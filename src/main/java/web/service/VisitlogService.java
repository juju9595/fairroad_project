package web.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import web.model.dao.VisitlogDao;

@Service
public class VisitlogService {
    @Autowired
    VisitlogDao visitlogDao;
}

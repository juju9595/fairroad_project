package web.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import web.model.dao.VisitLogDao;

@Service
public class VisitlogService {
    @Autowired
    VisitLogDao visitlogDao;
}

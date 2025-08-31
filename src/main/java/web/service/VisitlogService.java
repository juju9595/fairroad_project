package web.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import web.model.dao.VisitlogDao;
import web.model.dto.VisitlogDto;

@Service
public class VisitlogService {
    @Autowired
    VisitlogDao visitlogDao;

    // 방문 로그 저장
    public boolean insertVisitLog(VisitlogDto visitlogDto ){
        boolean result = visitlogDao.insertVisitLog(visitlogDto);
        return result;
    } // func e

}

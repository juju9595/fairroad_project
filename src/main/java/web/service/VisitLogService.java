package web.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import web.model.dao.VisitLogDao;
import web.model.dto.VisitLogDto;

@Service
public class VisitLogService {
    @Autowired
    VisitLogDao visitlogDao;

    // 방문 로그 저장
    public boolean insertVisitLog(VisitLogDto visitlogDto ){
        boolean result = visitlogDao.insertVisitLog(visitlogDto);
        return result;
    } // func e

}

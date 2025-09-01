package web.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import web.model.dao.VisitLogDao;
import web.model.dto.LastVisitDto;
import web.model.dto.VisitLogDto;

import java.util.List;

@Service
public class VisitLogService {
    @Autowired
    VisitLogDao visitlogDao;

    // 방문 로그 저장
    public boolean insertVisitLog(VisitLogDto visitlogDto ){
        boolean result = visitlogDao.insertVisitLog(visitlogDto);
        return result;
    } // func e

    // 최근 본 박람회 조회
    public List<LastVisitDto> lastVisitList(int mno){
        List<LastVisitDto> result = visitlogDao.lastVisitList(mno);
        return result;
    } // func e


} // class e

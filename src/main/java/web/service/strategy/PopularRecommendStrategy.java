package web.service.strategy;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import web.model.dao.FairDao;
import web.model.dao.VisitLogDao;
import web.model.dao.VisitLogDaoImpl;
import web.model.dto.FairDto;

import java.util.ArrayList;
import java.util.List;

@Service
public class PopularRecommendStrategy implements RecommendStrategy{
    @Autowired
    VisitLogDao visitLogDao;
    @Autowired
    FairDao fairDao;

    private static final int TOP_COUNT = 10;


    @Override
    public List<FairDto> recommend(int mno){
        return visitLogDao.getTopVisitedFairs(TOP_COUNT);
    } // func e

} // class e

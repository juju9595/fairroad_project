package web.service.strategy;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import web.model.dao.FairDao;
import web.model.dao.VisitLogDao;
import web.model.dto.FairDto;

import java.util.ArrayList;
import java.util.List;

@Service
public class PopularRecommendStrategy implements RecommendStrategy{
    @Autowired
    VisitLogDao visitLogDao;
    @Autowired
    FairDao fairDao;

    @Override
    public List<FairDto> recommend(int mno){
        // 방문수 기준 상위 fno 가져오기
        List<Integer> topFnoList = visitLogDao.getTopVisitedFairs(5);

        // fno로 박람회 상세 정보 가져오기
        List<FairDto> list = new ArrayList<>();
        for (int fno : topFnoList){
            FairDto fair = fairDao.getFairbyFno(fno);
            if(fair != null ) list.add(fair);
        }
        return list;
    } // func e

} // class e

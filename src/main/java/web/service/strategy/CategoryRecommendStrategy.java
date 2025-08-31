package web.service.strategy;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import web.model.dao.FairDao;
import web.model.dao.VisitLogDao;
import web.model.dto.FairDto;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class CategoryRecommendStrategy implements RecommendStrategy {
    @Autowired
    VisitLogDao visitLogDao;
    @Autowired
    FairDao fairDao;

    @Override
    public List<FairDto> recommend(int mno){

        // 회원 방문 fno 목록
        List<Integer> visitedFno = visitLogDao.getVisitFnoByMember(mno);

        // fno -> FairDto 가져오기
        Map<Integer , Integer> categoryCount = new HashMap<>();
        for (int fno : visitedFno){
            FairDto fair = fairDao.getFairbyFno(fno);
            if(fair != null){
                categoryCount.put(fair.getCno() , categoryCount.put(fair.getCno() , 0 ) + 1);
            }
        } // for e

        // 방문 빈도 가장 높은 카테고리 선택
        int topCategory = categoryCount.entrySet().stream()
                .max(Map.Entry.comparingByValue())
                .map(Map.Entry::getKey)
                .orElse(0);

        // 해당 카테고리 박람회 조회
        return fairDao.getFairsByCategory(topCategory);
    } // func e

} // class e

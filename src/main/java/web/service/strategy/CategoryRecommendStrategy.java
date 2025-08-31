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

    public CategoryRecommendStrategy(VisitLogDao visitLogDao, FairDao fairDao) {
    }

    @Override
    public List<FairDto> recommend(int mno){

        // 회원 방문 fno 목록
        List<Integer> visitedFno = visitLogDao.getVisitFnoByMember(mno);

        // fno -> FairDto 가져오기
        Map<Integer , Integer> categoryCount = new HashMap<>(); // key = 카테고리번호(cno) , value = 방문횟수
        for (int fno : visitedFno){                             // 회원이 방문한 fno 목록 순회 하면서 각 fno의 카테고리 가져와서 카운트 저장
            FairDto fair = fairDao.getFairbyFno(fno);
            if(fair != null){
                categoryCount.put(fair.getCno() , categoryCount.put(fair.getCno() , 0 ) + 1);   // 해당 카테고리 값 없으면 0반환
            }
        } // for e

        // 방문 빈도 가장 높은 카테고리 선택
        int topCategory = categoryCount.entrySet().stream()
                // categoryCount.entrySet() : Map의 모든 쌍을 Set로 반환 = 배열화
                // .stream() : entrySet을 stream으로 변환 함수형 처리 가능
                .max(Map.Entry.comparingByValue())      // value(방문 횟수) 가장 큰 요소 선택
                .map(Map.Entry::getKey)                 // 선택된 Entry에서 key만 꺼냄!! -> 카테고리 번호
                .orElse(0);                     // Map 비어있고 max 없으면 기본값 0 반환

        // 해당 카테고리 박람회 조회
        return fairDao.getFairsByCategory(topCategory);
    } // func e

} // class e

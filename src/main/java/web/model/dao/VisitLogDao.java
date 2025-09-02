package web.model.dao;

import org.springframework.stereotype.Repository;
import web.model.dto.FairDto;

import java.util.List;

@Repository
public interface VisitLogDao {
    List<Integer> getVisitFnoByMember(int mno);  // 회원별 방문 fno
    List<FairDto> getTopVisitedFairs(int n);     // 전체 인기 박람회 fno 상위 n개
}

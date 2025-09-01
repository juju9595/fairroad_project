package web.service.strategy;

import web.model.dto.FairDto;

import java.util.List;

public interface RecommendStrategy { //  추천 알고리즘 위한 인터페이스
    List<FairDto> recommend(int mno);

}

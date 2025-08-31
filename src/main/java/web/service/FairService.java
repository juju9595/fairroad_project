package web.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import web.model.dao.FairDao;
import web.model.dto.FairCountDto;
import web.model.dto.FairDto;
import web.model.dto.FairRegionDto;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class FairService {
    @Autowired FairDao fairDao;

    // 조회수별 박람회 조회
    public List<FairCountDto> fcountList(){
        List<FairCountDto> result = fairDao.fcountList();
        return result;
    } // func e

    // 지역별 그룹핑 박람회 조회
    // 전체 조회 하는 이유
    // : DB 에서 바로 Map 형태로 가져오기 불가능함. 전체 조회 후 지역별 그룹핑 과정 필요
    public Map<String , List<FairRegionDto>> fregionList(){
        // key -> 지역명("서울" , "인천" 등등) String
        // value -> 해당 지역의 박람회 리스트 List<FairRegionDto>
        List<FairDto> fairList = fairDao.selectAllFairs();

        // 지역별 박람회 묶기 위한 Map
        Map<String , List<FairRegionDto>> regionMap = new HashMap<>();

        // 전체 박람회 반복 조회
        for( FairDto f : fairList ){
            String region = extractRegion(f.getFplace());
            // 박람회 장소 "서울 코엑스" -> "서울"

            regionMap.computeIfAbsent(region , k -> new ArrayList<>())
                    .add(new FairRegionDto(f.getFno() , f.getFname()));
            // computeIfAbsent : regionMap 에 region 이미 존재하는 지 확인
            // 존재하면 기존 리스트 그대로 사용
            // 존재하지 않으면 새 ArrayList 생성 후 Map에 추가
            // 이후에 현재 박람회를 리스트에 넣기
        }
        return regionMap;

    } // func e

    // 박람회 장소 문자열 반환 메소드 , 없으면 기타로 반환
    private String extractRegion(String fplace){
        if(fplace == null || fplace.isEmpty()) return "기타";
        return fplace.split(" ")[0]; // " " 공백 넣어야함 , 안하면
    } // func e

}//class end

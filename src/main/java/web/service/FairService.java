package web.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import web.model.dao.FairDao;
import web.model.dto.FairDto;
import web.model.dto.PageDto;

import java.util.List;
import web.model.dto.FairCountDto;
import web.model.dto.FairDto;
import web.model.dto.FairRegionDto;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class FairService {
    private final FairDao fairDao;

    //박람회 등록
    public int fairWrite(FairDto fairDto){
        int result = fairDao.fairWrite(fairDto);
        return result;
    }//func end

    //박람회 대표 이미지 등록
    public boolean fairImg(String fimg,int fno){
        boolean result = fairDao.fairImg(fimg,fno);
        return result;
    }//func end

    //박람회 메인 전체 조회
    public PageDto fairMainPrint(int page,int count,String key, String keyword){
        int startRow=(page-1)*count;

        int totalCount;

        //자료구하기
        List<FairDto>fairList;
        if(key!=null&&!key.isEmpty()&&keyword!=null&&!keyword.isEmpty()){
            //검색일때
            totalCount=fairDao.getMainTotalCountSearch(key,keyword);
            fairList=fairDao.fairPrintMainSearch(startRow,count,key,keyword);
        }else{
            //검색 아닐때
            totalCount = fairDao.getMainTotalCount();
            fairList = fairDao.fairPrintMain(startRow,count);
        }//if end
        //메인 전체 페이지수
        int totalPage = totalCount % count == 0? totalCount/count : totalCount/count+1; //나머지 1

        //최대 버튼수
        int btnCount=5;

        //시작버튼
        int startBtn = ((page-1)/btnCount)*btnCount+1;
        //int startBtn = ((page-1)/btnCount)*btnCount+1;
        // 끝버튼
        int endBtn = startBtn + btnCount -1;
        //총 페이지 수 끝번호
        if(endBtn > totalPage) endBtn = totalPage;

        //pageDto 구성하기
        PageDto pageDto = new PageDto();
        pageDto.setCurrentPage(page);
        pageDto.setTotalPage(totalPage);
        pageDto.setPerCount(count);
        pageDto.setTotalCount(totalCount);
        pageDto.setStartBtn(startBtn);
        pageDto.setEndBtn(endBtn);
        pageDto.setData(fairList);
        return pageDto;

    }//func end

    //박람회 카테고리별 전체 조회
    public PageDto fairPrint(int cno,int page,int count,String key, String keyword ){

        // 페이지 계산
        int startRow=(page-1)*count;

        int totalCount;

        //자료구하기
        List<FairDto>fairList;
        if(key!=null&&!key.isEmpty()&&keyword!=null&&!keyword.isEmpty()){
            //검색일때
            totalCount=fairDao.getTotalCountSearch(cno,key,keyword);
            fairList=fairDao.fairPrintSearch(cno,startRow,count,key,keyword);
        }else{
            //검색 아닐때
            totalCount=fairDao.getTotalCount(cno);
            fairList=fairDao.fairPrint(cno,startRow,count);
        }//if end

        //전체 페이지수
        int totalPage = totalCount % count == 0? totalCount/count : totalCount/count+1; //나머지가 존재하면 +1

        // 최대 버튼수
        int btnCount=5;

        //시작 버튼
        int startBtn = ((page-1)/btnCount)*btnCount+1;
        //끝 버튼
        int endBtn = startBtn + btnCount -1;
        //총 페이지수 끝번호
        if(endBtn > totalPage) endBtn = totalPage;

        //pageDto 구성하기
        PageDto pageDto = new PageDto();
        pageDto.setCurrentPage(page);
        pageDto.setTotalPage(totalPage);
        pageDto.setPerCount(count);
        pageDto.setTotalCount(totalCount);
        pageDto.setStartBtn(startBtn);
        pageDto.setEndBtn(endBtn);
        pageDto.setData(fairList);
        return pageDto;
    }//func end

    // 박람회 상세 조회
    public FairDto fairInfo(int fno){
        FairDto result = fairDao.fairInfo(fno);
        return result;
    }//func end

    // 박람회 조회수 증가
    public void increment(int fno){
        fairDao.incrementCount(fno);
    }//func end


    // 페이징 적용된 조회수별 박람회 리스트
    public List<FairCountDto> fcountList(int page, int count){
        return fairDao.fcountList(page, count);
    }

    // 전체 조회수 기준 박람회 개수
    public int getTotalFcount(){
        return fairDao.getTotalFcount();
    }

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
                    .add(new FairRegionDto(f.getFno() , f.getFname(), f.getFplace(), f.getFprice()));
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
        return fplace.split(" ")[0]; // " " 공백 넣어야함 , 안하면 "서울 엑스포" -> "ㅅ" 나옴
    } // func e

}//class end

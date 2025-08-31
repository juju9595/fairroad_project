package web.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import web.model.dao.FairDao;
import web.model.dto.FairDto;
import web.model.dto.PageDto;

import java.util.List;

@Service
public class FairService {
    @Autowired FairDao fairDao;

    //박람회 등록
    public int fairWrite(FairDto fairDto){
        int result = fairDao.fairWrite(fairDto);
        return result;
    }//func end

    //박람회 전체 조회
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
        int btbCount=5;

        //시작 버튼
        int startBtn = ((page-1)/btbCount)*btbCount+1;
        //끝 버튼
        int endBtn = startBtn + btbCount -1;
        //총 페이지수 끝번호
        if(endBtn > totalPage) endBtn = totalPage;

        //pageDto 구성하기
        PageDto pageDto = new PageDto();
        pageDto.setCurrentPage(page);
        pageDto.setTotalCount(totalPage);
        pageDto.setPerCount(count);
        pageDto.setTotalCount(totalCount);
        pageDto.setStartBtn(startBtn);
        pageDto.setEndBtn(endBtn);
        pageDto.setData(fairList);
        return pageDto;
    }//func end



}//class end

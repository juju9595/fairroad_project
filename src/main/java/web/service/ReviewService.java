package web.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import web.model.dao.ReviewDao;
import web.model.dto.PageDto;
import web.model.dto.ReviewDto;

import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class ReviewService {

    private final ReviewDao reviewDao;

    // [1] 방문 리뷰 등록
    // [1] 방문 리뷰 등록
    public int reviewWrite( ReviewDto reviewDto ){
        return reviewDao.reviewWrite( reviewDto );
    }


    // [2] 방문 리뷰 박람회별 조회
    public List< ReviewDto > reviewPrint( int fno ){
        return reviewDao.reviewPrint( fno );
    }

//    public PageDto reviewPrint(int fno, int page, int count) {
//        int startRow = (page - 1) * count;
//
//        int totalCount = reviewDao.getTotalCount(fno);
//        List<ReviewDto> postList = reviewDao.reviewPrint(fno, startRow, count);
//
//        int totalPage = (totalCount % count == 0) ? (totalCount / count) : (totalCount / count + 1);
//
//        int btnCount = 5;
//        int startBtn = ((page - 1) / btnCount) * btnCount + 1;
//        int endBtn = startBtn + btnCount - 1;
//        if (endBtn > totalPage) endBtn = totalPage;
//
//        PageDto pageDto = new PageDto();
//        pageDto.setCurrentPage(page);
//        pageDto.setTotalPage(totalPage);
//        pageDto.setPerCount(count);
//        pageDto.setTotalCount(totalCount);
//        pageDto.setStartBtn(startBtn);
//        pageDto.setEndBtn(endBtn);
//        pageDto.setData(postList);
//
//        return pageDto;
//    }


    // [3] 방문 리뷰 개별 조회
    public ReviewDto reviewPrint2( int rno ){
        return reviewDao.reviewPrint2( rno );
    }


    // [4] 방문 리뷰 수정
    public int reviewUpdate( int rno , int mno , String rtitle , String rcontent ){
        return reviewDao.reviewUpdate( rno , mno , rtitle , rcontent );
    }


    // [5] 방문 리뷰 삭제
    public int reviewDelete(int rno, int mno) {
        return reviewDao.reviewDelete(rno, mno);
    }






    //-------------------------------------------------------------//


}

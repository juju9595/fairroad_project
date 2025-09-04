package web.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import web.model.dao.ReviewDao;
import web.model.dto.ReviewDto;

import java.util.List;

@Service
public class ReviewService {
    @Autowired
    ReviewDao reviewDao;

    // [1] 방문 리뷰 등록
    public int reviewWrite( ReviewDto reviewDto ){
        return reviewDao.reviewWrite( reviewDto );
    }

    // [2] 방문 리뷰 전체 조회
    public List< ReviewDto > reviewPrint( int fno ){
        return reviewDao.reviewPrint( fno );
    }

    // [3] 방문 리뷰 개별 조회
    public ReviewDto reviewPrint2( int rno ){
        return reviewDao.reviewPrint2( rno );
    }


    // [4] 방문 리뷰 수정
    public int reviewUpdate( int rno , String rtitle , String rcontent ){
        return reviewDao.reviewUpdate( rno , rtitle , rcontent );
    }


    // [5] 방문 리뷰 삭제
    public boolean reviewDelete( int rno ){
        return reviewDao.reviewDelete( rno );
    }




    //-------------------------------------------------------------//


}

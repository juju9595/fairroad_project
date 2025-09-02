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
    public boolean reviewWrite( ReviewDto reviewDto ){
        return reviewDao.reviewWrite( reviewDto );
    }

    // [2] 방문 리뷰 전체 조회
    public List< ReviewDto > reviewPrint(){
        return reviewDao.reviewPrint();
    }

    // [3] 방문 리뷰 개별 조회
    public ReviewDto reviewPrint2( int rno ){
        return reviewDao.reviewPrint2( rno );
    }


    // [4] 방문 리뷰 수정
    public boolean reviewUpdate( int rno , String rcontent ){
        return reviewDao.reviewUpdate( rno , rcontent );
    }


    // [5] 방문 리뷰 삭제
    public boolean revieweDelete( int rno ){
        return reviewDao.reviewDelete( rno );
    }
}

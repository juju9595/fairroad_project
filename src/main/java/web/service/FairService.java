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
    public List<FairDto> fairPrint(){
        return fairDao.fairPrint();
    }//func end



}//class end

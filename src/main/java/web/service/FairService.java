package web.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import web.model.dao.FairDao;
import web.model.dto.FairCountDto;

import java.util.List;

@Service
public class FairService {
    @Autowired FairDao fairDao;

    // 조회수별 박람회 조회
    public List<FairCountDto> fcountList(){
        List<FairCountDto> result = fairDao.fcountList();
        return result;
    } // func e

}//class end

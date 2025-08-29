package web.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import web.model.dao.FairDao;

@Service
public class FairService {
    @Autowired FairDao fairDao;

}//class end

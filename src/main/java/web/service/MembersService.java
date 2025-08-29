package web.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import web.model.dao.MemberDao;

@Service
public class MembersService {
    @Autowired
    MemberDao memberDao;
}

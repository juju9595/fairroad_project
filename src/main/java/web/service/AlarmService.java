package web.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import web.model.dao.AlarmDao;
import web.model.dto.AlarmDto;

import java.util.List;


@Service
public class AlarmService { // class start

    @Autowired
    private AlarmDao alarmDao;

    // 알림 생성
    public boolean createAlarm(int mno, int fno, String message) {
        AlarmDto dto = new AlarmDto();
        dto.setMno(mno);
        dto.setFno(fno);
        dto.setMessage(message);

        return alarmDao.insert(dto); // ✅ Dao의 insert 호출
    }

    // 회원별 알림 조회
    public List<AlarmDto> findByMember(int mno) {
        return alarmDao.findByMember(mno);
    }
}

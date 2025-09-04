package web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import web.model.dao.VisitLogDao;
import web.model.dto.VisitLogDto;

@Component
public class VisitLogMultiThreadTest implements CommandLineRunner {

    @Autowired
    private VisitLogDao visitLogDao;

    @Override
    public void run(String... args) throws Exception {

//        Runnable task = () -> {
//            VisitLogDto log = new VisitLogDto();
//            log.setMno(1);   // 회원번호
//            log.setFno(101); // 박람회번호
//
//            if (visitLogDao instanceof web.model.dao.VisitLogDaoImpl impl) {
//                impl.saveVisitLog(log);
//            }
//        };
//
//        // 스레드 동시에 실행
//        for (int i = 0; i < 1; i++) {
//            new Thread(task).start();
//        }
    }
}
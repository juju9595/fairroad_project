package web;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;

import java.io.File;

@EnableScheduling // 스케줄링 써야해서
@SpringBootApplication
@EnableAsync // 비동기 처리 활성화
public class AppStart { // class start
    public static void main(String[] args) { // main start
//1234910
        SpringApplication.run( AppStart.class );

        // baseDir 경로 설정
        String baseDir = System.getProperty("user.dir") + File.separator + "logs";

        // 절대 경로 출력
        System.out.println("logs 폴더 절대 경로: " + new File(baseDir).getAbsolutePath());

        // 폴더 존재 여부 확인
        File dir = new File(baseDir);
        if (!dir.exists()) {
            boolean created = dir.mkdirs();
            System.out.println("logs 폴더 생성 시도: " + created);
        } else {
            System.out.println("logs 폴더 이미 존재");
        }


        System.out.println("notifyUpcomingFairs 실행됨");



    } // main end
} // class end 1234567678912543234123534

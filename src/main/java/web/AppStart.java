package web;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableAsync // 비동기 처리 활성화
public class AppStart { // class start
    public static void main(String[] args) { // main start
//12349
        SpringApplication.run( AppStart.class );

    } // main end
} // class end 1234567678912

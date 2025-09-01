package web.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class VisitLogDto {
    private int vno;              // 방문 로그 고유번호 (PK)
    private Integer mno;          // 회원번호 (비회원은 null 허용해야해서 Integer)
    private int fno;              // 박람회 번호 (무조건 있어야해서 int)
    private LocalDateTime vdate;  // 방문 일자 (DB 자동 생성)

    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    // 객체 -> CSV 문자열 변환
    public String toCsv(){
        return vno + "," + (mno != null ? mno : "") + "," + fno + "," + vdate.format(formatter);
    }

    // CSV 문자열 -> 객체 변환
    public static VisitLogDto fromCsv(String line){
        String[] parts = line.split(",");
        int vno = Integer.parseInt(parts[0]);
        Integer mno = parts[1].isEmpty() ? null : Integer.parseInt(parts[1]);
        int fno = Integer.parseInt(parts[2]);
        LocalDateTime vdate = LocalDateTime.parse(parts[3] , formatter);
        return new VisitLogDto(vno , mno , fno , vdate);
    }
}//class end

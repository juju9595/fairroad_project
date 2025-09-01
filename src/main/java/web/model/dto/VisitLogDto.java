package web.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class VisitLogDto {
    private int vno;              // 방문 로그 고유번호 (PK)
    private Integer mno;          // 회원번호 (비회원은 null 허용해야해서 Integer)
    private int fno;              // 박람회 번호 (무조건 있어야해서 int)
    private LocalDateTime vdate;  // 방문 일자 (DB 자동 생성)
}//class end

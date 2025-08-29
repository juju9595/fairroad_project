package web.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class VisitLogDto {
    private int vno;        // 방문로그 번호
    private int mno;        // 회원번호(FK)
    private int fno;        // 박람회번호(FK)
    private String vdate;   // 사이트 방문일자
}//class end

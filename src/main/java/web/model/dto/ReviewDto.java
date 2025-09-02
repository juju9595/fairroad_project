package web.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Data

public class ReviewDto {

    private int rno;            // 리뷰 번호
    private int mno;            // 회원 번호(FK)
    private int fno;            // 박람회 번호 (FK)
    private String rcontent;    // 리뷰 내용
    private String rdate;       // 리뷰 작성일

    public ReviewDto(int rno, String rcontent, LocalDate rdate) {
    }
}//class end

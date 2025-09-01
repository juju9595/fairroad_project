package web.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class FairCountDto {

    private int fno;
    private String fname;
    private int fcount; // 조회수
}

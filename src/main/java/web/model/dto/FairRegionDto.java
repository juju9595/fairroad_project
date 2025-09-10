package web.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class FairRegionDto { // 지역별로 리스트 묶어 반환하기 위한 Dto
    private int fno;
    private String fname;
    private String fplace;  // 추가
    private int fprice;     // 추가
    private String fimg;
}

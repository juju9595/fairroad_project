package web.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class RecommendDto {
    private int fno;
    private String fname;
    private String fplace;
    private String startDate;
    private String endDate;

}

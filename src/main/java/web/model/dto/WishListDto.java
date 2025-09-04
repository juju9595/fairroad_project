package web.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class WishListDto { // 즐겨찾기 된 박람회 리스트 Dto

    private int fno;       // 박람회 번호
    private String fname;  // 박람회 이름
    private String fimg;
    private String fplace;
    private int fprice;
    private String furl;
}//class

package web.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class WishlistDto {

    private int wno;    // 즐겨찾기 번호
    private int mno;    // 회원 번호(FK)
    private int fno;    // 박람회 번호(FK)

}//class

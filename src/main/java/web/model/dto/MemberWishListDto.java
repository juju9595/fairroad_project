package web.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class MemberWishListDto { // 회원별 즐겨찾기된 리스트 Dto

    private int mno;                    // 회원번호
    private List<WishListDto> wishfair; // 즐겨찾기된 박람회 리스트

}

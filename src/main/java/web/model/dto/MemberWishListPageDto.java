package web.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class MemberWishListPageDto {
    private int currentPage;         // 현재 페이지
    private int totalPage;           // 전체 페이지 수
    private int totalCount;          // 총 즐겨찾기 개수
    private List<WishListDto> wishList;  // 실제 즐겨찾기 데이터
}
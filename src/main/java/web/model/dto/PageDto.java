package web.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class PageDto {
    private int totalCount;     // 1. 전체 자료 개수
    private int currentPage;    // 2. 현재 페이지 번호
    private int totalPage;      // 3. 전체 페이지 수
    private int startBtn;       // 4. 페이징 버튼 시작번호
    private int endBtn;         // 5. 페이징 버튼 끝번호
    private int perCount;       // 6. 1페이지당 보이는 자료수
    private Object data;        // 7. 페이징된 자료들
}//class end

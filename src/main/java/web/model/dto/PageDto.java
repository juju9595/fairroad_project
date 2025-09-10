package web.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class PageDto {
    private int totalCount;     // 전체 자료 개수
    private int currentPage;    // 현재 페이지 번호
    private int totalPage;      // 전체 페이지 수
    private int startBtn;       // 페이징 버튼 시작번호 (무한스크롤이면 필요 X)
    private int endBtn;         // 페이징 버튼 끝번호 (무한스크롤이면 필요 X)
    private int perCount;       // 1페이지당 보이는 자료수
    private Object data;        // 페이징된 자료들
    private boolean lastPage;   // 마지막 페이지 여부 (추가)
}
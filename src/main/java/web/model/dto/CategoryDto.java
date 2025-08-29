package web.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class CategoryDto {
    private int cno;        // 카테고리 번호
    private String cname;   // 카테고리 이름
}//class end

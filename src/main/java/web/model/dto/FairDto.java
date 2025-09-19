package web.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class FairDto {

    private int fno;            // 박람회 번호
    private int cno;            // 카테고리 번호(FK)
    private String fname;       // 박람회 이름
    private String fimg;        // 박람회 메인 이미지
    private String fplace;      // 박람회 장소
    private  int fprice;        // 박람회 가격
    private  String furl;       // 박람회 링크
    private String finfo;       // 박람회 상세정보
    private  String start_date; // 박람회 개최일
    private String end_date;    // 박람회 마감일
    private int fcount;         // 조회수

    //  업로드에 사용할 MultipartFile 객체를 List타입으로 여러개 첨부파일 받아오기
    private List<MultipartFile> uploads;
    //  조회시 업로드된 파일명을 문자열로 조회하기
    private List<String>images;


}//class end

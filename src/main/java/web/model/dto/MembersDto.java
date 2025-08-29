package web.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class MembersDto {
    private int mno;            // 회원 번호
    private String mid;         // 회원 아이디
    private String mpwd;        // 회원 비밀번호
    private String mname;       // 회원 이름
    private String mbirth;      // 회원 생년월일
    private String mphone;      // 회원 연락처
    private String memail;      // 회원 이메일
    private String joindate;    // 회원 가입일
    private String maddress;    // 회원 주소
}//class end

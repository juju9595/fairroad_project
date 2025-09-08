package web.model.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class AlarmDto { // class start

    private int ano;
    private int mno;
    private int fno;
    private String message;
    private LocalDateTime createdAt;

} // class end

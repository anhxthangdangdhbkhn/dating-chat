package vn.dating.chat.dto;

import lombok.*;

@Getter
@Setter
@ToString
@Data
@NoArgsConstructor
@AllArgsConstructor
public class OutputMessage {
    private String name;
    private String message;
    private String time;
}

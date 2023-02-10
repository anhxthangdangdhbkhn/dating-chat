package vn.dating.chat.dto.messages;

import lombok.*;

@Getter
@Setter
@ToString
@Data
@NoArgsConstructor
@AllArgsConstructor
public class GreetingOutput {
    private String name;
    private String message;
    private String time;
}

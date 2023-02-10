package vn.dating.chat.dto.messages;

import lombok.*;

@Getter
@Setter
@ToString
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Greeting {
    private String name;
    private String message;
}

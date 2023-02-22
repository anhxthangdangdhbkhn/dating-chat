package vn.dating.chat.dto.messages.socket;

import lombok.*;

@Getter
@Setter
@ToString
@Data
@NoArgsConstructor
@AllArgsConstructor
public class MessagePrivateGroupOutputDto {
    private String content;
    private Long groupId;
    private String senderId;
    private String time;
}

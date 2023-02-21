package vn.dating.chat.dto.messages;

import lombok.*;

@Getter
@Setter
@ToString
@Data
@NoArgsConstructor
@AllArgsConstructor
public class MessagePrivateRoomDto {
    private MessageType type;
    private String content;
    private String time;
}

package vn.dating.chat.dto.messages;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@Data
public class ChatMessage {
    private String sender;
    private String content;
    private String chatRoomId;
}

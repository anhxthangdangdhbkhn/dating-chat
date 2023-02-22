package vn.dating.chat.dto.messages;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@Data
public class ChatMessageDto {
    private String time;
    private String content;
    private String id;
}

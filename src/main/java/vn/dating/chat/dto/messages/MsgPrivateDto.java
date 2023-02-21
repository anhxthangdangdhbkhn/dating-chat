package vn.dating.chat.dto.messages;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MsgPrivateDto {
    private String senderId;
    private String recipientId;
    private String content;
    private String time;
}

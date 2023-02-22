package vn.dating.chat.dto.messages.socket;

import lombok.*;
import vn.dating.chat.model.audit.DateAudit;


import java.io.Serializable;

@Getter
@Setter
@ToString
@Data
@NoArgsConstructor
@AllArgsConstructor
public class MessagePrivateDto extends DateAudit implements Serializable {
    private String senderId;
    private String recipientId;
    private String content;
    private String time;
}

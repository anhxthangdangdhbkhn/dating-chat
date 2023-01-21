package vn.dating.chat.dto;

import lombok.*;
import vn.dating.chat.model.audit.DateAudit;

import java.io.Serializable;

@Getter
@Setter
@ToString
@Data
@NoArgsConstructor
@AllArgsConstructor
public class MessageDto extends DateAudit implements Serializable {
    private String chatId;
    private String senderId;
    private Long recipientId;
    private String content;
}

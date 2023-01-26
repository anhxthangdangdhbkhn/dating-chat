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
public class MessageNewDto extends DateAudit implements Serializable {
    private String senderId;
    private String recipientId;
    private String content;
}

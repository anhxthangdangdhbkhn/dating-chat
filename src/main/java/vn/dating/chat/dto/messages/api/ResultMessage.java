package vn.dating.chat.dto.messages.api;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ResultMessage {
    private Long id;
    private String content;
    private String senderName;
    private String senderEmail;
}

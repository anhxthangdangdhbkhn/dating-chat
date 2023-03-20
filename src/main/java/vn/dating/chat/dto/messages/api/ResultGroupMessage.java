package vn.dating.chat.dto.messages.api;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ResultGroupMessage {
    private Long groupId;
    private Long id;
    private String content;
    private String senderUsername;
    private String senderEmail;
    private Instant createdAt;
}

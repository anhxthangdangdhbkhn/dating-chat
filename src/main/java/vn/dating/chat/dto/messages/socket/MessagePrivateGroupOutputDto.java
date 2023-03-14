package vn.dating.chat.dto.messages.socket;

import lombok.*;

import java.time.Instant;

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
    private Instant createdAt;
    private long id;
}

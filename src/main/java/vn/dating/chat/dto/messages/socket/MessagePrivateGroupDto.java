package vn.dating.chat.dto.messages.socket;

import lombok.*;

@Getter
@Setter
@ToString
@Data
@NoArgsConstructor
@AllArgsConstructor
public class MessagePrivateGroupDto {
    private String content;
    private Long groupId;
    private String time;
}

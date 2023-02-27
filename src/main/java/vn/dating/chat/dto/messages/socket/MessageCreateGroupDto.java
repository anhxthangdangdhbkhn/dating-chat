package vn.dating.chat.dto.messages.socket;

import lombok.*;
import vn.dating.chat.model.GroupType;

@Getter
@Setter
@ToString
@Data
@NoArgsConstructor
@AllArgsConstructor
public class MessageCreateGroupDto {
    private GroupType type;
    private String with;
    private String time;
    private String content;
}

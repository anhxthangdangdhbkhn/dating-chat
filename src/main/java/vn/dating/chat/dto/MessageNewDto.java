package vn.dating.chat.dto;

import lombok.*;

@Getter
@Setter
@ToString
@Data
@NoArgsConstructor
@AllArgsConstructor
public class MessageNewDto {
    private String from;
    private String text;
    private MessageType type;
    public enum  MessageType{
        CHAT,LEAVE,JOIN
    }
}

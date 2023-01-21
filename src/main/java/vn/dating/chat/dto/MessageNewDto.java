package vn.dating.chat.dto;

import lombok.*;

@Getter
@Setter
@ToString
@Data
@NoArgsConstructor
@AllArgsConstructor
public class MessageNewDto {
    private String name;
    private String message;
}

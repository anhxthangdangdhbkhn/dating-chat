package vn.dating.chat.dto.messages.api;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ResultGroupMemberDto {
    private String email;
    private String username;
    private Long id;
    private String avatar;
}

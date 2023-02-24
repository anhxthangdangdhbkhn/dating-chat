package vn.dating.chat.dto.messages.api;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import vn.dating.chat.dto.auth.UserDto;
import vn.dating.chat.model.GroupType;

import java.util.ArrayList;
import java.util.List;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ResultGroupDto {
    private Long id;
    private String name;
    private String time;
    private String adminEmail;
    private GroupType type;
    private List<UserDto> members = new ArrayList<>();
}

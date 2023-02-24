package vn.dating.chat.dto.messages.api;

import lombok.*;
import vn.dating.chat.model.GroupMemberType;
import vn.dating.chat.model.GroupType;

import javax.validation.constraints.NotBlank;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@ToString
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateGroupDto {

    @NotBlank
    private String name;
    @NotBlank
    private String time;
    @NotBlank
    private GroupType type;
    @NotBlank
    private List<String> member = new ArrayList<>();
}

package vn.dating.chat.dto.messages.api;

import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@ToString
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateGroupDto {
    private String name;
    private String time;
    private String admin;
    private List<String> member = new ArrayList<>();
}

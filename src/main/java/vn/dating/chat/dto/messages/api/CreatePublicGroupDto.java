package vn.dating.chat.dto.messages.api;

import lombok.*;

import javax.validation.constraints.NotBlank;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@ToString
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreatePublicGroupDto {
    @NotBlank
    private String name;
    @NotBlank
    private List<String> member = new ArrayList<>();
}

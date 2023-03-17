package vn.dating.chat.dto.messages.api;

import lombok.*;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
@ToString
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreatePrivateGroupDto {
    @NotBlank
    private String withEmail;
}

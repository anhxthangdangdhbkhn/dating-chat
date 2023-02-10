package vn.dating.chat.dto.auth;

import lombok.*;

import javax.validation.constraints.NotBlank;


@Getter
@Setter
@ToString
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AuthLoginDto {
    @NotBlank
    private String email;

    @NotBlank
    private String password;
}

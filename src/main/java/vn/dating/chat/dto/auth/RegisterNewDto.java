package vn.dating.chat.dto.auth;

import lombok.*;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@ToString
@Data
@NoArgsConstructor
@AllArgsConstructor
public class RegisterNewDto {

    @NotNull
    @Email
    private String email;

    @NotNull
    private String password;

    @NotNull
    private String username;
}

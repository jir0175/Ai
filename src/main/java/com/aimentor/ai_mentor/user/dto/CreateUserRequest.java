package com.aimentor.ai_mentor.user.dto;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateUserRequest {
    @NotBlank(message = "Email cannot be blank")
    @Size(max = 50)
    @Email(message = "invalid email")
    private String email;
    @NotBlank(message = "Password cannot be blank")
    @Size(min = 8,max = 40)

    private String password;
}

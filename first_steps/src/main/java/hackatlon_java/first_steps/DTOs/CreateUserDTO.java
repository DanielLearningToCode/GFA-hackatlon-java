package hackatlon_java.first_steps.DTOs;


import hackatlon_java.first_steps.Validation.ValidEmail;
import hackatlon_java.first_steps.Validation.ValidPassword;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;


public class CreateUserDTO {

    @NotBlank(message = "Username is required")
    public String name;

    @ValidEmail(message = "Valid Email is required")
    public String email;

    @NotBlank(message = "Password is required.")
    @ValidPassword
    public String password;

    public CreateUserDTO(String name, String email, String password) {
        this.name = name;
        this.email = email;
        this.password = password;
    }

    public CreateUserDTO(){    }
}

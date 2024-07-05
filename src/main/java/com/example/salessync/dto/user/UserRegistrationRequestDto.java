package com.example.salessync.dto.user;

import com.example.salessync.validator.FieldMatch;
import com.example.salessync.validator.ValidFirstCapitalLetter;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

@Data
@FieldMatch(
        field = "password",
        fieldMatch = "repeatPassword",
        message = "Passwords do not match!"
        )
public class UserRegistrationRequestDto {
    @Email(regexp = "[a-z0-9._%+-]+@[a-z0-9.-]+\\.[a-z]{2,3}",
            flags = Pattern.Flag.CASE_INSENSITIVE)
    @NotNull
    @Length(max = 32)
    private String email;
    @NotNull
    @Length(min = 5, max = 15)
    private String password;
    @NotNull
    @Length(min = 5, max = 15)
    private String repeatPassword;
    @NotNull
    @ValidFirstCapitalLetter
    @Length(min = 2, max = 40)
    private String firstName;
    @NotNull
    @ValidFirstCapitalLetter
    @Length(min = 2, max = 40)
    private String lastName;
}

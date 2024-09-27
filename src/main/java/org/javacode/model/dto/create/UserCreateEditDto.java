package org.javacode.model.dto.create;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;

public record UserCreateEditDto(
        @Size(min = 3, max = 50)
        String firstname,
        @Size(min = 3, max = 50)
        String lastname,
        @Email
        String email) {

}

package org.ocularlens.expenserbe.requests;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import org.ocularlens.expenserbe.common.Role;
import org.ocularlens.expenserbe.custom.validation.ValueOfEnum;

public record RegisterRequest(
        @NotNull(message = "firstname is required") @Size(min = 3, message = "firstname should atleast be 3 characters long")
        String firstname,
        @NotNull(message = "lastname is required") @Size(min = 3, message = "lastname should atleast be 3 characters long")
        String lastname,
        @NotNull(message = "username is required") @Size(min = 5, message = "username should atleast be 5 characters long")
        String username,
        @NotNull(message = "password is required") @Size(min = 6, message = "password should atleast be 6 characters long")
        String password,
        @ValueOfEnum(enumClass = Role.class, message = "role must be any of [ADMIN,USER]")
        String role
) {
}


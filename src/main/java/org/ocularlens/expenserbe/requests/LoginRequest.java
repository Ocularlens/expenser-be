package org.ocularlens.expenserbe.requests;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record LoginRequest(
        @NotNull(message = "username is required") @Size(min = 5, message = "username should atleast be 5 characters long")
        String username,
        @NotNull(message = "password is required") @Size(min = 6, message = "password should atleast be 6 characters long")
        String password
) {}

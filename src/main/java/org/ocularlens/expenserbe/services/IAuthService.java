package org.ocularlens.expenserbe.services;

import org.ocularlens.expenserbe.models.User;
import org.ocularlens.expenserbe.response.JWTResponse;

public interface IAuthService {
    JWTResponse login(String username, String password);
    User newUser(String firstname, String lastname, String username, String password, String role);
}

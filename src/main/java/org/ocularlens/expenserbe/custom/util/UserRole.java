package org.ocularlens.expenserbe.custom.util;

import org.ocularlens.expenserbe.common.Role;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
public class UserRole {
    public static Role getScope(Authentication authentication) {
        Object[] scopes = authentication.getAuthorities().stream().toList().toArray();
        return Role.valueOf(scopes[0].toString().replaceAll("^SCOPE_", ""));
    }
}

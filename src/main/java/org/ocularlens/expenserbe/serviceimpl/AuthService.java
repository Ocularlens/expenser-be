package org.ocularlens.expenserbe.serviceimpl;

import org.ocularlens.expenserbe.common.Role;
import org.ocularlens.expenserbe.exception.UserNotFoundException;
import org.ocularlens.expenserbe.models.User;
import org.ocularlens.expenserbe.repository.UserRepository;
import org.ocularlens.expenserbe.response.JWTResponse;
import org.ocularlens.expenserbe.services.IAuthService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Optional;

@Service
public class AuthService implements IAuthService {
    private UserRepository userRepository;
    private PasswordEncoder passwordEncoder;
    private JwtEncoder jwtEncoder;

    public AuthService(UserRepository userRepository, PasswordEncoder passwordEncoder, JwtEncoder jwtEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtEncoder = jwtEncoder;
    }

    private String createToken(User user) {
        JwtClaimsSet claimsSet = JwtClaimsSet.builder()
                .issuer("self")
                .issuedAt(Instant.now())
                .expiresAt(Instant.now().plusSeconds(60 * 30))
                .subject(user.getUsername())
                .claim("scope", user.getRole())
                .build();

        JwtEncoderParameters parameters = JwtEncoderParameters.from(claimsSet);
        return jwtEncoder.encode(parameters).getTokenValue();
    }

    @Override
    public JWTResponse login(String username, String password) {
        Optional<User> user = userRepository.findByUsername(username);
        if (user.isEmpty()) throw new UserNotFoundException("username" + username);

        boolean matches = passwordEncoder.matches(password, user.get().getPassword());

        if (!matches) throw new UserNotFoundException("username" + username);

        return new JWTResponse(createToken(user.get()));
    }

    @Override
    public User newUser(String firstname, String lastname, String username, String password, String role) {
        String encodedPassword = passwordEncoder.encode(password);
        boolean roleCondition = role == null || role.equals(Role.USER.toString());
        Role userRole = roleCondition ? Role.USER : Role.ADMIN;

        return userRepository.save(
                new User(
                        firstname,
                        lastname,
                        username,
                        encodedPassword,
                        userRole
                )
        );
    }
}
package org.acme.application.service;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.acme.application.dto.LoginUserDTO;
import org.acme.application.dto.RegisterUserDTO;
import org.acme.domain.model.Users;
import org.acme.domain.repository.UserRepository;
import org.acme.infrastructure.config.security.JwtUtil;
import org.mindrot.jbcrypt.BCrypt;

import java.util.Optional;

@ApplicationScoped
@RequiredArgsConstructor(onConstructor_ = {@Inject})
public class AuthService {
    private final UserRepository userRepository;
    private final JwtUtil jwtUtil;

    public Optional<String> login(LoginUserDTO dto) {
        Optional<Users> userOpt = userRepository.findByEmail(dto.getEmail());
        if (userOpt.isPresent() && BCrypt.checkpw(dto.getPassword(), userOpt.get().getPassword())) {
            return Optional.of(jwtUtil.generateToken(dto.getEmail(), userOpt.get().getRole()));
        }
        return Optional.empty();
    }

    @Transactional
    public Optional<String> register(RegisterUserDTO dto) {
        // Check if email is already taken
        if (userRepository.findByEmail(dto.getEmail()).isPresent()) {
            return Optional.empty();
        }

        // Hash the password before storing
        String hashedPassword = BCrypt.hashpw(dto.getPassword(), BCrypt.gensalt());

        // Create new user
        Users newUser = new Users();
        newUser.setEmail(dto.getEmail());
        newUser.setPassword(hashedPassword);
        newUser.setRole(dto.getRole()); // Change if needed

        // Save the user in the database
        newUser.persist();

        // Generate a JWT token for the new user
        return Optional.of(jwtUtil.generateToken(newUser.getEmail(), newUser.getRole()));
    }
}

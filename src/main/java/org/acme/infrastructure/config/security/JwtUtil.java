package org.acme.infrastructure.config.security;

import jakarta.enterprise.context.ApplicationScoped;
import org.acme.domain.model.Role;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import io.smallrye.jwt.build.Jwt;

import java.util.Set;

@ApplicationScoped
public class JwtUtil {

    @ConfigProperty(name = "mp.jwt.verify.issuer")
    String issuer;

    public String generateToken(String email, Role role) {
        return Jwt.issuer(issuer)
                .upn(email)
                .groups(Set.of(role.name()))
                .sign();
    }
}

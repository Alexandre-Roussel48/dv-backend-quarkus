package org.acme.domain.repository;

import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;
import org.acme.domain.model.Users;

import java.util.Optional;

@ApplicationScoped
public class UserRepository implements PanacheRepository<Users> {
    public Optional<Users> findByEmail(String email) {
        return find("email", email).firstResultOptional();
    }
}

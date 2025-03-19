package org.acme.domain.repository;

import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;
import org.acme.domain.model.Session;

import java.time.LocalDateTime;
import java.util.Optional;

@ApplicationScoped
public class SessionRepository implements PanacheRepository<Session>  {
    public Optional<Session> findByDate(LocalDateTime date) {
        return find("beginDate <= ?1 AND endDate > ?1", date).firstResultOptional();
    }
}

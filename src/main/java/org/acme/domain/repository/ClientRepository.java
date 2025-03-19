package org.acme.domain.repository;

import io.quarkus.hibernate.orm.panache.PanacheRepository;
import io.quarkus.panache.common.Parameters;
import jakarta.enterprise.context.ApplicationScoped;
import org.acme.domain.model.Client;

import java.util.List;

@ApplicationScoped
public class ClientRepository  implements PanacheRepository<Client> {
    public List<Client> findByEmail(String email) {
        return find("""
                (:email IS NULL OR LOWER(email) LIKE :email)
                """,
                Parameters.with("email", email)
        ).list();
    }
}

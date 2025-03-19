package org.acme.domain.repository;

import io.quarkus.hibernate.orm.panache.PanacheRepository;
import io.quarkus.panache.common.Parameters;
import jakarta.enterprise.context.ApplicationScoped;
import org.acme.domain.model.Game;

import java.util.List;

@ApplicationScoped
public class GameRepository implements PanacheRepository<Game> {
    public List<Game> findByQuery(String query) {
        return find("""
                :query IS NULL OR LOWER(name) LIKE :query OR LOWER(editor) LIKE :query OR CAST(id AS string) LIKE :query
                """,
                Parameters.with("query", query != null ? query.toLowerCase() + "%" : null)
        ).list();
    }
}

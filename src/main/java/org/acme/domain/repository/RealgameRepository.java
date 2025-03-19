package org.acme.domain.repository;

import io.quarkus.hibernate.orm.panache.PanacheRepository;
import io.quarkus.panache.common.Parameters;
import jakarta.enterprise.context.ApplicationScoped;
import org.acme.domain.model.Realgame;
import org.acme.domain.model.Status;

import java.math.BigDecimal;
import java.util.List;

@ApplicationScoped
public class RealgameRepository implements PanacheRepository<Realgame> {
    public List<Realgame> findStockedBySessionAndQuery(Long sessionId, String query, BigDecimal minPrice, BigDecimal maxPrice) {
        return find("""
                relatedSession.id = :sessionId AND status = :status
                AND (:query IS NULL OR LOWER(game.name) LIKE :query OR LOWER(game.editor) LIKE :query OR CAST(id AS string) LIKE :query)
                AND (:minPrice IS NULL OR unitPrice >= :minPrice)
                AND (:maxPrice IS NULL OR unitPrice <= :maxPrice)
                """,
                Parameters.with("sessionId", sessionId)
                        .and("status", Status.STOCK)
                        .and("query", query != null ? query.toLowerCase() + "%" : null)
                        .and("minPrice", minPrice)
                        .and("maxPrice", maxPrice)
        ).list();
    }

    public List<Realgame> findByClient(Long clientId) {
        return find("""
                seller.id = :clientId
                """,
                Parameters.with("clientId", clientId)
        ).list();
    }
}

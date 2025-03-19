package org.acme.domain.repository;

import io.quarkus.hibernate.orm.panache.PanacheRepository;
import io.quarkus.panache.common.Parameters;
import jakarta.enterprise.context.ApplicationScoped;
import org.acme.domain.model.Transaction;
import org.acme.domain.model.Type;

import java.util.Collection;
import java.util.List;

@ApplicationScoped
public class TransactionRepository implements PanacheRepository<Transaction> {
    private static final String QUERY_ALL = """
    relatedSession.id = :sessionId AND type = :type
    """;
    private static final String SESSION_ID = "sessionId";
    private static final String TYPE = "type";
    private static final String CLIENT_ID = "clientId";

    public List<Transaction> findPaidTransactionsBySessionAndClient(Long sessionId, Long id) {
        return find("""
                relatedSession.id = :sessionId AND type = :type AND client.id = :clientId
                """,
                Parameters.with(SESSION_ID, sessionId)
                        .and(TYPE, Type.PAY)
                        .and(CLIENT_ID, id)
        ).list();
    }

    public List<Transaction> findSaleTransactionsBySessionAndClient(Long sessionId, Long id) {
        return find("""
                relatedSession.id = :sessionId AND type = :type AND realgame.seller.id = :clientId
                """,
                Parameters.with(SESSION_ID, sessionId)
                        .and(TYPE, Type.SALE)
                        .and(CLIENT_ID, id)
        ).list();
    }

    public List<Transaction> findAllDepositTransactionsBySession(Long sessionId) {
        return find(QUERY_ALL,
                Parameters.with(SESSION_ID, sessionId)
                        .and(TYPE, Type.DEPOSIT)
        ).list();
    }

    public List<Transaction> findAllCommissionTransactionsBySession(Long sessionId) {
        return find(QUERY_ALL,
                Parameters.with(SESSION_ID, sessionId)
                        .and(TYPE, Type.COMMISSION)
        ).list();
    }

    public List<Transaction> findAllPaidTransactionsBySession(Long sessionId) {
        return find(QUERY_ALL,
                Parameters.with(SESSION_ID, sessionId)
                        .and(TYPE, Type.PAY)
        ).list();
    }

    public List<Transaction> findAllSaleTransactionsBySession(Long sessionId) {
        return find(QUERY_ALL,
                Parameters.with(SESSION_ID, sessionId)
                        .and(TYPE, Type.SALE)
        ).list();
    }
}

package org.acme.infrastructure.persistence;

import io.quarkus.runtime.Startup;
import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.acme.domain.model.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@ApplicationScoped
@Startup
@RequiredArgsConstructor
public class DatabaseSeeder {

    private final EntityManager entityManager;

    private Session session;
    private List<Game> games;
    private Client client;
    private List<Realgame> realgames;

    @PostConstruct
    void init() {
        seedDatabase();
    }

    @Transactional
    public void seedDatabase() {
        createSession();
        createGames();
        createClient();
        createRealgames();
        createTransactions();
    }

    private void createSession() {
        session = new Session();
        session.setBeginDate(LocalDateTime.parse("2024-10-01T00:00:00"));
        session.setEndDate(LocalDateTime.parse("2025-10-31T23:59:59"));
        session.setCommission(BigDecimal.valueOf(0.1));
        session.setFees(BigDecimal.valueOf(5.0));

        entityManager.persist(session);
    }

    private void createGames() {
        games = List.of(
                new Game("monopoly", "hasbro", null),
                new Game("mario", "nintendo", null),
                new Game("chess", "various", null),
                new Game("risk", "hasbro", null),
                new Game("catan", "kosmos", null),
                new Game("uno", "mattel", null),
                new Game("scrabble", "mattel", null),
                new Game("ticket to ride", "days of wonder", null),
                new Game("clue", "hasbro", null),
                new Game("carcassonne", "hans im gluck", null),
                new Game("pandemic", "z-man games", null),
                new Game("dixit", "libellud", null),
                new Game("azul", "plan b games", null),
                new Game("7 wonders", "repos production", null),
                new Game("gloomhaven", "cephalofair games", null),
                new Game("the crew", "kosmos", null),
                new Game("wingspan", "stonemaier games", null),
                new Game("terraforming mars", "stronghold games", null),
                new Game("love letter", "aeg", null),
                new Game("the resistance", "indie boards & cards", null)
        );

        for (Game game : games) {
            entityManager.persist(game);
        }
    }

    private void createClient() {
        client = new Client();
        client.setName("John");
        client.setSurname("Doe");
        client.setEmail("john@doe.com");
        client.setPhoneNumber("0606060606");

        entityManager.persist(client);
    }

    private void createRealgames() {
        realgames = List.of(
                new Realgame(BigDecimal.valueOf(10), Status.STOCK, session, client, games.get(0), null),
                new Realgame(BigDecimal.valueOf(10), Status.STOCK, session, client, games.get(0), null),
                new Realgame(BigDecimal.valueOf(10), Status.STOCK, session, client, games.get(0), null),
                new Realgame(BigDecimal.valueOf(10), Status.STOCK, session, client, games.get(0), null),
                new Realgame(BigDecimal.valueOf(10), Status.STOCK, session, client, games.get(0), null),
                new Realgame(BigDecimal.valueOf(10), Status.STOCK, session, client, games.get(0), null),
                new Realgame(BigDecimal.valueOf(10), Status.STOCK, session, client, games.get(0), null),
                new Realgame(BigDecimal.valueOf(10), Status.STOCK, session, client, games.get(0), null),
                new Realgame(BigDecimal.valueOf(5), Status.STOCK, session, client, games.get(0), null),
                new Realgame(BigDecimal.valueOf(5), Status.STOCK, session, client, games.get(1), null),
                new Realgame(BigDecimal.valueOf(5), Status.STOCK, session, client, games.get(1), null),
                new Realgame(BigDecimal.valueOf(5), Status.STOCK, session, client, games.get(1), null),
                new Realgame(BigDecimal.valueOf(5), Status.STOCK, session, client, games.get(1), null),
                new Realgame(BigDecimal.valueOf(5), Status.STOCK, session, client, games.get(1), null),
                new Realgame(BigDecimal.valueOf(5), Status.STOCK, session, client, games.get(1), null),
                new Realgame(BigDecimal.valueOf(5), Status.STOCK, session, client, games.get(2), null),
                new Realgame(BigDecimal.valueOf(5), Status.STOCK, session, client, games.get(2), null),
                new Realgame(BigDecimal.valueOf(5), Status.STOCK, session, client, games.get(2), null),
                new Realgame(BigDecimal.valueOf(5), Status.STOCK, session, client, games.get(2), null),
                new Realgame(BigDecimal.valueOf(5), Status.STOCK, session, client, games.get(2), null),
                new Realgame(BigDecimal.valueOf(5), Status.STOCK, session, client, games.get(2), null),
                new Realgame(BigDecimal.valueOf(5), Status.STOCK, session, client, games.get(2), null),
                new Realgame(BigDecimal.valueOf(5), Status.STOCK, session, client, games.get(2), null),
                new Realgame(BigDecimal.valueOf(5), Status.STOCK, session, client, games.get(2), null),
                new Realgame(BigDecimal.valueOf(5), Status.STOCK, session, client, games.get(3), null),
                new Realgame(BigDecimal.valueOf(5), Status.STOCK, session, client, games.get(3), null),
                new Realgame(BigDecimal.valueOf(5), Status.STOCK, session, client, games.get(4), null),
                new Realgame(BigDecimal.valueOf(5), Status.STOCK, session, client, games.get(5), null),
                new Realgame(BigDecimal.valueOf(5), Status.STOCK, session, client, games.get(6), null),
                new Realgame(BigDecimal.valueOf(5), Status.STOCK, session, client, games.get(7), null),
                new Realgame(BigDecimal.valueOf(5), Status.STOCK, session, client, games.get(8), null),
                new Realgame(BigDecimal.valueOf(5), Status.STOCK, session, client, games.get(9), null),
                new Realgame(BigDecimal.valueOf(5), Status.STOCK, session, client, games.get(10), null)
        );

        for (Realgame realgame : realgames) {
            entityManager.persist(realgame);
        }
    }

    private void createTransactions() {
        List<Transaction> transactions = List.of(
                // DEPOSIT Transactions
                new Transaction(LocalDateTime.parse("2024-10-01T10:00:00"), BigDecimal.valueOf(100.0), Type.DEPOSIT, session, client, null),
                new Transaction(LocalDateTime.parse("2024-10-02T11:00:00"), BigDecimal.valueOf(50.0), Type.DEPOSIT, session, client, null),

                // COMMISSION Transactions
                new Transaction(LocalDateTime.parse("2024-10-03T12:00:00"), BigDecimal.valueOf(10.0), Type.COMMISSION, session, client, realgames.get(0)),
                new Transaction(LocalDateTime.parse("2024-10-04T13:00:00"), BigDecimal.valueOf(15.0), Type.COMMISSION, session, client, realgames.get(1)),

                // SALE Transactions
                new Transaction(LocalDateTime.parse("2024-10-05T14:00:00"), BigDecimal.valueOf(20.0), Type.SALE, session, client, realgames.get(0)),
                new Transaction(LocalDateTime.parse("2024-10-06T15:00:00"), BigDecimal.valueOf(25.0), Type.SALE, session, client, realgames.get(1)),

                // PAY Transactions
                new Transaction(LocalDateTime.parse("2024-10-07T16:00:00"), BigDecimal.valueOf(30.0), Type.PAY, session, client, null)
        );

        for (Transaction transaction : transactions) {
            entityManager.persist(transaction);
        }
    }
}

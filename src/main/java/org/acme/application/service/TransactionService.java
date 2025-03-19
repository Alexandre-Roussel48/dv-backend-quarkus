package org.acme.application.service;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.acme.application.dto.TransactionDTO;
import org.acme.domain.model.Realgame;
import org.acme.domain.model.Session;
import org.acme.domain.model.Transaction;
import org.acme.domain.model.Type;
import org.acme.domain.repository.ClientRepository;
import org.acme.domain.repository.RealgameRepository;
import org.acme.domain.repository.SessionRepository;
import org.acme.domain.repository.TransactionRepository;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@ApplicationScoped
@RequiredArgsConstructor(onConstructor_ = {@Inject})
public class TransactionService {
    private final TransactionRepository transactionRepository;
    private final SessionRepository sessionRepository;
    private final RealgameRepository realgameRepository;
    private final ClientRepository clientRepository;

    public BigDecimal getCurrentDueByClient(Long id) {
        Session session = sessionRepository.findByDate(LocalDateTime.now())
                .orElseThrow(() -> new NotFoundException("Opened session on " + LocalDateTime.now() + " not found"));
        BigDecimal sales = transactionRepository.findSaleTransactionsBySessionAndClient(session.id, id)
                .stream()
                .map(Transaction::getValue)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        BigDecimal paid = transactionRepository.findPaidTransactionsBySessionAndClient(session.id, id)
                .stream()
                .map(Transaction::getValue)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        return sales.subtract(paid);
    }

    public BigDecimal getCurrentWithdrawnByClient(Long id) {
        Session session = sessionRepository.findByDate(LocalDateTime.now())
                .orElseThrow(() -> new NotFoundException("Opened session on " + LocalDateTime.now() + " not found"));
        return transactionRepository.findPaidTransactionsBySessionAndClient(session.id, id)
                .stream()
                .map(Transaction::getValue)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    @Transactional
    public void createSaleTransaction(Long id) {
        Session session = sessionRepository.findByDate(LocalDateTime.now())
                .orElseThrow(() -> new NotFoundException("Opened session on " + LocalDateTime.now() + " not found"));
        Realgame realgame = realgameRepository.findById(id);

        Transaction saleTransaction = new Transaction();
        saleTransaction.setDate(LocalDateTime.now());
        saleTransaction.setRealgame(realgame);
        saleTransaction.setType(Type.SALE);
        saleTransaction.setValue(realgame.getUnitPrice().subtract(realgame.getUnitPrice().multiply(session.getCommission())));
        saleTransaction.setRelatedSession(session);

        Transaction commissionTransaction = new Transaction();
        commissionTransaction.setDate(LocalDateTime.now());
        commissionTransaction.setRealgame(realgame);
        commissionTransaction.setType(Type.COMMISSION);
        commissionTransaction.setValue(realgame.getUnitPrice().multiply(session.getCommission()));
        commissionTransaction.setRelatedSession(session);

        saleTransaction.persist();
        commissionTransaction.persist();
    }

    @Transactional
    public void createPayTransaction(Long id) {
        Session session = sessionRepository.findByDate(LocalDateTime.now())
                .orElseThrow(() -> new NotFoundException("Opened session on " + LocalDateTime.now() + " not found"));
        Transaction payTransaction = new Transaction();
        payTransaction.setDate(LocalDateTime.now());
        payTransaction.setType(Type.PAY);
        payTransaction.setRelatedSession(session);
        payTransaction.setValue(getCurrentDueByClient(id));
        payTransaction.setClient(clientRepository.findById(id));
        payTransaction.persist();
    }

    @Transactional
    public void createDepositTransaction(Long id) {
        Session session = sessionRepository.findByDate(LocalDateTime.now())
                .orElseThrow(() -> new NotFoundException("Opened session on " + LocalDateTime.now() + " not found"));
        Realgame realgame = realgameRepository.findById(id);
        Transaction depositTransaction = new Transaction();
        depositTransaction.setDate(LocalDateTime.now());
        depositTransaction.setType(Type.DEPOSIT);
        depositTransaction.setRelatedSession(session);
        depositTransaction.setValue(realgame.getUnitPrice().multiply(session.getFees()));
        depositTransaction.setClient(realgame.getSeller());
        depositTransaction.persist();
    }

    public BigDecimal getCurrentTotalDepositFees() {
        Session session = sessionRepository.findByDate(LocalDateTime.now())
                .orElseThrow(() -> new NotFoundException("Opened session on " + LocalDateTime.now() + " not found"));
        return transactionRepository.findAllDepositTransactionsBySession(session.id)
                .stream()
                .map(Transaction::getValue)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    public BigDecimal getCurrentTotalDues() {
        Session session = sessionRepository.findByDate(LocalDateTime.now())
                .orElseThrow(() -> new NotFoundException("Opened session on " + LocalDateTime.now() + " not found"));
        BigDecimal sales = transactionRepository.findAllSaleTransactionsBySession(session.id)
                .stream()
                .map(Transaction::getValue)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        BigDecimal paid = transactionRepository.findAllPaidTransactionsBySession(session.id)
                .stream()
                .map(Transaction::getValue)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        return sales.subtract(paid);
    }

    public BigDecimal getCurrentTotalCommissions() {
        Session session = sessionRepository.findByDate(LocalDateTime.now())
                .orElseThrow(() -> new NotFoundException("Opened session on " + LocalDateTime.now() + " not found"));
        return transactionRepository.findAllCommissionTransactionsBySession(session.id)
                .stream()
                .map(Transaction::getValue)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    public BigDecimal getCurrentTotalPaid() {
        Session session = sessionRepository.findByDate(LocalDateTime.now())
                .orElseThrow(() -> new NotFoundException("Opened session on " + LocalDateTime.now() + " not found"));
        return transactionRepository.findAllPaidTransactionsBySession(session.id)
                .stream()
                .map(Transaction::getValue)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    public BigDecimal getCurrentTotalTreasury() {
        return getCurrentTotalCommissions().add(getCurrentTotalDepositFees());
    }

    public List<TransactionDTO> getAllTransactions() {
        return transactionRepository.findAll()
                .stream()
                .map(transaction -> {
                    TransactionDTO dto = new TransactionDTO();
                    dto.setDate(transaction.getDate());
                    dto.setValue(transaction.getValue());
                    dto.setType(transaction.getType());
                    return dto;
                }).toList();
    }
}

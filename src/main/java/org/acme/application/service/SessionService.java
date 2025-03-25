package org.acme.application.service;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.acme.application.dto.IdSessionDTO;
import org.acme.application.dto.SessionDTO;
import org.acme.domain.model.Session;
import org.acme.domain.repository.SessionRepository;

import java.time.LocalDateTime;
import java.util.List;

@ApplicationScoped
@RequiredArgsConstructor(onConstructor_ = {@Inject})
public class SessionService {
    private final SessionRepository sessionRepository;

    public SessionDTO getCurrentSession() {
        Session session = sessionRepository.findByDate(LocalDateTime.now())
                .orElseThrow(() -> new NotFoundException("Opened session on " + LocalDateTime.now() + " not found."));
        SessionDTO sessionDTO = new SessionDTO();
        sessionDTO.setBeginDate(session.getBeginDate());
        sessionDTO.setEndDate(session.getEndDate());
        sessionDTO.setCommission(session.getCommission());
        sessionDTO.setFees(session.getFees());
        return sessionDTO;
    }

    @Transactional
    public void createSession(SessionDTO dto) {
        Session session = new Session();
        session.setBeginDate(dto.getBeginDate());
        session.setEndDate(dto.getEndDate());
        session.setCommission(dto.getCommission());
        session.setFees(dto.getFees());
        sessionRepository.persist(session);
    }

    public void updateSession(Long id, SessionDTO dto) {
        Session session = sessionRepository.findById(id);
        if (session != null) {
            session.setBeginDate(dto.getBeginDate() != null ? dto.getBeginDate() : session.getBeginDate());
            session.setEndDate(dto.getEndDate() != null ? dto.getEndDate() : session.getEndDate());
            session.setCommission(dto.getCommission() != null ? dto.getCommission() : session.getCommission());
            session.setFees(dto.getFees() != null ? dto.getFees() : session.getFees());
        } else {
            throw new NotFoundException("Session with ID " + id + " not found.");
        }
    }

    @Transactional
    public void deleteSession(Long id) {
        if (!sessionRepository.deleteById(id)) {
            throw new NotFoundException("Session with ID " + id + " not found.");
        }
    }

    public List<IdSessionDTO> getSessions() {
        return sessionRepository.findAll().stream().map((session -> {
            IdSessionDTO sessionDTO = new IdSessionDTO();
            sessionDTO.setId(session.id);
            sessionDTO.setBeginDate(session.getBeginDate());
            sessionDTO.setEndDate(session.getEndDate());
            sessionDTO.setCommission(session.getCommission());
            sessionDTO.setFees(session.getFees());
            return sessionDTO;
        })).toList();
    }
}

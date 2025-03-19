package org.acme.application.service;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.acme.application.dto.DepositItemDTO;
import org.acme.application.dto.IdRealgameDTO;
import org.acme.application.dto.RealgameDTO;
import org.acme.domain.model.Realgame;
import org.acme.domain.model.Session;
import org.acme.domain.model.Status;
import org.acme.domain.repository.ClientRepository;
import org.acme.domain.repository.GameRepository;
import org.acme.domain.repository.RealgameRepository;
import org.acme.domain.repository.SessionRepository;

import java.time.LocalDateTime;
import java.util.List;

@ApplicationScoped
@RequiredArgsConstructor(onConstructor_ = {@Inject})
public class RealgameService {
    private final RealgameRepository realgameRepository;
    private final SessionRepository sessionRepository;
    private final GameRepository gameRepository;
    private final ClientRepository clientRepository;

    public List<IdRealgameDTO> getRealgamesByClient(Long id) {
        return realgameRepository.findByClient(id).stream().map(realgame -> {
            IdRealgameDTO dto = new IdRealgameDTO();
            dto.setId(realgame.id);
            dto.setUnitPrice(realgame.getUnitPrice());
            dto.setStatus(realgame.getStatus());
            dto.setName(realgame.getGame().getName());
            dto.setEditor(realgame.getGame().getEditor());
            return dto;
        }).toList();
    }

    public List<IdRealgameDTO> getRealgamesForSale(String query) {
        Session session = sessionRepository.findByDate(LocalDateTime.now())
                .orElseThrow(() -> new NotFoundException("Opened session on " + LocalDateTime.now() + " not found"));
        return realgameRepository.findStockedBySessionAndQuery(session.id, query, null, null)
                .stream()
                .map(realgame -> {
                    IdRealgameDTO dto = new IdRealgameDTO();
                    dto.setId(realgame.id);
                    dto.setUnitPrice(realgame.getUnitPrice());
                    dto.setStatus(realgame.getStatus());
                    dto.setName(realgame.getGame().getName());
                    dto.setEditor(realgame.getGame().getEditor());
                    return dto;
                }).toList();
    }

    @Transactional
    public void updateRealgameStatusSold(Long id) {
        Realgame realgame = realgameRepository.findById(id);
        if (realgame != null) {
            realgame.setStatus(Status.SOLD);
        } else {
            throw new NotFoundException("Realgame with ID " + id + " not found.");
        }
    }

    @Transactional
    public Long createRealgame(DepositItemDTO dto) {
        Realgame realgame = new Realgame();
        realgame.setUnitPrice(dto.getUnitPrice());
        realgame.setStatus(Status.STOCK);
        realgame.setGame(gameRepository.findById(dto.getGame().getId()));
        realgame.setSeller(clientRepository.findById(dto.getClient().getId()));
        realgame.persist();

        return realgame.id;
    }
}

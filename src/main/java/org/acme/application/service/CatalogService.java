package org.acme.application.service;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.acme.application.dto.CatalogItemDTO;
import org.acme.domain.model.Session;
import org.acme.domain.model.Realgame;
import org.acme.domain.repository.RealgameRepository;
import org.acme.domain.repository.SessionRepository;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@ApplicationScoped
@RequiredArgsConstructor(onConstructor_ = {@Inject})
public class CatalogService {
    private final RealgameRepository realgameRepository;
    private final SessionRepository sessionRepository;

    public List<CatalogItemDTO> getCurrentCatalog(String query, BigDecimal minPrice, BigDecimal maxPrice) {
        Session session = sessionRepository.findByDate(LocalDateTime.now())
                .orElseThrow(() -> new NotFoundException("Opened session on " + LocalDateTime.now() + " not found"));
        List<Realgame> realgames = realgameRepository.findStockedBySessionAndQuery(session.id, query, minPrice, maxPrice);

        return realgames.stream()
                .collect(Collectors.groupingBy(
                        item -> new GroupKey(item.getUnitPrice(), item.getSeller().id, item.getGame().id),
                        Collectors.collectingAndThen(Collectors.toList(), list -> {
                            Realgame first = list.getFirst();
                            return new CatalogItemDTO(
                                    first.getUnitPrice(),
                                    list.size(),
                                    first.getGame().getName(),
                                    first.getGame().getEditor(),
                                    first.getSeller().getName(),
                                    first.getSeller().getSurname());
                        })
                ))
                .values()
                .stream()
                .toList();
    }

    private record GroupKey(BigDecimal unitPrice, Long sellerId, Long gameId) {}
}

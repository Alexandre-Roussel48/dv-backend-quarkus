package org.acme.rest;

import jakarta.annotation.security.RolesAllowed;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import lombok.RequiredArgsConstructor;
import org.acme.application.dto.*;
import org.acme.application.service.GameService;
import org.acme.application.service.RealgameService;
import org.acme.application.service.SessionService;
import org.acme.application.service.TransactionService;
import org.acme.rest.gestionsubresources.ClientResource;
import org.acme.rest.gestionsubresources.SaleResource;
import org.acme.rest.gestionsubresources.TotalResource;

import java.util.List;
import java.util.Optional;

@ApplicationScoped
@RequiredArgsConstructor(onConstructor_ = {@Inject})
//@RolesAllowed("GESTION")
public class GestionResource {

    private final SessionService sessionService;
    private final GameService gameService;
    private final RealgameService realgameService;
    private final TransactionService transactionService;

    private final ClientResource clientResource;
    private final SaleResource saleResource;
    private final TotalResource totalResource;

    @GET
    @Path("/sessions")
    public List<SessionDTO> getSessions() {
        return sessionService.getSessions();
    }

    @GET
    @Path("/games")
    public List<IdGameDTO> getGames(
            @QueryParam("query") Optional<String> query
    ) {
        return gameService.getGames(query.orElse(""));
    }

    @GET
    @Path("/transactions")
    public List<TransactionDTO> getTransactions() {
        return transactionService.getAllTransactions();
    }

    @POST
    @Path("/deposits")
    @Consumes(MediaType.APPLICATION_JSON)
    public void registerDeposit(
        List<DepositItemDTO> deposits
    ) {
        for (DepositItemDTO depositItemDTO : deposits) {
            Long id = realgameService.createRealgame(depositItemDTO);
            transactionService.createDepositTransaction(id);
        }
    }

    @Path("/clients")
    public ClientResource client() {
        return clientResource;
    }

    @Path("/sales")
    public SaleResource sale() {
        return saleResource;
    }

    @Path("/totals")
    public TotalResource total() {
        return totalResource;
    }
}

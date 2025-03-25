package org.acme.rest.gestionsubresources;

import jakarta.annotation.security.RolesAllowed;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import lombok.RequiredArgsConstructor;
import org.acme.application.dto.IdRealgameDTO;
import org.acme.application.service.RealgameService;
import org.acme.application.service.TransactionService;

import java.util.List;
import java.util.Optional;

@ApplicationScoped
@RequiredArgsConstructor(onConstructor_ = {@Inject})
@RolesAllowed({"GESTION", "ADMIN"})
public class SaleResource {
    private final RealgameService realgameService;
    private final TransactionService transactionService;

    @GET
    @Path("/realgames")
    public List<IdRealgameDTO> getRealGamesForSale(
            @QueryParam("query") Optional<String> query
    ) {
        return realgameService.getRealgamesForSale(query.orElse(""));
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public void registerSale(
            List<Long> realgames
    ) {
        for (Long id : realgames) {
            realgameService.updateRealgameStatusSold(id);
            transactionService.createSaleTransaction(id);
        }
    }
}

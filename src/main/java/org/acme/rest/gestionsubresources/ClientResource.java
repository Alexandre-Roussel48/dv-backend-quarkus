package org.acme.rest.gestionsubresources;

import jakarta.annotation.security.RolesAllowed;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;

import jakarta.ws.rs.core.MediaType;
import lombok.RequiredArgsConstructor;
import org.acme.application.dto.ClientDTO;
import org.acme.application.dto.IdClientDTO;
import org.acme.application.dto.IdRealgameDTO;
import org.acme.application.dto.RealgameDTO;
import org.acme.application.service.ClientService;
import org.acme.application.service.RealgameService;
import org.acme.application.service.TransactionService;
import org.acme.domain.model.Client;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@ApplicationScoped
@RequiredArgsConstructor(onConstructor_ = {@Inject})
@RolesAllowed({"GESTION", "ADMIN"})
public class ClientResource {

    private final ClientService clientService;
    private final RealgameService realgameService;
    private final TransactionService transactionService;

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public IdClientDTO createClient(
            ClientDTO client
    ) {
        return clientService.createClient(client);
    }

    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    public void updateClient(
            @QueryParam("id") Long id,
            ClientDTO client
    ) {
        clientService.updateClient(id, client);
    }

    @GET
    public List<IdClientDTO> getClients(
            @QueryParam("id") Optional<Long> id,
            @QueryParam("email") Optional<String> email
    ) {
        return id.map(aLong -> List.of(clientService.getClient(aLong))).orElseGet(() -> clientService.getClients(email.orElse("")));
    }

    @GET
    @Path("/realgames")
    public List<IdRealgameDTO> getRealGamesByClient(
            @QueryParam("id") Long id
    ) {
        return realgameService.getRealgamesByClient(id);
    }

    @GET
    @Path("/due")
    public String dueClient(
            @QueryParam("id") Long id
    ) {
        return transactionService.getCurrentDueByClient(id).toString();
    }

    @GET
    @Path("/withdrawn")
    public String withdrawn(
            @QueryParam("id") Long id
    ) {
        return transactionService.getCurrentWithdrawnByClient(id).toString();
    }

    @GET
    @Path("/withdraw")
    public void withdraw(
            @QueryParam("id") Long id
    ) {
        transactionService.createPayTransaction(id);
    }
}

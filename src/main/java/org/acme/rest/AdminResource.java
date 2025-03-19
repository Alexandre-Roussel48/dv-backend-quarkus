package org.acme.rest;

import jakarta.annotation.security.RolesAllowed;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.MediaType;
import lombok.RequiredArgsConstructor;
import org.acme.application.service.ClientService;
import org.acme.rest.adminsubresources.GameResource;
import org.acme.rest.adminsubresources.SessionResource;

@ApplicationScoped
@RequiredArgsConstructor(onConstructor_ = {@Inject})
//@RolesAllowed("ADMIN")
public class AdminResource {

    private final ClientService clientService;

    private final SessionResource sessionResource;
    private final GameResource gameResource;

    @DELETE
    @Path("/clients")
    @Consumes(MediaType.APPLICATION_JSON)
    public void deleteClient(
            @QueryParam("id") Long id
    ) {
        clientService.deleteClient(id);
    }

    @Path("/sessions")
    public SessionResource session() {
        return sessionResource;
    }

    @Path("/games")
    public GameResource game() {
        return gameResource;
    }
}

package org.acme.rest.adminsubresources;

import jakarta.annotation.security.RolesAllowed;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import lombok.RequiredArgsConstructor;
import org.acme.application.dto.SessionDTO;
import org.acme.application.service.SessionService;

@ApplicationScoped
@RequiredArgsConstructor(onConstructor_ = {@Inject})
@RolesAllowed("ADMIN")
public class SessionResource {

    private final SessionService sessionService;

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public void createSession(
            SessionDTO session
    ) {
        sessionService.createSession(session);
    }

    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    public void updateSession(
            @QueryParam("id") Long id,
            SessionDTO session
    ) {
        sessionService.updateSession(id, session);
    }

    @DELETE
    public void deleteSession(
            @QueryParam("id") Long id
    ) {
        sessionService.deleteSession(id);
    }
}

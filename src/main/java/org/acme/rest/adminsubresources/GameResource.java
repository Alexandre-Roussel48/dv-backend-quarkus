package org.acme.rest.adminsubresources;

import jakarta.annotation.security.RolesAllowed;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import lombok.RequiredArgsConstructor;
import org.acme.application.dto.GameDTO;
import org.acme.application.service.GameService;

@ApplicationScoped
@RequiredArgsConstructor(onConstructor_ = {@Inject})
@RolesAllowed("ADMIN")
public class GameResource {

    private final GameService gameService;

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public void createGame(
            GameDTO game
    ) {
        gameService.createGame(game);
    }

    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    public void updateGame(
            @QueryParam("id") Long id,
            GameDTO game
    ) {
        gameService.updateGame(id, game);
    }

    @DELETE
    public void deleteGame(
            @QueryParam("id") Long id
    ) {
        gameService.deleteGame(id);
    }
}

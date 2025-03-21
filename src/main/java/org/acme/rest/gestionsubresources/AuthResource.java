package org.acme.rest.gestionsubresources;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import lombok.RequiredArgsConstructor;
import org.acme.application.dto.LoginUserDTO;
import org.acme.application.dto.RegisterUserDTO;
import org.acme.application.service.AuthService;

import java.util.Map;

@ApplicationScoped
@RequiredArgsConstructor(onConstructor_ = {@Inject})
public class AuthResource {

    private final AuthService authService;

    @Path("/login")
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response login(LoginUserDTO user) {
        return authService.login(user)
                .map(token -> Response.ok(Map.of("token", token)).build())
                .orElse(Response.status(Response.Status.UNAUTHORIZED).build());
    }

    @Path("/register")
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response register(RegisterUserDTO user) {
        return authService.register(user)
                .map(token -> Response.status(Response.Status.CREATED).entity(Map.of("token", token)).build())
                .orElse(Response.status(Response.Status.CONFLICT).entity("Email already exists").build());
    }
}

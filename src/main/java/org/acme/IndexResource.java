package org.acme;

import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.MediaType;
import lombok.RequiredArgsConstructor;
import org.acme.application.dto.CatalogItemDTO;
import org.acme.application.dto.SessionDTO;
import org.acme.application.service.CatalogService;
import org.acme.application.service.SessionService;
import org.acme.rest.AdminResource;
import org.acme.rest.GestionResource;
import org.acme.rest.gestionsubresources.AuthResource;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Path("/api")
@RequiredArgsConstructor(onConstructor_ = {@Inject})
public class IndexResource {

    private final SessionService sessionService;
    private final CatalogService catalogService;

    private final AdminResource adminResource;
    private final GestionResource gestionResource;
    private final AuthResource authResource;

    @GET
    @Path("/session")
    @Produces(MediaType.APPLICATION_JSON)
    public SessionDTO getCurrentSession() {
        return sessionService.getCurrentSession();
    }

    @GET
    @Path("/catalog")
    @Produces(MediaType.APPLICATION_JSON)
    public List<CatalogItemDTO> getCurrentCatalog(
            @QueryParam("query") Optional<String> query,
            @QueryParam("minPrice") Optional<BigDecimal> minPrice,
            @QueryParam("maxPrice") Optional<BigDecimal> maxPrice
    ) {
        return catalogService.getCurrentCatalog(query.orElse(""), minPrice.orElse(null), maxPrice.orElse(null));
    }

    @Path("/gestion")
    public GestionResource gestion() {
        return gestionResource;
    }

    @Path("/admin")
    public AdminResource admin() {
        return adminResource;
    }

    @Path("/auth")
    public AuthResource auth() {
        return authResource;
    }
}

package org.acme.rest.gestionsubresources;

import jakarta.annotation.security.RolesAllowed;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import lombok.RequiredArgsConstructor;
import org.acme.application.service.TransactionService;

@ApplicationScoped
@RequiredArgsConstructor(onConstructor_ = {@Inject})
@RolesAllowed("GESTION")
public class TotalResource {

    private final TransactionService transactionService;

    @GET
    @Path("/fee")
    public String getTotalDepositFees() {
        return transactionService.getCurrentTotalDepositFees().toString();
    }

    @GET
    @Path("/due")
    public String getTotalDues() {
        return transactionService.getCurrentTotalDues().toString();
    }

    @GET
    @Path("/commission")
    public String getTotalCommissions() {
        return transactionService.getCurrentTotalCommissions().toString();
    }

    @GET
    @Path("/paid")
    public String getTotalPaid() {
        return transactionService.getCurrentTotalPaid().toString();
    }

    @GET
    @Path("/treasury")
    public String getTotalTreasury() {
        return transactionService.getCurrentTotalTreasury().toString();
    }
}

package org.acme.application.dto;

import lombok.Data;
import org.acme.domain.model.Status;

import java.math.BigDecimal;

@Data
public class IdRealgameDTO {
    private Long id;
    private BigDecimal unitPrice;
    private Status status;
    private String name;
    private String editor;
}

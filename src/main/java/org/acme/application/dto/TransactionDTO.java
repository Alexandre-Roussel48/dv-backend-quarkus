package org.acme.application.dto;

import lombok.Data;
import org.acme.domain.model.Type;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class TransactionDTO {
    private LocalDateTime date;
    private BigDecimal value;
    private Type type;
}

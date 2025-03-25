package org.acme.application.dto;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class IdSessionDTO {
    private Long id;
    private LocalDateTime beginDate;
    private LocalDateTime endDate;
    private BigDecimal commission;
    private BigDecimal fees;
}

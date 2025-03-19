package org.acme.application.dto;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class DepositItemDTO {
    private BigDecimal unitPrice;
    private Integer quantity;
    private IdGameDTO game;
    private IdClientDTO client;
}

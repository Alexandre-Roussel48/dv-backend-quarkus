package org.acme.application.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CatalogItemDTO {
    private BigDecimal unitPrice;
    private Integer quantity;
    private String gameName;
    private String gameEditor;
    private String sellerName;
    private String sellerSurname;
}

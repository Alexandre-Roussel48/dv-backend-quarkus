package org.acme.domain.model;

import io.quarkus.hibernate.orm.panache.PanacheEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@EqualsAndHashCode(callSuper = true)
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Transaction extends PanacheEntity {
    private LocalDateTime date;
    private BigDecimal value;
    private Type type;

    @ManyToOne
    private Session relatedSession;

    @ManyToOne
    private Client client;

    @ManyToOne
    private Realgame realgame;
}

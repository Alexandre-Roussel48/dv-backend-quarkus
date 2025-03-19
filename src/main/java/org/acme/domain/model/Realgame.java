package org.acme.domain.model;

import io.quarkus.hibernate.orm.panache.PanacheEntity;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Realgame extends PanacheEntity {
    private BigDecimal unitPrice;
    private Status status;

    @ManyToOne
    private Session relatedSession;

    @ManyToOne
    private Client seller;

    @ManyToOne
    private Game game;

    @OneToMany(mappedBy = "realgame", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Transaction> transactions;

}

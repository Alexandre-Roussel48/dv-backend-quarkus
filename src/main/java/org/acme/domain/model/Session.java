package org.acme.domain.model;

import io.quarkus.hibernate.orm.panache.PanacheEntity;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Session extends PanacheEntity {
    private LocalDateTime beginDate;
    private LocalDateTime endDate;
    private BigDecimal commission;
    private BigDecimal fees;

    @OneToMany(mappedBy = "relatedSession", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Transaction> transactions;

    @OneToMany(mappedBy = "relatedSession", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Realgame> realgames;
}

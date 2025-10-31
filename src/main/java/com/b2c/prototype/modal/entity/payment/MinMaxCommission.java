package com.b2c.prototype.modal.entity.payment;

import com.b2c.prototype.modal.entity.price.Price;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.NamedQueries;
import jakarta.persistence.NamedQuery;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "min_max_commission")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@NamedQueries({
        @NamedQuery(
                name = "MinMaxCommission.getCommissionList",
                query = "SELECT m FROM MinMaxCommission m " +
                        "LEFT JOIN FETCH m.minCommission min " +
                        "LEFT JOIN FETCH min.currency minc " +
                        "LEFT JOIN FETCH m.maxCommission max " +
                        "LEFT JOIN FETCH max.currency maxc " +
                        "LEFT JOIN FETCH m.changeCommissionPrice ccp " +
                        "LEFT JOIN FETCH ccp.currency ccpc "
        ),
})
public class MinMaxCommission {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private CommissionValue minCommission;
    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private CommissionValue maxCommission;
    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(nullable = false)
    private Price changeCommissionPrice;
    private LocalDateTime lastUpdateTimestamp;
}

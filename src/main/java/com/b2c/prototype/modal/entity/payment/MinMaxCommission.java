package com.b2c.prototype.modal.entity.payment;

import com.b2c.prototype.modal.constant.CommissionType;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.NamedQueries;
import jakarta.persistence.NamedQuery;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.UpdateTimestamp;

import java.util.Date;

@Entity
@Table(name = "min_max_commission")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@NamedQueries({
        @NamedQuery(
                name = "MinMaxCommission.findByCommissionType",
                query = "SELECT m FROM MinMaxCommission m " +
                        "LEFT JOIN FETCH m.minCommission min " +
                        "LEFT JOIN FETCH min.currency minc " +
                        "LEFT JOIN FETCH m.maxCommission max " +
                        "LEFT JOIN FETCH max.currency maxc " +
                        "WHERE m.commissionType = :commissionType"
        ),
        @NamedQuery(
                name = "MinMaxCommission.getCommissionList",
                query = "SELECT m FROM MinMaxCommission m " +
                        "LEFT JOIN FETCH m.minCommission min " +
                        "LEFT JOIN FETCH min.currency minc " +
                        "LEFT JOIN FETCH m.maxCommission max " +
                        "LEFT JOIN FETCH max.currency maxc"
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
    @Enumerated(EnumType.STRING)
    @Column(name = "commission_type", nullable = false, unique = true)
    private CommissionType commissionType;
    private double changeCommissionValue;
    @UpdateTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    private Date lastUpdateTimestamp;
}

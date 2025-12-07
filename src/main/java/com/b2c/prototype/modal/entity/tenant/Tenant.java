package com.b2c.prototype.modal.entity.tenant;

import com.b2c.prototype.modal.entity.price.Currency;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.NamedQueries;
import jakarta.persistence.NamedQuery;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.ZoneId;

@Entity
@Table(name = "tenant")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@NamedQueries({
        @NamedQuery(
                name = "Tenant.findByCode",
                query = "SELECT e FROM Tenant e " +
                        "LEFT JOIN FETCH e.primaryCurrency ec " +
                        "WHERE e.code = : code"
        ),
        @NamedQuery(
                name = "Tenant.findAll",
                query = "SELECT e FROM Tenant e " +
                        "LEFT JOIN FETCH e.primaryCurrency ec "
        ),
        @NamedQuery(
                name = "Tenant.countByCurrency",
                query = "SELECT COUNT(e) FROM Tenant e " +
                        "JOIN e.primaryCurrency ec " +
                        "WHERE ec.key = :key"
        ),
})
public class Tenant {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(nullable = false, length = 64, unique = true)
    private String code;
    @Column(nullable = false, length = 128)
    private String value;
    @Column(nullable = false, length = 64)
    private String language;
    @Column(nullable = false, length = 16)
    private String defaultLocale;
    @ManyToOne(fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private Currency primaryCurrency;
    @Column(nullable = false, length = 64)
    private ZoneId timezone;
}

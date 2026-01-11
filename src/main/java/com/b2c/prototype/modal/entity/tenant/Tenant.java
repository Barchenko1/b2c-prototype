package com.b2c.prototype.modal.entity.tenant;

import com.b2c.prototype.modal.entity.price.Currency;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.NamedQueries;
import jakarta.persistence.NamedQuery;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.ZoneId;
import java.util.HashSet;
import java.util.Set;

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
                        "LEFT JOIN FETCH e.languages el " +
                        "WHERE e.code = : code"
        ),
        @NamedQuery(
                name = "Tenant.findAll",
                query = "SELECT e FROM Tenant e " +
                        "LEFT JOIN FETCH e.primaryCurrency ec " +
                        "LEFT JOIN FETCH e.languages el "
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
    @ManyToMany(fetch = FetchType.LAZY, cascade = {CascadeType.DETACH, CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH})
    @JoinTable(
            name = "tenant_language",
            joinColumns = {@JoinColumn(name = "tenant_id")},
            inverseJoinColumns = {@JoinColumn(name = "language_id")})
    @Builder.Default
    @EqualsAndHashCode.Exclude
    private Set<Language> languages = new HashSet<>();
    @Column(nullable = false, length = 16)
    private String defaultLocale;
    @ManyToOne(fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private Currency primaryCurrency;
    @Column(nullable = false, length = 64)
    private ZoneId timezone;

    public void addLanguage(Language language) {
        this.languages.add(language);
        language.getTenants().add(this);
    }

    public void removeLanguage(Language language) {
        this.languages.remove(language);
        language.getTenants().remove(this);
    }
}

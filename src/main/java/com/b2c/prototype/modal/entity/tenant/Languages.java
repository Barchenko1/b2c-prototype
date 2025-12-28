package com.b2c.prototype.modal.entity.tenant;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "language")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Languages {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(name = "language_code", length = 5, nullable = false)
    private String languageCode;   // e.g. "en", "pl"
    @Column(name = "country_code", length = 5)
    private String countryCode;    // e.g. "US", "PL"
    @Column(name = "locale", length = 10, nullable = false)
    private String locale;         // e.g. "en-US", "pl-PL"
    @Column(name = "name", nullable = false)
    private String name;
    @Column(name = "is_active", nullable = false)
    private boolean isActive = true;
}

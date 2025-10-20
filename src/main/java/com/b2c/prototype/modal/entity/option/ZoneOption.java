package com.b2c.prototype.modal.entity.option;

import com.b2c.prototype.modal.entity.price.Price;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
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
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Table(name = "zone_option")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@NamedQueries({
        @NamedQuery(
                name = "ZoneOption.findByValue",
                query = "SELECT zo FROM ZoneOption zo WHERE zo.value = :value"
        ),
        @NamedQuery(
                name = "ZoneOption.findAllWithPriceAndCurrency",
                query = "SELECT zo FROM ZoneOption zo " +
                        "LEFT JOIN FETCH zo.price p " +
                        "LEFT JOIN FETCH p.currency c " +
                        "WHERE zo.value = :value"
        ),
        @NamedQuery(
                name = "ZoneOption.all",
                query = "SELECT zo FROM ZoneOption zo " +
                        "LEFT JOIN FETCH zo.price p " +
                        "LEFT JOIN FETCH p.currency c"
        )
})
public class ZoneOption {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", updatable = false, nullable = false)
    private long id;
    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(nullable = false)
    private Price price;
    private String label;
    @Column(unique = true, nullable = false)
    private String value;

    @ManyToOne(fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST})
    @JoinColumn(name = "option_group_id", nullable = false)
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @JsonIgnore
    protected ZoneOptionGroup optionGroup;
}

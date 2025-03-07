package com.b2c.prototype.modal.entity.option;

import com.b2c.prototype.modal.base.constant.AbstractConstantEntity;
import com.b2c.prototype.modal.entity.price.Price;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.NamedQueries;
import jakarta.persistence.NamedQuery;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "zone_option")
@Data
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
@NamedQueries({
        @NamedQuery(
                name = "ZoneOption.findByValue",
                query = "SELECT zo FROM ZoneOption zo WHERE zo.value = : value"
        ),
        @NamedQuery(
                name = "ZoneOption.findAllWithPriceAndCurrency",
                query = "SELECT zo FROM ZoneOption zo " +
                        "LEFT JOIN FETCH zo.price p " +
                        "LEFT JOIN FETCH p.currency c " +
                        "WHERE zo.value = : value"
        ),
        @NamedQuery(
                name = "ZoneOption.all",
                query = "SELECT zo FROM ZoneOption zo " +
                        "LEFT JOIN FETCH zo.price p " +
                        "LEFT JOIN FETCH p.currency c"
        )
})
public class ZoneOption extends AbstractConstantEntity {
    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private Price price;
    @Column(name = "city", nullable = false)
    private String city;
    @Column(name = "zoneName", unique = true, nullable = false)
    private String zoneName;
}

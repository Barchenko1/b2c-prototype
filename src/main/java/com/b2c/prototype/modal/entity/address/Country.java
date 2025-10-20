package com.b2c.prototype.modal.entity.address;

import com.b2c.prototype.modal.base.constant.AbstractConstantEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.NamedQueries;
import jakarta.persistence.NamedQuery;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "country")
@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@NamedQueries({
        @NamedQuery(
                name = "Country.findByValue",
                query = "SELECT c FROM Country c WHERE c.value = :value"
        ),
        @NamedQuery(
                name = "Country.all",
                query = "SELECT c FROM Country c"
        )
})
public class Country extends AbstractConstantEntity {
    private String flagImagePath;
}

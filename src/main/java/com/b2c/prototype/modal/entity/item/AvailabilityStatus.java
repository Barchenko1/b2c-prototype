package com.b2c.prototype.modal.entity.item;

import com.b2c.prototype.modal.base.constant.AbstractConstantEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.NamedQueries;
import jakarta.persistence.NamedQuery;
import jakarta.persistence.Table;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Entity
@Table(name = "availability_status")
@SuperBuilder
@NoArgsConstructor
@NamedQueries({
        @NamedQuery(
                name = "AvailabilityStatus.findByValue",
                query = "SELECT a FROM AvailabilityStatus a WHERE a.value = : value"
        ),
        @NamedQuery(
                name = "AvailabilityStatus.all",
                query = "SELECT a FROM AvailabilityStatus a"
        )
})
public class AvailabilityStatus extends AbstractConstantEntity {
}

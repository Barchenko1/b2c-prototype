package com.b2c.prototype.modal.entity.store;

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
                name = "AvailabilityStatus.findByKey",
                query = "SELECT e FROM AvailabilityStatus e WHERE e.key = :key"
        ),
        @NamedQuery(
                name = "AvailabilityStatus.all",
                query = "SELECT e FROM AvailabilityStatus e"
        )
})
public class AvailabilityStatus extends AbstractConstantEntity {
}

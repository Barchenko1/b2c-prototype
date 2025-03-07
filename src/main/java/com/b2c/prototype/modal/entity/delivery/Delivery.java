package com.b2c.prototype.modal.entity.delivery;

import com.b2c.prototype.modal.base.delivery.AbstractDelivery;
import com.b2c.prototype.modal.entity.option.TimeDurationOption;
import com.b2c.prototype.modal.entity.option.ZoneOption;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Entity
@Table(name = "delivery")
@SuperBuilder
@NoArgsConstructor
public class Delivery extends AbstractDelivery {
    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
    private TimeDurationOption timeDurationOption;
    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
    private ZoneOption zoneOption;
}

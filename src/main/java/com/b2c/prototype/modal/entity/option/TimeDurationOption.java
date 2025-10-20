package com.b2c.prototype.modal.entity.option;

import com.b2c.prototype.modal.base.constant.AbstractConstantEntity;
import com.b2c.prototype.modal.entity.price.Price;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;
import java.time.ZoneId;

@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "time_duration_option")
@Data
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
public class TimeDurationOption extends AbstractConstantEntity {
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    @Column(name = "duration_in_min", nullable = false)
    private long durationInMin;
    private ZoneId timeZone;
    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(nullable = false)
    private Price price;
    @ManyToOne(fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST})
    @JoinColumn(name = "option_group_id", nullable = false)
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @JsonIgnore
    protected OptionGroup optionGroup;
}

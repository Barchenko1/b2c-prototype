package com.b2c.prototype.modal.entity.option;

import com.b2c.prototype.modal.base.constant.AbstractConstantEntity;
import com.b2c.prototype.modal.entity.price.Price;
import com.fasterxml.jackson.annotation.JsonFormat;
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

import java.time.LocalTime;

@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "time_duration_option")
@Data
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
@NamedQueries({
        @NamedQuery(
                name = "TimeDurationOption.findByValue",
                query = "SELECT td FROM TimeDurationOption td WHERE td.value = : value"
        ),
        @NamedQuery(
                name = "TimeDurationOption.findAllWithPriceAndCurrency",
                query = "SELECT td FROM TimeDurationOption td " +
                        "LEFT JOIN FETCH td.price p " +
                        "LEFT JOIN FETCH p.currency c " +
                        "WHERE td.value = : value"
        ),
        @NamedQuery(
                name = "TimeDurationOption.all",
                query = "SELECT td FROM TimeDurationOption td " +
                        "LEFT JOIN FETCH td.price p " +
                        "LEFT JOIN FETCH p.currency c"
        )
})
public class TimeDurationOption extends AbstractConstantEntity {
    @Column(name = "start_time", nullable = false)
    private LocalTime startTime;
    @Column(name = "end_time", nullable = false)
    private LocalTime endTime;
    @Column(name = "duration_in_min", nullable = false)
    private int duration;
    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private Price price;
}

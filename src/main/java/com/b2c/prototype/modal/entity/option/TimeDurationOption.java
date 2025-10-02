package com.b2c.prototype.modal.entity.option;

import com.b2c.prototype.modal.entity.price.Price;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.NamedQueries;
import jakarta.persistence.NamedQuery;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "time_duration_option")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@NamedQueries({
        @NamedQuery(
                name = "TimeDurationOption.findAllWithPriceAndCurrency",
                query = "SELECT td FROM TimeDurationOption td " +
                        "LEFT JOIN FETCH td.price p " +
                        "LEFT JOIN FETCH p.currency c " +
                        "WHERE td.startTime = :start AND td.endTime = :end"
        ),
        @NamedQuery(
                name = "TimeDurationOption.all",
                query = "SELECT td FROM TimeDurationOption td " +
                        "LEFT JOIN FETCH td.price p " +
                        "LEFT JOIN FETCH p.currency c"
        )
})
public class TimeDurationOption {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", updatable = false, nullable = false)
    private long id;
    private String label;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    @Column(name = "duration_in_min", nullable = false)
    private int durationInMin;
    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(nullable = false)
    private Price price;
}

package com.b2c.prototype.modal.entity.review;

import com.b2c.prototype.modal.base.constant.AbstractNumberConstantEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.NamedQueries;
import jakarta.persistence.NamedQuery;
import jakarta.persistence.Table;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Entity
@Table(name = "rating")
@SuperBuilder
@NoArgsConstructor
@NamedQueries({
        @NamedQuery(
                name = "Rating.findByValue",
                query = "SELECT r FROM Rating r WHERE r.value = :value"
        ),
        @NamedQuery(
                name = "Rating.all",
                query = "SELECT r FROM Rating r"
        )
})
public class Rating extends AbstractNumberConstantEntity {

}

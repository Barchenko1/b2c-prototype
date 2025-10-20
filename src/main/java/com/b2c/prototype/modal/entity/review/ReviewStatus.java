package com.b2c.prototype.modal.entity.review;

import com.b2c.prototype.modal.base.constant.AbstractConstantEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.NamedQueries;
import jakarta.persistence.NamedQuery;
import jakarta.persistence.Table;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Entity
@Table(name = "review_status")
@SuperBuilder
@NoArgsConstructor
@NamedQueries({
        @NamedQuery(
                name = "ReviewStatus.findByValue",
                query = "SELECT r FROM ReviewStatus r WHERE r.value = :value"
        ),
        @NamedQuery(
                name = "ReviewStatus.all",
                query = "SELECT r FROM ReviewStatus r"
        )
})
public class ReviewStatus extends AbstractConstantEntity {

}

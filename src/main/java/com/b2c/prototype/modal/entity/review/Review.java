package com.b2c.prototype.modal.entity.review;

import com.b2c.prototype.modal.entity.item.Item;
import com.b2c.prototype.modal.entity.item.Rating;
import com.b2c.prototype.modal.entity.user.UserProfile;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static com.b2c.prototype.util.UniqueIdUtil.getUUID;

@Entity
@Table(name = "review")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Review {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", updatable = false, nullable = false)
    private long id;
    @Column(name = "review_id", unique = true, nullable = false)
    private String reviewId;
    private String title;
    private String message;
    private long dateOfCreate;
    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
    private UserProfile userProfile;
    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
    private Rating rating;
    @OneToMany(fetch = FetchType.LAZY, cascade = {CascadeType.ALL})
    private List<Comment> comments;

    @PrePersist
    protected void onPrePersist() {
        if (this.reviewId == null) {
            this.reviewId = getUUID();
        }
    }
}

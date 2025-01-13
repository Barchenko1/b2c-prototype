package com.b2c.prototype.modal.entity.review;

import com.b2c.prototype.modal.entity.item.Rating;
import com.b2c.prototype.modal.entity.user.UserProfile;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

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
    @Column(name = "unique_id", unique = true, nullable = false)
    private String uniqueId;
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
        if (this.uniqueId == null) {
            this.uniqueId = getUUID();
        }
    }
}

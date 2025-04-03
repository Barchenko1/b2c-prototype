package com.b2c.prototype.modal.entity.review;

import com.b2c.prototype.modal.entity.user.UserDetails;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.NamedQueries;
import jakarta.persistence.NamedQuery;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "review")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@NamedQueries({
        @NamedQuery(
                name = "Review.findByReviewId",
                query = "SELECT r FROM Review r " +
                        "WHERE r.reviewId = :reviewId"
        ),
        @NamedQuery(
                name = "Review.findByUserId",
                query = "SELECT r FROM Review r " +
                        "LEFT JOIN FETCH r.status s " +
                        "LEFT JOIN FETCH r.rating ra " +
                        "LEFT JOIN FETCH r.userDetails u " +
                        "LEFT JOIN FETCH u.contactInfo ci " +
                        "LEFT JOIN FETCH r.comments c " +
                        "WHERE u.userId = :userId"
        )

})
public class Review {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", updatable = false, nullable = false)
    private long id;
    @Column(name = "reviewId", unique = true, nullable = false)
    private String reviewId;
    private String title;
    private String message;
    private long dateOfCreate;
    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
    private UserDetails userDetails;
    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
    @JoinColumn(name = "review_status_id")
    private ReviewStatus status;
    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
    @JoinColumn(name = "rating_id")
    private Rating rating;
    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "review_id")
    @Builder.Default
    @ToString.Exclude
    private List<ReviewComment> comments = new ArrayList<>();
}

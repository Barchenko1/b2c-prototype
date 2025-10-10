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
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "review_comment")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ReviewComment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", updatable = false, nullable = false)
    private long id;
    @Column(name = "review_comment_uniq_id", unique = true, nullable = false)
    private String reviewCommentUniqId;
    private String title;
    private String message;
    private LocalDateTime dateOfCreate;
    @ManyToOne(fetch = FetchType.LAZY)
    private UserDetails userDetails;
    @ManyToOne
    @JoinColumn(name = "comment_id")
    @ToString.Exclude
    private ReviewComment parent;
    @OneToMany(mappedBy = "parent", cascade = CascadeType.ALL)
    @Builder.Default
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private List<ReviewComment> childList = new ArrayList<>();

    public void addChildComment(ReviewComment comment) {
        this.childList.add(comment);
        comment.setParent(this);
    }

    public void removeChildComment(ReviewComment comment) {
        this.childList.remove(comment);
        comment.setParent(null);
    }
}

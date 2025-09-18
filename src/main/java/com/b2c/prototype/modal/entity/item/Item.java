package com.b2c.prototype.modal.entity.item;

import com.b2c.prototype.modal.entity.option.OptionItem;
import com.b2c.prototype.modal.entity.post.Post;
import com.b2c.prototype.modal.entity.review.Review;
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
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "item")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@NamedQueries({
        @NamedQuery(
                name = "Item.findItemByArticularUniqId",
                query = "SELECT i FROM Item i " +
                        "LEFT JOIN FETCH i.reviews r " +
                        "LEFT JOIN FETCH i.articularItems ai " +
                        "WHERE ai.articularUniqId = : articularUniqId"
        ),
        @NamedQuery(
                name = "Item.findItemWithReviewCommentsByArticularUniqId",
                query = "SELECT i FROM Item i " +
                        "LEFT JOIN FETCH i.reviews r " +
                        "LEFT JOIN FETCH r.comments c " +
                        "LEFT JOIN FETCH c.userDetails ud " +
                        "LEFT JOIN FETCH ud.contactInfo ci " +
                        "LEFT JOIN FETCH i.articularItems ai " +
                        "WHERE ai.articularUniqId = : articularUniqId AND c.parent IS NULL"
        ),
        @NamedQuery(
                name = "Item.findItemWithTopLevelPostsByArticularUniqId",
                query = "SELECT DISTINCT i FROM Item i " +
                        "LEFT JOIN FETCH i.posts p " +
                        "LEFT JOIN FETCH i.articularItems ai " +
                        "WHERE ai.articularUniqId = :articularUniqId AND p.parent IS NULL"
        )
})
public class Item {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", updatable = false, nullable = false)
    private long id;
    @Column(name = "item_uniq_id", unique = true, nullable = false)
    private String itemUniqId;
    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "meta_data_id")
    private MetaData metaData;
    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "item_id")
    @Builder.Default
    @EqualsAndHashCode.Exclude
    private List<ArticularItem> articularItems = new ArrayList<>();
    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "item_id")
    @Builder.Default
    @EqualsAndHashCode.Exclude
    private Set<Review> reviews = new HashSet<>();
    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "item_id")
    @Builder.Default
    @EqualsAndHashCode.Exclude
    private Set<Post> posts = new HashSet<>();

    public void addReview(Review review) {
        this.reviews.add(review);
    }

    public void removeReview(Review review) {
        this.reviews.remove(review);
    }

    public void addPost(Post post) {
        this.posts.add(post);
    }

    public void removeReview(Post post) {
        this.posts.remove(post);
    }

}

package com.b2c.prototype.modal.entity.item;

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
                name = "Item.findItemByArticularId",
                query = "SELECT i FROM Item i " +
                        "LEFT JOIN FETCH i.reviews r " +
                        "LEFT JOIN FETCH i.articularItem ai " +
                        "WHERE ai.articularId = : articularId"
        ),
        @NamedQuery(
                name = "Item.findItemWithReviewCommentsByArticularId",
                query = "SELECT i FROM Item i " +
                        "LEFT JOIN FETCH i.reviews r " +
                        "LEFT JOIN FETCH r.comments c " +
                        "LEFT JOIN FETCH c.userDetails ud " +
                        "LEFT JOIN FETCH ud.contactInfo ci " +
                        "LEFT JOIN FETCH i.articularItem ai " +
                        "WHERE ai.articularId = : articularId AND c.parent IS NULL"
        ),
        @NamedQuery(
                name = "Item.findItemWithTopLevelPostsByArticularId",
                query = "SELECT DISTINCT i FROM Item i " +
                        "LEFT JOIN FETCH i.posts p " +
                        "LEFT JOIN FETCH i.articularItem ai " +
                        "WHERE ai.articularId = :articularId AND p.parent IS NULL"
        )
})
public class Item {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", updatable = false, nullable = false)
    private long id;
    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "articular_item_id")
    private ArticularItem articularItem;
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

}

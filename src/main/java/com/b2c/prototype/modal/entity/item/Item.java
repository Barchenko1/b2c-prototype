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
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "item")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Item {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", updatable = false, nullable = false)
    private long id;
    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
    @JoinColumn(name = "articular_item_id")
    private ArticularItem articularItem;
    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.MERGE, orphanRemoval = true)
    @Builder.Default
    @EqualsAndHashCode.Exclude
    private List<Review> reviews = new ArrayList<>();
    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.MERGE, orphanRemoval = true)
    @Builder.Default
    @EqualsAndHashCode.Exclude
    private List<Post> posts = new ArrayList<>();

}

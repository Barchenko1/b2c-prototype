package com.b2c.prototype.modal.entity.item;

import com.b2c.prototype.modal.entity.option.OptionItem;
import com.b2c.prototype.modal.entity.post.Post;
import com.b2c.prototype.modal.entity.price.Price;
import com.b2c.prototype.modal.entity.review.Review;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.HashSet;
import java.util.Set;

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
    private String name;
    private String articularId;
    private long dateOfCreate;
    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
    @JoinColumn(name = "category_id")
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    private Category category;
    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
    private ItemType itemType;
    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
    private Brand brand;
    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
    private ItemStatus status;
    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
    private Price price;
    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
    private CurrencyDiscount currencyDiscount;
    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
    private PercentDiscount percentDiscount;
    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
    @JoinTable(
            name = "item_option",
            joinColumns = {@JoinColumn(name = "item_id")},
            inverseJoinColumns = {@JoinColumn(name = "option_item_id")}
    )
    @Builder.Default
    @EqualsAndHashCode.Exclude
    private Set<OptionItem> optionItems = new HashSet<>();
    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
    @JoinTable(
            name = "item_review",
            joinColumns = {@JoinColumn(name = "item_id")},
            inverseJoinColumns = {@JoinColumn(name = "review_id")}
    )
    @Builder.Default
    @EqualsAndHashCode.Exclude
    private Set<Review> reviews = new HashSet<>();
    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
    @JoinTable(
            name = "item_post",
            joinColumns = {@JoinColumn(name = "item_id")},
            inverseJoinColumns = {@JoinColumn(name = "post_id")}
    )
    @Builder.Default
    @EqualsAndHashCode.Exclude
    private Set<Post> posts = new HashSet<>();

    public void addOptionItem(OptionItem optionItem) {
        this.optionItems.add(optionItem);
        optionItem.getItems().add(this);
    }

    public void removeOptionItem(OptionItem optionItem) {
        this.optionItems.remove(optionItem);
        optionItem.getItems().remove(this);
    }

    public void addReview(Review review) {
        this.reviews.add(review);
        review.getItems().add(this);
    }

    public void removeReview(Review review) {
        this.reviews.remove(review);
        review.getItems().remove(this);
    }

    public void addPost(Post post) {
        this.posts.add(post);
        post.getItems().add(this);
    }

    public void removePost(Post post) {
        this.posts.remove(post);
        post.getItems().add(this);
    }

}

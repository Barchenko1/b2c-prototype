package com.b2c.prototype.modal.entity.review;

import com.b2c.prototype.modal.entity.item.Item;
import com.b2c.prototype.modal.entity.item.Rating;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

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
    private String title;
    private String message;
    private long dateOfCreate;
    @ManyToMany
    @JoinTable(
            name = "review_rating",
            joinColumns = { @JoinColumn(name = "review_id") },
            inverseJoinColumns = { @JoinColumn(name = "rating_id") }
    )
    private List<Rating> ratings;
    @ManyToMany(fetch = FetchType.LAZY, mappedBy = "reviews")
    private List<Item> items;
}

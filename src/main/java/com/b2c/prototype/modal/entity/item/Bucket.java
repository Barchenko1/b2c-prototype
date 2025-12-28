package com.b2c.prototype.modal.entity.item;

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
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.HashSet;
import java.util.Set;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "bucket")
public class Bucket {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", updatable = false, nullable = false)
    private long id;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_details_id", nullable = false)
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private UserDetails userDetails;
//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "articular_item_quantity_id", nullable = false)
//    @ToString.Exclude
//    @EqualsAndHashCode.Exclude
//    private ArticularItemQuantity articularItemQuantity;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "bucket_id")
    @Builder.Default
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private Set<ArticularItemQuantity> articularItemQuantities = new HashSet<>();

    public void addArticularItemQuantity(ArticularItemQuantity articularItemQuantity) {
        articularItemQuantities.add(articularItemQuantity);
    }

    public void removeArticularItemQuantity(ArticularItemQuantity articularItemQuantity) {
        articularItemQuantities.remove(articularItemQuantity);
    }
}

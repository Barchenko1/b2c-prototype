package com.b2c.prototype.modal.embedded.wishlist;

import com.b2c.prototype.modal.embedded.item.TempItemData;
import com.b2c.prototype.modal.embedded.user.TempUserProfile;
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

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "wishlist")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Wishlist {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", updatable = false, nullable = false)
    private long id;
    private long dateOfUpdate;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "temp_user_profile_id")
    private TempUserProfile tempUserProfile;
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "wishlist_item",
            joinColumns = {@JoinColumn(name = "wishlist_id")},
            inverseJoinColumns = {@JoinColumn(name = "temp_item_data_id")}
    )
    @Builder.Default
    @EqualsAndHashCode.Exclude
    private Set<TempItemData> tempItemDataSet = new HashSet<>();
}

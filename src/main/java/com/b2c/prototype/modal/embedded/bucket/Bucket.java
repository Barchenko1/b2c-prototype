package com.b2c.prototype.modal.embedded.bucket;

import com.b2c.prototype.modal.embedded.item.TempItemDataQuantity;
import com.b2c.prototype.modal.embedded.user.TempUserProfile;
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

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "bucket")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Bucket {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", updatable = false, nullable = false)
    private long id;
    private long dateOfUpdate;
    private String sessionId;
    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
    @JoinColumn(name = "temp_user_profile_id")
    private TempUserProfile tempUserProfile;
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "bucket_temp_item_data_quantity",
            joinColumns = {@JoinColumn(name = "bucket_id")},
            inverseJoinColumns = {@JoinColumn(name = "temp_item_data_quantity_id")}
    )
    @Builder.Default
    @EqualsAndHashCode.Exclude
    private Set<TempItemDataQuantity> tempItemDataQuantitySet = new HashSet<>();

    private void addTempItemQuantity(TempItemDataQuantity tempItemDataQuantity) {
        this.tempItemDataQuantitySet.add(tempItemDataQuantity);
        tempItemDataQuantity.getBucketSet().add(this);
    }

    private void removeTempItemQuantity(TempItemDataQuantity tempItemDataQuantity) {
        this.tempItemDataQuantitySet.remove(tempItemDataQuantity);
        tempItemDataQuantity.getBucketSet().remove(this);
    }
}

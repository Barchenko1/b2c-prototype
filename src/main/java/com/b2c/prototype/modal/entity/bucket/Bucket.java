package com.b2c.prototype.modal.entity.bucket;

import com.b2c.prototype.modal.entity.item.Item;
import com.b2c.prototype.modal.entity.item.ItemQuantity;
import com.b2c.prototype.modal.entity.user.AppUser;
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
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Table(name = "bucket")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Bucket {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", updatable = false, nullable = false)
    private long id;
    private long dateOfUpdate;
    private String sessionId;
    @ManyToOne(fetch = FetchType.LAZY)
    private AppUser user;
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "bucket_quantity_item",
            joinColumns = {@JoinColumn(name = "bucket_id")},
            inverseJoinColumns = {@JoinColumn(name = "quantity_id")}
    )
    private List<ItemQuantity> itemQuantityList;
}

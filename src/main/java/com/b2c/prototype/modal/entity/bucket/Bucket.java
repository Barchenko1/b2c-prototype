package com.b2c.prototype.modal.entity.bucket;

import com.b2c.prototype.modal.entity.item.Item;
import com.b2c.prototype.modal.entity.user.AppUser;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

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
    private long dateOfAdded;
    @ManyToOne(fetch = FetchType.LAZY)
    private AppUser user;
    @ManyToOne(fetch = FetchType.LAZY)
    private Item item;
}

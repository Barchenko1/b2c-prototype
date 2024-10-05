package com.b2c.prototype.modal.entity.wishlist;

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
    private long dateOfCreate;
    @ManyToOne(fetch = FetchType.LAZY)
    private AppUser user;
    @ManyToOne(fetch = FetchType.LAZY)
    private Item item;
}

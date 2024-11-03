package com.b2c.prototype.modal.entity.user;

import com.b2c.prototype.modal.entity.address.Address;
import com.b2c.prototype.modal.entity.payment.CreditCard;
import com.b2c.prototype.modal.entity.post.Post;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Table(name = "user_profile")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserProfile {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", updatable = false, nullable = false)
    private long id;
    private String username;
    private String email;
    private long dateOfCreate;
    private boolean isActive;
    @OneToOne(fetch = FetchType.LAZY, cascade = {CascadeType.MERGE})
    private ContactInfo contactInfo;
    @OneToOne(fetch = FetchType.LAZY, cascade = {CascadeType.MERGE})
    private Address address;
    @OneToMany(fetch = FetchType.LAZY, cascade = {CascadeType.MERGE})
    private List<CreditCard> creditCardList;
    @OneToMany(fetch = FetchType.LAZY, cascade = {CascadeType.MERGE})
    private List<Post> postList;
}

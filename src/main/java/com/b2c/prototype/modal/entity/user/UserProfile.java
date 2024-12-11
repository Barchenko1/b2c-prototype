package com.b2c.prototype.modal.entity.user;

import com.b2c.prototype.modal.base.AbstractUserProfile;
import com.b2c.prototype.modal.entity.address.Address;
import com.b2c.prototype.modal.entity.payment.CreditCard;
import com.b2c.prototype.modal.entity.post.Post;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "user_profile")
@Data
@SuperBuilder
@NoArgsConstructor
public class UserProfile extends AbstractUserProfile {
    @OneToOne(fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private ContactInfo contactInfo;
    @OneToOne(fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private Address address;
    @OneToMany(fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private List<CreditCard> creditCardList;
    @OneToMany(fetch = FetchType.LAZY, cascade = {CascadeType.MERGE})
    private List<Post> postList;
}

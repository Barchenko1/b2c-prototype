package com.b2c.prototype.modal.base.user;

import com.b2c.prototype.modal.entity.address.UserAddress;
import com.b2c.prototype.modal.entity.user.ContactInfo;
import com.b2c.prototype.modal.entity.user.Device;
import com.b2c.prototype.modal.entity.user.UserCreditCard;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.HashSet;
import java.util.Set;

@MappedSuperclass
@Data
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
public class AbstractUserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", updatable = false, nullable = false)
    private long id;
    @Column(name = "user_id", nullable = false, unique = true, updatable = false)
    private String userId;
    @Column(nullable = false, unique = true)
    private String username;
    @Column(nullable = false)
    private long dateOfCreate;
    private boolean isActive;
    private boolean isEmailVerified;
    private boolean isContactPhoneVerified;
    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "contactinfo_id")
    private ContactInfo contactInfo;
    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "userdetails_id")
    @Builder.Default
    private Set<Device> devices = new HashSet<>();
    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "userdetails_id")
    @Builder.Default
    private Set<UserAddress> userAddresses = new HashSet<>();
    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "userdetails_id")
    @Builder.Default
    private Set<UserCreditCard> userCreditCards = new HashSet<>();
}

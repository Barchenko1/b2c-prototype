package com.b2c.prototype.modal.entity.user;

import com.b2c.prototype.modal.entity.address.Address;
import com.b2c.prototype.modal.entity.payment.Card;
import com.b2c.prototype.modal.entity.post.Post;
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
@Table(name = "app_user")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AppUser {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", updatable = false, nullable = false)
    private long id;
    private String username;
    private String email;
    @OneToOne(fetch = FetchType.LAZY)
    private UserInfo userInfo;
    @OneToOne(fetch = FetchType.LAZY)
    private Address address;
    private long dateOfCreate;
    private boolean isActive;
    @OneToMany(fetch = FetchType.LAZY)
    private List<Card> cardList;
    @OneToMany(fetch = FetchType.LAZY)
    private List<Post> postList;
}

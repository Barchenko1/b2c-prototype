package com.b2c.prototype.modal.entity.message;

import com.b2c.prototype.modal.entity.user.UserProfile;
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
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "message_box")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MessageBox {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", updatable = false, nullable = false)
    private long id;
    @OneToOne(fetch = FetchType.LAZY)
    private UserProfile userProfile;
    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
    @JoinTable(
            name = "message_box_message",
            joinColumns = {@JoinColumn(name = "message_box_id")},
            inverseJoinColumns = {@JoinColumn(name = "message_id")}
    )
    @Builder.Default
    @EqualsAndHashCode.Exclude
    private Set<Message> messages = new HashSet<>();

    public void addMessage(Message message) {
        this.messages.add(message);
        message.getBoxes().add(this);
    }

    public void removeMessage(Message message) {
        this.messages.remove(message);
        message.getBoxes().remove(this);
    }
}

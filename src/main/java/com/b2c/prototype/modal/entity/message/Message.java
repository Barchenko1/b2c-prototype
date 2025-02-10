package com.b2c.prototype.modal.entity.message;

import jakarta.persistence.CascadeType;
import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static com.b2c.prototype.util.Util.getUUID;

@Entity
@Table(name = "message")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Message {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", updatable = false, nullable = false)
    private long id;
    private String sender;
    @Column(updatable = false, nullable = false, unique = true)
    private String messageUniqNumber;
    @ElementCollection
    @CollectionTable(name = "message_receivers", joinColumns = @JoinColumn(name = "message_id"))
    @Column(name = "receiver_email", nullable = false)
    private List<String> receivers;
    private long dateOfSend;
    private String title;
    private String message;
    private String sendSystem;
    private String subscribe;
    @ManyToMany(mappedBy = "messages", fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
    @Builder.Default
    @ToString.Exclude
    private Set<MessageBox> boxes = new HashSet<>();
    @ManyToOne(fetch = FetchType.LAZY)
    private MessageStatus status;
    @ManyToOne(fetch = FetchType.LAZY)
    private MessageType type;

    @PrePersist
    protected void onPrePersist() {
        if (this.messageUniqNumber == null) {
            this.messageUniqNumber = getUUID();
        }
    }
}

package com.b2c.prototype.modal.entity.message;

import jakarta.persistence.CascadeType;
import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.ForeignKey;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.NamedQueries;
import jakarta.persistence.NamedQuery;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "message")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@NamedQueries({
        @NamedQuery(
                name = "Message.findMessageBySender",
                query = "SELECT DISTINCT m FROM Message m " +
                        "LEFT JOIN FETCH m.messageTemplate mt " +
                        "LEFT JOIN FETCH m.status s "
        ),
        @NamedQuery(
                name = "Message.findMessageByReceiver",
                query = "SELECT DISTINCT m FROM Message m " +
                        "LEFT JOIN FETCH m.messageTemplate mt " +
                        "LEFT JOIN FETCH m.status s "
        )
})
public class Message {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", updatable = false, nullable = false)
    private long id;
    private String sender;
    @ElementCollection
    @CollectionTable(name = "message_receivers", joinColumns = @JoinColumn(name = "message_id"))
    @Column(name = "receiver_email", nullable = false)
    @Fetch(FetchMode.SUBSELECT)
    private Set<String> receivers;
    private LocalDateTime dateOfSend;
    @ManyToMany(mappedBy = "messages")
    @Builder.Default
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private Set<MessageBox> boxes = new HashSet<>();
    @ManyToOne(fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private MessageTemplate messageTemplate;
    @ManyToOne(fetch = FetchType.LAZY)
    private MessageStatus status;
    @ManyToOne(fetch = FetchType.LAZY)
    private MessageType type;

    public void addMessageBox(MessageBox messageBox) {
        this.boxes.add(messageBox);
        messageBox.getMessages().add(this);
    }

    public void removeMessageBox(MessageBox messageBox) {
        this.boxes.remove(messageBox);
        messageBox.getMessages().remove(this);
    }
}

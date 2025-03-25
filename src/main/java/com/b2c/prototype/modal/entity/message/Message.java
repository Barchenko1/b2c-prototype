package com.b2c.prototype.modal.entity.message;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
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
                        "LEFT JOIN FETCH mt.type t " +
                        "LEFT JOIN FETCH m.status s " +
                        "WHERE mt.sender = : sender"
        ),
        @NamedQuery(
                name = "Message.findMessageByReceiver",
                query = "SELECT DISTINCT m FROM Message m " +
                        "LEFT JOIN FETCH m.messageTemplate mt " +
                        "LEFT JOIN FETCH mt.type t " +
                        "LEFT JOIN FETCH m.status s " +
                        "WHERE :receiver MEMBER OF mt.receivers"
        )
})
public class Message {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", updatable = false, nullable = false)
    private long id;
    @ManyToOne(fetch = FetchType.LAZY, cascade = {CascadeType.ALL})
    @JoinColumn(name = "message_template_id", foreignKey = @ForeignKey(name = "fk_message_template"))
    private MessageTemplate messageTemplate;
    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
    @JoinTable(
            name = "message_box_message",
            joinColumns = @JoinColumn(name = "message_id"),
            inverseJoinColumns = @JoinColumn(name = "message_box_id")
    )
    @Builder.Default
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private Set<MessageBox> boxes = new HashSet<>();
//    @ManyToMany(mappedBy = "messages")
//    @Builder.Default
//    @ToString.Exclude
//    @EqualsAndHashCode.Exclude
//    private Set<MessageBox> boxes = new HashSet<>();
    @ManyToOne(fetch = FetchType.LAZY)
    private MessageStatus status;

    public void addMessageBox(MessageBox messageBox) {
        this.boxes.add(messageBox);
        messageBox.getMessages().add(this);
    }

    public void removeMessageBox(MessageBox messageBox) {
        this.boxes.remove(messageBox);
        messageBox.getMessages().remove(this);
    }
}

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
import jakarta.persistence.ManyToOne;
import jakarta.persistence.NamedQueries;
import jakarta.persistence.NamedQuery;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "message_template")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@NamedQueries({
        @NamedQuery(
                name = "MessageTemplate.findByMessageId",
                query = "SELECT DISTINCT mt FROM MessageTemplate mt " +
                        "WHERE mt.messageUniqNumber = : messageUniqNumber"
        ),
})
public class MessageTemplate {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", updatable = false, nullable = false)
    private long id;
    private String sender;
    @Column(updatable = false, nullable = false, unique = true)
    private String messageUniqNumber;
    @OneToMany(mappedBy = "messageTemplate", cascade = CascadeType.ALL, orphanRemoval = true)
    @Builder.Default
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private Set<Message> messages = new HashSet<>();
    @ElementCollection
    @CollectionTable(name = "message_template_receivers", joinColumns = @JoinColumn(name = "message_template_id"))
    @Column(name = "receiver_email", nullable = false)
    private List<String> receivers;
    private long dateOfSend;
    private String title;
    private String message;
    private String sendSystem;
    @ManyToOne(fetch = FetchType.LAZY)
    private MessageType type;
}

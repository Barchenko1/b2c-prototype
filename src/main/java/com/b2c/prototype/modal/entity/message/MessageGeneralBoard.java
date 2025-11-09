package com.b2c.prototype.modal.entity.message;

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
import jakarta.persistence.NamedQueries;
import jakarta.persistence.NamedQuery;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
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
@Table(name = "message_general_board")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@NamedQueries({
        @NamedQuery(
                name = "MessageGeneralBoard.findByUserIdWithMessages",
                query = "SELECT DISTINCT mb FROM MessageBox mb " +
                        "JOIN FETCH mb.userDetails ud " +
                        "LEFT JOIN FETCH mb.messages " +
                        "WHERE ud.userId = :userId"
        ),
        @NamedQuery(
                name = "MessageGeneralBoard.findByEmailListWithMessages",
                query = "SELECT DISTINCT mb FROM MessageBox mb " +
                        "JOIN FETCH mb.userDetails ud " +
                        "JOIN FETCH ud.contactInfo ci " +
                        "LEFT JOIN FETCH mb.messages " +
                        "WHERE ci.email IN :emails"
        ),
        @NamedQuery(
                name = "MessageGeneralBoard.findByUserId",
                query = "SELECT DISTINCT mb FROM MessageBox mb " +
                        "JOIN FETCH mb.userDetails ud " +
                        "LEFT JOIN FETCH mb.messages m " +
                        "LEFT JOIN FETCH m.messageTemplate mt " +
                        "LEFT JOIN FETCH m.status " +
                        "WHERE ud.userId = :userId"
        ),

})
public class MessageGeneralBoard {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", updatable = false, nullable = false)
    private long id;
    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "message_id")
    @Builder.Default
    @EqualsAndHashCode.Exclude
    private Set<Message> messages = new HashSet<>();
    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "mail_message_id")
    @Builder.Default
    @EqualsAndHashCode.Exclude
    private Set<MailMessage> mailMessages = new HashSet<>();
}

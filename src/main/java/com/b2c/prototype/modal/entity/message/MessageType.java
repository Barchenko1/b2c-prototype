package com.b2c.prototype.modal.entity.message;

import com.b2c.prototype.modal.base.constant.AbstractConstantEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.NamedQueries;
import jakarta.persistence.NamedQuery;
import jakarta.persistence.Table;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Entity
@Table(name = "message_type")
@SuperBuilder
@NoArgsConstructor
@NamedQueries({
        @NamedQuery(
                name = "MessageType.findByKey",
                query = "SELECT m FROM MessageType m WHERE m.key = :key"
        ),
        @NamedQuery(
                name = "MessageType.all",
                query = "SELECT m FROM MessageType m"
        )
})
public class MessageType extends AbstractConstantEntity {
}

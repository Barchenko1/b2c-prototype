package com.b2c.prototype.modal.entity.message;

import com.b2c.prototype.modal.base.constant.AbstractConstantEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.NamedQueries;
import jakarta.persistence.NamedQuery;
import jakarta.persistence.Table;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Entity
@Table(name = "message_status")
@SuperBuilder
@NoArgsConstructor
@NamedQueries({
        @NamedQuery(
                name = "MessageStatus.findByKey",
                query = "SELECT m FROM MessageStatus m WHERE m.key = :key"
        ),
        @NamedQuery(
                name = "MessageStatus.all",
                query = "SELECT m FROM MessageStatus m"
        )
})
public class MessageStatus extends AbstractConstantEntity {

}

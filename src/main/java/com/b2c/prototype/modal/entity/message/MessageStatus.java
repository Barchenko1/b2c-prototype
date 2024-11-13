package com.b2c.prototype.modal.entity.message;

import com.b2c.prototype.modal.base.AbstractOneColumnEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Entity
@Table(name = "message_status")
@SuperBuilder
@NoArgsConstructor
public class MessageStatus extends AbstractOneColumnEntity {

}

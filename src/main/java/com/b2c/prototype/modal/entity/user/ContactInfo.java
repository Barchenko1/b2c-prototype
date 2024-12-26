package com.b2c.prototype.modal.entity.user;

import com.b2c.prototype.modal.base.AbstractContactInfo;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "contact_info")
@Data
@SuperBuilder
@NoArgsConstructor
public class ContactInfo extends AbstractContactInfo {
    private int orderNumber;
}

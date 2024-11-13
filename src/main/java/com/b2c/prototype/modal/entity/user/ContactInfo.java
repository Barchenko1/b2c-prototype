package com.b2c.prototype.modal.entity.user;

import com.b2c.prototype.modal.base.AbstractContactInfo;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Entity
@Table(name = "contact_info")
@SuperBuilder
@NoArgsConstructor
public class ContactInfo extends AbstractContactInfo {
}

package com.b2c.prototype.modal.entity.user;

import com.b2c.prototype.modal.base.user.AbstractContactInfo;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.Date;

@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "contact_info")
@Data
@SuperBuilder
@NoArgsConstructor
public class ContactInfo extends AbstractContactInfo {
    @Column(name = "birthday_date")
    private Date birthdayDate;
}

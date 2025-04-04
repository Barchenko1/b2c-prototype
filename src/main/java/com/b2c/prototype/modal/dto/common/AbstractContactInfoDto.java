package com.b2c.prototype.modal.dto.common;

import com.b2c.prototype.modal.dto.payload.order.ContactPhoneDto;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.persistence.MappedSuperclass;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@MappedSuperclass
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties
@JsonInclude(JsonInclude.Include.NON_NULL)
public abstract class AbstractContactInfoDto {
    private String firstName;
    private String lastName;
    private ContactPhoneDto contactPhone;
    private String email;
}

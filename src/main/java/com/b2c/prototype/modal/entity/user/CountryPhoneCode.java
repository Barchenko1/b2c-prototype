package com.b2c.prototype.modal.entity.user;

import com.b2c.prototype.modal.base.AbstractConstantEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.NamedQueries;
import jakarta.persistence.NamedQuery;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "country_phone_code")
@Data
@NoArgsConstructor
@SuperBuilder
@NamedQueries({
        @NamedQuery(
                name = "CountryPhoneCode.findByValue",
                query = "SELECT c FROM CountryPhoneCode c WHERE c.value = : value"
        )
})
public class CountryPhoneCode extends AbstractConstantEntity {

}

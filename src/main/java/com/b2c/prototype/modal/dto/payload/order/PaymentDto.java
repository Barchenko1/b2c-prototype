package com.b2c.prototype.modal.dto.payload.order;

import com.b2c.prototype.modal.dto.common.AbstractPaymentDto;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@EqualsAndHashCode(callSuper = true)
@Data
@SuperBuilder
@NoArgsConstructor
public class PaymentDto extends AbstractPaymentDto {
}

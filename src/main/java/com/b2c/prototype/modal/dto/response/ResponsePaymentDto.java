package com.b2c.prototype.modal.dto.response;

import com.b2c.prototype.modal.dto.common.AbstractPaymentDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@EqualsAndHashCode(callSuper = true)
@Data
@SuperBuilder
@NoArgsConstructor
public class ResponsePaymentDto extends AbstractPaymentDto {
    private String orderId;
}

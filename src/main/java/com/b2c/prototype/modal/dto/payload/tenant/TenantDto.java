package com.b2c.prototype.modal.dto.payload.tenant;

import com.b2c.prototype.modal.dto.payload.constant.CurrencyDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TenantDto {
    private String code;
    private String value;
    private String language;
    private String defaultLocale;
    private CurrencyDto primaryCurrency;
    private String timezone;

}

package com.b2c.prototype.modal.dto.response;

import com.b2c.prototype.modal.dto.payload.AddressDto;
import com.b2c.prototype.modal.dto.payload.ContactInfoDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ResponseUserDetailsDto {
    private ContactInfoDto contactInfo;
    private List<AddressDto> addresses;
    private List<ResponseUserCreditCardDto> creditCards;
}

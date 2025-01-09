package com.b2c.prototype.modal.dto.payload;

import com.b2c.prototype.modal.dto.response.ResponseCreditCardDto;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class UserProfileDto {
    private String email;
    private ContactInfoDto contactInfo;
    private AddressDto addressDto;
    private List<ResponseCreditCardDto> creditCards;
}

package com.b2c.prototype.modal.dto.payload;

import com.b2c.prototype.modal.dto.response.ResponseCreditCardDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserProfileDto {
    private String email;
    private ContactInfoDto contactInfo;
    private AddressDto addressDto;
    private List<ResponseCreditCardDto> creditCards;
}

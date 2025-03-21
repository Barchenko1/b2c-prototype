package com.b2c.prototype.modal.dto.response;

import com.b2c.prototype.modal.dto.payload.ContactInfoDto;
import com.b2c.prototype.modal.dto.payload.user.UserAddressDto;
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
    private List<UserAddressDto> addresses;
    private List<ResponseUserCreditCardDto> creditCards;
    private List<ResponseDeviceDto> devices;
}

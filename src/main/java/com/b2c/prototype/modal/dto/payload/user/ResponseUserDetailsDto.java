package com.b2c.prototype.modal.dto.payload.user;

import com.b2c.prototype.modal.dto.payload.order.ContactInfoDto;
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
    private String username;
    private long dateOfCreate;
    private boolean isEmailVerified;
    private boolean isContactPhoneVerified;
    private ContactInfoDto contactInfo;
    private List<UserAddressDto> addresses;
    private List<ResponseUserCreditCardDto> creditCards;
    private List<ResponseDeviceDto> devices;
}

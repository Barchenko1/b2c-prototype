package com.b2c.prototype.modal.dto.request;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class UserDetailsDto {
    private String email;
    private String name;
    private String secondName;
    private ContactPhoneDto contactPhone;
    private AddressDto addressDto;
    private List<CreditCardDto> creditCardDtoList;
    private List<PostDto> postDtoList;

}

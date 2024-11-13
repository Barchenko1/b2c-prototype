package com.b2c.prototype.modal.dto.response;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class ResponseUserDetailsDto {
    private String username;
    private String email;
    private String name;
    private String secondName;
    private ResponseContactPhoneDto contactPhone;
    private ResponseAddressDto addressDto;
    private List<ResponseCardDto> creditCardDtoList;
    private List<ResponsePostDto> postDtoList;

}

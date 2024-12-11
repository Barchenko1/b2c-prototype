package com.b2c.prototype.modal.dto.request;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class WishListDto {
    private String userId;
    private String articularId;
}

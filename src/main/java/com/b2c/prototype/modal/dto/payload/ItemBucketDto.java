package com.b2c.prototype.modal.dto.payload;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ItemBucketDto {
    private String userId;
    private String articularId;
    private int count;
}

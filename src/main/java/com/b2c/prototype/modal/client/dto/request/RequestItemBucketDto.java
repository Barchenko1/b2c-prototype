package com.b2c.prototype.modal.client.dto.request;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class RequestItemBucketDto {
    private String itemName;
    private int count;
}

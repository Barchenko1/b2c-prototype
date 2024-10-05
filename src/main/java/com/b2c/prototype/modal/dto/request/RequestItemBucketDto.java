package com.b2c.prototype.modal.dto.request;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class RequestItemBucketDto {
    private String userId;
    private String articularId;
    private int count;
}

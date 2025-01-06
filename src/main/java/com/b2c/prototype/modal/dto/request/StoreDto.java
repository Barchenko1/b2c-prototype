package com.b2c.prototype.modal.dto.request;

import com.b2c.prototype.modal.constant.CountTypeEnum;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class StoreDto {
    private String articularId;
    private int count;
    private CountTypeEnum countType;
}

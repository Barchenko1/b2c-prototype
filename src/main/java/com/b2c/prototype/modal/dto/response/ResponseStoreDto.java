package com.b2c.prototype.modal.dto.response;

import com.b2c.prototype.modal.dto.payload.ItemDataDto;
import com.b2c.prototype.modal.dto.payload.ItemDataOptionDto;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ResponseStoreDto {
    private ItemDataOptionDto itemDataOption;
    private ItemDataDto itemData;
}

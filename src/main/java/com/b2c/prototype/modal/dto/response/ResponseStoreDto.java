package com.b2c.prototype.modal.dto.response;

import com.b2c.prototype.modal.dto.request.ItemDataDto;
import com.b2c.prototype.modal.dto.request.ItemDataOptionDto;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ResponseStoreDto {
    private ItemDataOptionDto itemDataOption;
    private ItemDataDto itemData;
}

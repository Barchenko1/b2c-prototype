package com.b2c.prototype.modal.dto.payload.general;

import com.b2c.prototype.modal.dto.payload.item.ArticularGroupDto;
import com.b2c.prototype.modal.dto.payload.store.ArticularStockDto;
import com.b2c.prototype.modal.dto.payload.store.StoreInfoDto;

import java.util.List;


public class StoreArticularGroupDto {
    private List<StoreInfoDto> store;
    private ArticularStockDto articularStockDto;
    private List<ArticularGroupDto> articularGroupList;
}

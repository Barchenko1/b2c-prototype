package com.b2c.prototype.modal.dto.payload.store;

import com.b2c.prototype.modal.dto.payload.order.AddressDto;

public class StoreInfoDto {
    private String storeId;
    private String storeName;
    private boolean isActive;
    private String region;
    private AddressDto address;
}

package com.b2c.prototype.service.client.item;

import com.b2c.prototype.modal.client.dto.common.RequestOneFieldEntityDto;
import com.b2c.prototype.modal.client.dto.common.RequestOneFieldEntityDtoUpdate;

public interface IBrandService {
    void saveBrand(RequestOneFieldEntityDto requestOneFieldEntityDto);
    void updateBrand(RequestOneFieldEntityDtoUpdate requestOneFieldEntityDtoUpdate);
    void deleteBrand(RequestOneFieldEntityDto requestOneFieldEntityDto);
}

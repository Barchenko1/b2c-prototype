package com.b2c.prototype.service.item;

import com.b2c.prototype.modal.dto.common.RequestOneFieldEntityDto;
import com.b2c.prototype.modal.dto.common.RequestOneFieldEntityDtoUpdate;

public interface IBrandService {
    void saveBrand(RequestOneFieldEntityDto requestOneFieldEntityDto);
    void updateBrand(RequestOneFieldEntityDtoUpdate requestOneFieldEntityDtoUpdate);
    void deleteBrand(RequestOneFieldEntityDto requestOneFieldEntityDto);
}

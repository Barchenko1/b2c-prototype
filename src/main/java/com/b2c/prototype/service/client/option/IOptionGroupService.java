package com.b2c.prototype.service.client.option;

import com.b2c.prototype.modal.client.dto.common.RequestOneFieldEntityDto;
import com.b2c.prototype.modal.client.dto.common.RequestOneFieldEntityDtoUpdate;

public interface IOptionGroupService {
    void saveOptionGroup(RequestOneFieldEntityDto requestOneFieldEntityDto);
    void updateOptionGroup(RequestOneFieldEntityDtoUpdate requestOneFieldEntityDtoUpdate);
    void deleteOptionGroup(RequestOneFieldEntityDto requestOneFieldEntityDto);
}

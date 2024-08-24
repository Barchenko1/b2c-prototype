package com.b2c.prototype.service.option;

import com.b2c.prototype.modal.dto.common.RequestOneFieldEntityDto;
import com.b2c.prototype.modal.dto.common.RequestOneFieldEntityDtoUpdate;

public interface IOptionGroupService {
    void saveOptionGroup(RequestOneFieldEntityDto requestOneFieldEntityDto);
    void updateOptionGroup(RequestOneFieldEntityDtoUpdate requestOneFieldEntityDtoUpdate);
    void deleteOptionGroup(RequestOneFieldEntityDto requestOneFieldEntityDto);
}

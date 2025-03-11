package com.b2c.prototype.manager.userdetails;

import com.b2c.prototype.modal.dto.payload.DeviceDto;
import com.b2c.prototype.modal.dto.response.ResponseDeviceDto;

import java.util.List;

public interface IDeviceManager {
    void putDevice(String userId, DeviceDto deviceDto);
    void deleteDevice(String userId, DeviceDto deviceDto);

    List<ResponseDeviceDto> getDevicesByUserId(String userId);
}

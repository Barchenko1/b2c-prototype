package com.b2c.prototype.processor.user;

import com.b2c.prototype.modal.dto.payload.DeviceDto;
import com.b2c.prototype.modal.dto.response.ResponseDeviceDto;

import java.util.List;
import java.util.Map;

public interface IDeviceProcess {
    void putDevice(Map<String, String> requestParams, DeviceDto deviceDto);
    void deleteDevice(Map<String, String> requestParams, DeviceDto deviceDto);

    List<ResponseDeviceDto> getDevicesByUserId(Map<String, String> requestParams);
}

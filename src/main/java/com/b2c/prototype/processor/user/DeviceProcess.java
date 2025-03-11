package com.b2c.prototype.processor.user;

import com.b2c.prototype.manager.userdetails.IDeviceManager;
import com.b2c.prototype.modal.dto.payload.DeviceDto;
import com.b2c.prototype.modal.dto.response.ResponseDeviceDto;

import java.util.List;
import java.util.Map;

public class DeviceProcess implements IDeviceProcess {
    private final IDeviceManager deviceManager;

    public DeviceProcess(IDeviceManager deviceManager) {
        this.deviceManager = deviceManager;
    }

    @Override
    public void putDevice(Map<String, String> requestParams, DeviceDto deviceDto) {
        String userId = requestParams.get("userId");
        deviceManager.putDevice(userId, deviceDto);
    }

    @Override
    public void deleteDevice(Map<String, String> requestParams, DeviceDto deviceDto) {
        String userId = requestParams.get("userId");
        deviceManager.deleteDevice(userId, deviceDto);
    }

    @Override
    public List<ResponseDeviceDto> getDevicesByUserId(Map<String, String> requestParams) {
        String userId = requestParams.get("userId");
        return deviceManager.getDevicesByUserId(userId);
    }
}

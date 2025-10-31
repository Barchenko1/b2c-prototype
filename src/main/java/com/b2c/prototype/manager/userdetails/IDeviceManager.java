package com.b2c.prototype.manager.userdetails;

import com.b2c.prototype.modal.dto.payload.user.DeviceDto;

import java.util.List;

public interface IDeviceManager {
    void activateCurrentDevice(String userId, String clientIp, DeviceDto deviceDto);
    void deactivateCurrentDevice(String userId, String deviceName);
    void deleteDevice(String userId, DeviceDto deviceDto);

    List<DeviceDto> getDevicesByUserId(String userId, String clientIp);
}

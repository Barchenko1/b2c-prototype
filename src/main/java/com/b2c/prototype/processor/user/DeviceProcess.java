package com.b2c.prototype.processor.user;

import com.b2c.prototype.manager.userdetails.IDeviceManager;
import com.b2c.prototype.modal.dto.payload.user.DeviceDto;
import com.b2c.prototype.modal.dto.payload.user.ResponseDeviceDto;
import jakarta.servlet.http.HttpServletRequest;

import java.util.List;
import java.util.Map;

public class DeviceProcess implements IDeviceProcess {
    private final IDeviceManager deviceManager;

    public DeviceProcess(IDeviceManager deviceManager) {
        this.deviceManager = deviceManager;
    }

    @Override
    public void activateCurrentDevice(Map<String, String> requestParams, HttpServletRequest request, DeviceDto deviceDto) {
        String userId = requestParams.get("userId");
        String clientId = getClientIp(request);
        deviceManager.activateCurrentDevice(userId, clientId, deviceDto);
    }

    @Override
    public void deleteDevice(Map<String, String> requestParams, DeviceDto deviceDto) {
        String userId = requestParams.get("userId");
        deviceManager.deleteDevice(userId, deviceDto);
    }

    @Override
    public List<ResponseDeviceDto> getDevicesByUserId(Map<String, String> requestParams, HttpServletRequest request) {
        String userId = requestParams.get("userId");
        String clientId = getClientIp(request);
        return deviceManager.getDevicesByUserId(userId, clientId);
    }

    private String getClientIp(HttpServletRequest request) {
        String ip = request.getHeader("X-Forwarded-For");
        if (ip == null || ip.isEmpty() || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }
        return ip;
    }
}

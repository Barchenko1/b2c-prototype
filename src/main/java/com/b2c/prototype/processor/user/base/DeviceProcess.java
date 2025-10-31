package com.b2c.prototype.processor.user.base;

import com.b2c.prototype.manager.userdetails.IDeviceManager;
import com.b2c.prototype.modal.dto.payload.user.DeviceDto;
import com.b2c.prototype.processor.user.IDeviceProcess;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ServerWebExchange;

import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class DeviceProcess implements IDeviceProcess {

    private final IDeviceManager deviceManager;

    public DeviceProcess(IDeviceManager deviceManager) {
        this.deviceManager = deviceManager;
    }

    @Override
    public void activateCurrentDevice(Map<String, String> requestParams,
                                      ServerWebExchange exchange,
                                      DeviceDto deviceDto) {
        String userId = requestParams.get("userId");
        String clientIp = getClientIp(exchange);
        deviceManager.activateCurrentDevice(userId, clientIp, deviceDto);
    }

    @Override
    public void deleteDevice(Map<String, String> requestParams, DeviceDto deviceDto) {
        String userId = requestParams.get("userId");
        deviceManager.deleteDevice(userId, deviceDto);
    }

    @Override
    public List<DeviceDto> getDevicesByUserId(Map<String, String> requestParams,
                                                      ServerWebExchange exchange) {
        String userId = requestParams.get("userId");
        String clientIp = getClientIp(exchange);
        return deviceManager.getDevicesByUserId(userId, clientIp);
    }

    private String getClientIp(ServerWebExchange exchange) {
        // 1) X-Forwarded-For (take first IP)
        String xff = exchange.getRequest().getHeaders().getFirst("X-Forwarded-For");
        if (xff != null && !xff.isBlank() && !"unknown".equalsIgnoreCase(xff)) {
            int comma = xff.indexOf(',');
            return (comma > 0 ? xff.substring(0, comma) : xff).trim();
        }
        // 2) Remote address
        return Optional.ofNullable(exchange.getRequest().getRemoteAddress())
                .map(InetSocketAddress::getAddress)
                .map(InetAddress::getHostAddress)
                .orElse("unknown");
    }
}

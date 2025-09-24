package com.b2c.prototype.processor.user;

import com.b2c.prototype.modal.dto.payload.user.DeviceDto;
import com.b2c.prototype.modal.dto.payload.user.ResponseDeviceDto;
//import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.server.ServerWebExchange;

import java.util.List;
import java.util.Map;

public interface IDeviceProcess {
    void activateCurrentDevice(Map<String, String> requestParams, ServerWebExchange request, DeviceDto deviceDto);
    void deleteDevice(Map<String, String> requestParams, DeviceDto deviceDto);

    List<ResponseDeviceDto> getDevicesByUserId(Map<String, String> requestParams, ServerWebExchange request);
}

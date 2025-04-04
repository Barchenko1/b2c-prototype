package com.b2c.prototype.controller.user;

import com.b2c.prototype.modal.dto.payload.user.DeviceDto;
import com.b2c.prototype.modal.dto.payload.user.ResponseDeviceDto;
import com.b2c.prototype.processor.user.IDeviceProcess;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/device")
public class DeviceController {
    private final IDeviceProcess deviceProcess;

    public DeviceController(IDeviceProcess deviceProcess) {
        this.deviceProcess = deviceProcess;
    }

    @PostMapping(produces = MediaType.TEXT_PLAIN_VALUE)
    public ResponseEntity<Void> activateCurrentDevice(HttpServletRequest request,
                                                      @RequestParam final Map<String, String> requestParams,
                                                      @RequestBody final DeviceDto deviceDto) {
        deviceProcess.activateCurrentDevice(requestParams, request, deviceDto);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping(produces = MediaType.TEXT_PLAIN_VALUE)
    public ResponseEntity<Void> deleteDevice(@RequestParam final Map<String, String> requestParams,
                                             @RequestBody final DeviceDto deviceDto) {
        deviceProcess.deleteDevice(requestParams, deviceDto);
        return ResponseEntity.ok().build();
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public List<ResponseDeviceDto> getDevicesByUserId(HttpServletRequest request,
                                                      @RequestParam final Map<String, String> requestParams) {
        return deviceProcess.getDevicesByUserId(requestParams, request);
    }
}

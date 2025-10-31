package com.b2c.prototype.manager.userdetails.basic;

import com.b2c.prototype.dao.IGeneralEntityDao;
import com.b2c.prototype.manager.userdetails.IDeviceManager;
import com.b2c.prototype.modal.dto.payload.user.DeviceDto;
import com.b2c.prototype.modal.entity.user.Device;
import com.b2c.prototype.modal.entity.user.UserDetails;
import com.b2c.prototype.transform.userdetails.IUserDetailsTransformService;
import com.nimbusds.jose.util.Pair;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static com.b2c.prototype.util.Constant.USER_ID;

@Service
public class DeviceManager implements IDeviceManager {

    private final IGeneralEntityDao generalEntityDao;
    private final IUserDetailsTransformService userDetailsTransformService;

    public DeviceManager(IGeneralEntityDao generalEntityDao,
                         IUserDetailsTransformService userDetailsTransformService) {
        this.generalEntityDao = generalEntityDao;
        this.userDetailsTransformService = userDetailsTransformService;
    }

    @Override
    public void activateCurrentDevice(String userId, String clientIp, DeviceDto deviceDto) {
        UserDetails userDetails = generalEntityDao.findEntity(
                "UserDetails.findAllDevicesByUserId",
                Pair.of(USER_ID, userId));
        Device newDevice = userDetailsTransformService.mapDeviceDtoToDevice(deviceDto);

        Optional<Device> optionalDevice = userDetails.getDevices().stream()
                .filter(device -> device.getPlatform().equals(newDevice.getPlatform())
                        && device.getScreenHeight() == newDevice.getScreenHeight()
                        && device.getScreenWidth() == newDevice.getScreenWidth()
                        && device.getIpAddress().equals(clientIp)
                )
                .findFirst();

        if (optionalDevice.isPresent()) {
            optionalDevice.get().setLoginTime(LocalDateTime.now());
        } else {
            newDevice.setIpAddress(clientIp);
            userDetails.getDevices().add(newDevice);
        }
        generalEntityDao.mergeEntity(userDetails);
    }

    @Override
    public void deactivateCurrentDevice(String userId, String deviceName) {

    }

    @Override
    public void deleteDevice(String userId, DeviceDto deviceDto) {
        UserDetails userDetails = generalEntityDao.findEntity(
                "UserDetails.findAllDevicesByUserId",
                Pair.of(USER_ID, userId));

        userDetails.getDevices().stream()
                .filter(device -> device.getPlatform().equals(deviceDto.getPlatform())
                        && device.getScreenHeight() == deviceDto.getScreenHeight()
                        && device.getScreenWidth() == deviceDto.getScreenWidth()
                        && device.getUserAgent().equals(deviceDto.getUserAgent())
                )
                .findFirst()
                .ifPresent(device -> {
                    userDetails.getDevices().remove(device);
                    generalEntityDao.mergeEntity(userDetails);
                });
    }

    @Override
    public List<DeviceDto> getDevicesByUserId(String userId, String clientId) {
        UserDetails userDetails = generalEntityDao.findEntity(
                "UserDetails.findAllDevicesByUserId",
                Pair.of(USER_ID, userId));

        return userDetails.getDevices().stream()
                .map(device -> {
                    DeviceDto responseDeviceDto =
                            userDetailsTransformService.mapDeviceToDeviceDto(device);
                    responseDeviceDto.setThisDevice(device.getIpAddress().equals(clientId));
                    return responseDeviceDto;
                })
                .toList();
    }
}

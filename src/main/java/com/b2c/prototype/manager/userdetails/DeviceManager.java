package com.b2c.prototype.manager.userdetails;

import com.b2c.prototype.dao.user.IDeviceDao;
import com.b2c.prototype.modal.dto.payload.user.DeviceDto;
import com.b2c.prototype.modal.dto.payload.user.ResponseDeviceDto;
import com.b2c.prototype.modal.entity.user.Device;
import com.b2c.prototype.modal.entity.user.UserDetails;
import com.b2c.prototype.service.function.ITransformationFunctionService;
import com.b2c.prototype.service.query.ISearchService;
import com.tm.core.finder.factory.IParameterFactory;
import com.tm.core.process.manager.common.EntityOperationManager;
import com.tm.core.process.manager.common.IEntityOperationManager;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static com.b2c.prototype.util.Constant.USER_ID;

public class DeviceManager implements IDeviceManager {

    private final IEntityOperationManager entityOperationManager;
    private final ITransformationFunctionService transformationFunctionService;
    private final ISearchService searchService;
    private final IParameterFactory parameterFactory;

    public DeviceManager(IDeviceDao deviceDao, ITransformationFunctionService transformationFunctionService,ISearchService searchService, IParameterFactory parameterFactory) {
        this.entityOperationManager = new EntityOperationManager(deviceDao);
        this.transformationFunctionService = transformationFunctionService;
        this.searchService = searchService;
        this.parameterFactory = parameterFactory;
    }

    @Override
    public void activateCurrentDevice(String userId, String clientIp, DeviceDto deviceDto) {
        entityOperationManager.executeConsumer(session -> {
            UserDetails userDetails = searchService.getNamedQueryEntity(
                    UserDetails.class,
                    "UserDetails.findAllDevicesByUserId",
                    parameterFactory.createStringParameter(USER_ID, userId));
            Device newDevice = transformationFunctionService.getEntity(Device.class, deviceDto);

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
            session.merge(userDetails);
        });
    }

    @Override
    public void deactivateCurrentDevice(String userId, String deviceName) {
        entityOperationManager.executeConsumer(session -> {

        });
    }

    @Override
    public void deleteDevice(String userId, DeviceDto deviceDto) {
        entityOperationManager.executeConsumer(session -> {
            UserDetails userDetails = searchService.getNamedQueryEntity(
                    UserDetails.class,
                    "UserDetails.findAllDevicesByUserId",
                    parameterFactory.createStringParameter(USER_ID, userId));

            userDetails.getDevices().stream()
                    .filter(device -> device.getPlatform().equals(deviceDto.getPlatform())
                            && device.getScreenHeight() == deviceDto.getScreenHeight()
                            && device.getScreenWidth() == deviceDto.getScreenWidth()
                            && device.getUserAgent().equals(deviceDto.getUserAgent())
                    )
                    .findFirst()
                    .ifPresent(device -> {
                        userDetails.getDevices().remove(device);
                        session.merge(userDetails);
                    });
        });
    }

    @Override
    public List<ResponseDeviceDto> getDevicesByUserId(String userId, String clientId) {
        UserDetails userDetails = searchService.getNamedQueryEntity(
                UserDetails.class,
                "UserDetails.findAllDevicesByUserId",
                parameterFactory.createStringParameter(USER_ID, userId));

        return userDetails.getDevices().stream()
                .map(device -> {
                    ResponseDeviceDto responseDeviceDto =
                            transformationFunctionService.getTransformationFunction(Device.class, ResponseDeviceDto.class).apply(device);
                    responseDeviceDto.setThisDevice(device.getIpAddress().equals(clientId));
                    return responseDeviceDto;
                })
                .toList();


    }
}

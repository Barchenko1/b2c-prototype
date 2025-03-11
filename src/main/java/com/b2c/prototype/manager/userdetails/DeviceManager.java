package com.b2c.prototype.manager.userdetails;

import com.b2c.prototype.dao.user.IDeviceDao;
import com.b2c.prototype.modal.dto.payload.DeviceDto;
import com.b2c.prototype.modal.dto.response.ResponseDeviceDto;
import com.b2c.prototype.modal.entity.user.Device;
import com.b2c.prototype.service.function.ITransformationFunctionService;
import com.tm.core.finder.factory.IParameterFactory;
import com.tm.core.process.dao.identifier.IQueryService;
import com.tm.core.process.manager.common.EntityOperationManager;
import com.tm.core.process.manager.common.IEntityOperationManager;

import java.util.List;

public class DeviceManager implements IDeviceManager {

    private final IEntityOperationManager entityOperationManager;
    private final ITransformationFunctionService transformationFunctionService;
    private final IQueryService queryService;
    private final IParameterFactory parameterFactory;

    public DeviceManager(IDeviceDao deviceDao, ITransformationFunctionService transformationFunctionService, IQueryService queryService, IParameterFactory parameterFactory) {
        this.entityOperationManager = new EntityOperationManager(deviceDao);
        this.transformationFunctionService = transformationFunctionService;
        this.queryService = queryService;
        this.parameterFactory = parameterFactory;
    }

    @Override
    public void putDevice(String userId, DeviceDto deviceDto) {
        entityOperationManager.executeConsumer(session -> {

        });
    }

    @Override
    public void deleteDevice(String userId, DeviceDto deviceDto) {
        entityOperationManager.executeConsumer(session -> {

        });
    }

    @Override
    public List<ResponseDeviceDto> getDevicesByUserId(String userId) {
        return List.of();
    }
}

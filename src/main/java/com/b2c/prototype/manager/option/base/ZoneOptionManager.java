package com.b2c.prototype.manager.option.base;

import com.b2c.prototype.dao.option.IZoneOptionDao;
import com.b2c.prototype.manager.option.IZoneOptionManager;
import com.b2c.prototype.modal.dto.payload.option.ZoneOptionDto;
import com.b2c.prototype.modal.entity.option.ZoneOption;
import com.b2c.prototype.transform.function.ITransformationFunctionService;
import com.tm.core.finder.factory.IParameterFactory;
import com.tm.core.process.manager.common.EntityOperationManager;
import com.tm.core.process.manager.common.IEntityOperationManager;

import java.util.List;

import static com.b2c.prototype.util.Constant.VALUE;

public class ZoneOptionManager implements IZoneOptionManager {

    private final IEntityOperationManager entityOperationManager;
    private final ITransformationFunctionService transformationFunctionService;
    private final IParameterFactory parameterFactory;

    public ZoneOptionManager(IZoneOptionDao zoneOptionDao, ITransformationFunctionService transformationFunctionService, IParameterFactory parameterFactory) {
        this.entityOperationManager = new EntityOperationManager(zoneOptionDao);
        this.transformationFunctionService = transformationFunctionService;
        this.parameterFactory = parameterFactory;
    }

    @Override
    public void saveUpdateZoneOption(String zoneValue, ZoneOptionDto zoneOptionDto) {
        entityOperationManager.executeConsumer(session -> {
            ZoneOption zoneOption = transformationFunctionService.getEntity(session, ZoneOption.class, zoneOptionDto);
            if (zoneValue != null) {
                ZoneOption existingZoneOption = entityOperationManager.getNamedQueryEntity(
                        "ZoneOption.findAllWithPriceAndCurrency",
                        parameterFactory.createStringParameter(VALUE, zoneValue));
                zoneOption.setId(existingZoneOption.getId());
                zoneOption.getPrice().setId(existingZoneOption.getPrice().getId());
                zoneOption.getPrice().getCurrency().setId(existingZoneOption.getPrice().getCurrency().getId());
            }
            session.merge(zoneOption);
        });
    }

    @Override
    public void deleteZoneOption(String zoneValue) {
        entityOperationManager.deleteEntityByParameter(
                parameterFactory.createStringParameter(VALUE, zoneValue));
    }

    @Override
    public ZoneOptionDto getZoneOptionDto(String zoneValue) {
        return entityOperationManager.getNamedQueryEntityDto(
                "ZoneOption.findAllWithPriceAndCurrency",
                parameterFactory.createStringParameter(VALUE, zoneValue),
                transformationFunctionService.getTransformationFunction(ZoneOption.class, ZoneOptionDto.class));
    }

    @Override
    public List<ZoneOptionDto> getZoneOptionDtoList() {
        return entityOperationManager.getNamedQueryEntityDtoList(
                "ZoneOption.all",
                transformationFunctionService.getTransformationFunction(ZoneOption.class, ZoneOptionDto.class));
    }
}

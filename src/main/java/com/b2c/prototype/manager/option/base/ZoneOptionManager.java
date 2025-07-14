package com.b2c.prototype.manager.option.base;

import com.b2c.prototype.manager.option.IZoneOptionManager;
import com.b2c.prototype.modal.dto.payload.option.ZoneOptionDto;
import com.b2c.prototype.modal.entity.option.ZoneOption;
import com.b2c.prototype.transform.function.ITransformationFunctionService;
import com.tm.core.finder.factory.IParameterFactory;
import com.tm.core.process.dao.common.ITransactionEntityDao;
import com.tm.core.process.manager.common.operator.EntityOperationManager;
import com.tm.core.process.manager.common.IEntityOperationManager;
import org.hibernate.Session;

import java.util.List;
import java.util.Optional;

import static com.b2c.prototype.util.Constant.VALUE;

public class ZoneOptionManager implements IZoneOptionManager {

    private final IEntityOperationManager entityOperationManager;
    private final ITransformationFunctionService transformationFunctionService;
    private final IParameterFactory parameterFactory;

    public ZoneOptionManager(ITransactionEntityDao zoneOptionDao, ITransformationFunctionService transformationFunctionService, IParameterFactory parameterFactory) {
        this.entityOperationManager = new EntityOperationManager(zoneOptionDao);
        this.transformationFunctionService = transformationFunctionService;
        this.parameterFactory = parameterFactory;
    }

    @Override
    public void saveUpdateZoneOption(String zoneValue, ZoneOptionDto zoneOptionDto) {
        entityOperationManager.executeConsumer(session -> {
            ZoneOption zoneOption = transformationFunctionService.getEntity((Session) session, ZoneOption.class, zoneOptionDto);
            if (zoneValue != null) {
                ZoneOption existingZoneOption = entityOperationManager.getNamedQueryEntityClose(
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
        entityOperationManager.executeConsumer(session -> {
            ZoneOption zoneOption = entityOperationManager.getNamedQueryEntityClose(
                    "ZoneOption.findByValue",
                    parameterFactory.createStringParameter(VALUE, zoneValue));
            session.remove(zoneOption);
        });
    }

    @Override
    public ZoneOptionDto getZoneOptionDto(String zoneValue) {
        ZoneOption zoneOption = entityOperationManager.getNamedQueryEntityClose(
                "ZoneOption.findAllWithPriceAndCurrency",
                parameterFactory.createStringParameter(VALUE, zoneValue)
        );

        return Optional.ofNullable(zoneOption)
                .map(transformationFunctionService.getTransformationFunction(ZoneOption.class, ZoneOptionDto.class))
                .orElseThrow(() -> new RuntimeException(""));
    }

    @Override
    public List<ZoneOptionDto> getZoneOptionDtoList() {
        List<ZoneOption> zoneOptionList = entityOperationManager.getNamedQueryEntityListClose(
                "ZoneOption.all");

        return zoneOptionList.stream()
                .map(transformationFunctionService.getTransformationFunction(ZoneOption.class, ZoneOptionDto.class))
                .toList();
    }
}

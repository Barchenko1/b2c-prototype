package com.b2c.prototype.manager.option.base;

import com.b2c.prototype.dao.IGeneralEntityDao;
import com.b2c.prototype.manager.option.IZoneOptionManager;
import com.b2c.prototype.modal.dto.payload.option.ZoneOptionDto;
import com.b2c.prototype.modal.entity.option.ZoneOption;
import com.b2c.prototype.transform.item.IItemTransformService;
import com.nimbusds.jose.util.Pair;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

import static com.b2c.prototype.util.Constant.VALUE;

@Service
public class ZoneOptionManager implements IZoneOptionManager {

    private final IGeneralEntityDao generalEntityDao;
    private final IItemTransformService itemTransformService;

    public ZoneOptionManager(IGeneralEntityDao generalEntityDao,
                             IItemTransformService itemTransformService) {
        this.generalEntityDao = generalEntityDao;
        this.itemTransformService = itemTransformService;
    }

    @Override
    public void saveUpdateZoneOption(String zoneValue, ZoneOptionDto zoneOptionDto) {
        ZoneOption zoneOption = itemTransformService.mapZoneOptionDtoToZoneOption(zoneOptionDto);
        if (zoneValue != null) {
            ZoneOption existingZoneOption = generalEntityDao.findEntity(
                    "ZoneOption.findAllWithPriceAndCurrency",
                    Pair.of(VALUE, zoneValue));
            zoneOption.setId(existingZoneOption.getId());
            zoneOption.getPrice().setId(existingZoneOption.getPrice().getId());
            zoneOption.getPrice().getCurrency().setId(existingZoneOption.getPrice().getCurrency().getId());
        }
        generalEntityDao.mergeEntity(zoneOption);
    }

    @Override
    public void deleteZoneOption(String zoneValue) {
        ZoneOption zoneOption = generalEntityDao.findEntity(
                "ZoneOption.findByValue",
                Pair.of(VALUE, zoneValue));
        generalEntityDao.removeEntity(zoneOption);
    }

    @Override
    public ZoneOptionDto getZoneOptionDto(String zoneValue) {
        ZoneOption zoneOption = generalEntityDao.findEntity(
                "ZoneOption.findAllWithPriceAndCurrency",
                Pair.of(VALUE, zoneValue)
        );

        return Optional.ofNullable(zoneOption)
                .map(itemTransformService::mapZoneOptionToZoneOptionDto)
                .orElseThrow(() -> new RuntimeException(""));
    }

    @Override
    public List<ZoneOptionDto> getZoneOptionDtoList() {
        List<ZoneOption> zoneOptionList = generalEntityDao.findEntityList(
                "ZoneOption.all", (Pair<String, ?>) null);

        return zoneOptionList.stream()
                .map(itemTransformService::mapZoneOptionToZoneOptionDto)
                .toList();
    }
}

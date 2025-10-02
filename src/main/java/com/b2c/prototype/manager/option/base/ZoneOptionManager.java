package com.b2c.prototype.manager.option.base;

import com.b2c.prototype.dao.IGeneralEntityDao;
import com.b2c.prototype.manager.option.IZoneOptionManager;
import com.b2c.prototype.modal.dto.payload.option.ZoneOptionDto;
import com.b2c.prototype.modal.entity.option.ZoneOption;
import com.b2c.prototype.transform.item.IItemTransformService;
import com.b2c.prototype.transform.order.IOrderTransformService;
import com.nimbusds.jose.util.Pair;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

import static com.b2c.prototype.util.Constant.VALUE;

@Service
public class ZoneOptionManager implements IZoneOptionManager {

    private final IGeneralEntityDao generalEntityDao;
    private final IOrderTransformService orderTransformService;

    public ZoneOptionManager(IGeneralEntityDao generalEntityDao,
                             IOrderTransformService orderTransformService) {
        this.generalEntityDao = generalEntityDao;
        this.orderTransformService = orderTransformService;
    }

    @Transactional
    @Override
    public void persistEntity(ZoneOptionDto zoneOptionDto) {
        ZoneOption zoneOption = orderTransformService.mapZoneOptionDtoToZoneOption(zoneOptionDto);
        generalEntityDao.persistEntity(zoneOption);
    }

    @Transactional
    @Override
    public void mergeEntity(String zoneValue, ZoneOptionDto zoneOptionDto) {
        ZoneOption zoneOption = orderTransformService.mapZoneOptionDtoToZoneOption(zoneOptionDto);
        ZoneOption existingZoneOption = generalEntityDao.findEntity(
                "ZoneOption.findAllWithPriceAndCurrency",
                Pair.of(VALUE, zoneValue));
        zoneOption.setId(existingZoneOption.getId());
        zoneOption.getPrice().setId(existingZoneOption.getPrice().getId());
        generalEntityDao.mergeEntity(zoneOption);
    }

    @Transactional
    @Override
    public void removeEntity(String zoneValue) {
        ZoneOption zoneOption = generalEntityDao.findEntity("ZoneOption.findByValue", Pair.of(VALUE, zoneValue));
        generalEntityDao.removeEntity(zoneOption);
    }

    @Override
    public ZoneOptionDto getEntity(String value) {
        return Optional.of(generalEntityDao.findEntity("ZoneOption.findAllWithPriceAndCurrency", Pair.of(VALUE, value)))
                .map(e -> orderTransformService.mapZoneOptionToZoneOptionDto((ZoneOption) e))
                .orElseThrow(() -> new RuntimeException(""));
    }

    @Override
    public Optional<ZoneOptionDto> getEntityOptional(String value) {
        return generalEntityDao.findOptionEntity("ZoneOption.findAllWithPriceAndCurrency", Pair.of(VALUE, value));
    }

    @Override
    public List<ZoneOptionDto> getEntities() {
        return generalEntityDao.findEntityList("ZoneOption.all", (Pair<String, ?>) null).stream()
                .map(e -> orderTransformService.mapZoneOptionToZoneOptionDto((ZoneOption) e))
                .toList();
    }
}

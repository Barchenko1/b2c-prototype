package com.b2c.prototype.manager.option.base;

import com.b2c.prototype.dao.IGeneralEntityDao;
import com.b2c.prototype.manager.option.IZoneOptionManager;
import com.b2c.prototype.modal.dto.payload.option.group.ZoneOptionGroupDto;
import com.b2c.prototype.modal.entity.option.ZoneOption;
import com.b2c.prototype.modal.entity.option.ZoneOptionGroup;
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
    public void persistEntity(ZoneOptionGroup zoneOptionGroup) {
        ZoneOptionGroupDto zoneOptionGroupDto = orderTransformService.mapZoneOptionGroupToZoneOptionGroupDto(zoneOptionGroup);
        generalEntityDao.persistEntity(zoneOptionGroupDto);
    }

    @Transactional
    @Override
    public void mergeEntity(String value, ZoneOptionGroupDto zoneOptionGroupDto) {
//        ZoneOption zoneOption = orderTransformService.mapZoneOptionDtoToZoneOption(zoneOptionDto);
//        ZoneOption existingZoneOption = generalEntityDao.findEntity(
//                "ZoneOption.findAllWithPriceAndCurrency",
//                Pair.of(VALUE, zoneValue));
//        zoneOption.setId(existingZoneOption.getId());
//        zoneOption.getPrice().setId(existingZoneOption.getPrice().getId());
//        generalEntityDao.mergeEntity(zoneOption);
    }

    @Transactional
    @Override
    public void removeEntity(String zoneValue) {
        ZoneOption zoneOption = generalEntityDao.findEntity("ZoneOption.findByValue", Pair.of(VALUE, zoneValue));
        generalEntityDao.removeEntity(zoneOption);
    }

    @Override
    public ZoneOptionGroup getEntity(String value) {
        return generalEntityDao.findEntity("ZoneOption.findAllWithPriceAndCurrency", Pair.of(VALUE, value));
    }

    @Override
    public Optional<ZoneOptionGroup> getEntityOptional(String value) {
        return generalEntityDao.findOptionEntity("ZoneOption.findAllWithPriceAndCurrency", Pair.of(VALUE, value));
    }

    @Override
    public List<ZoneOptionGroup> getEntities() {
        return generalEntityDao.findEntityList("ZoneOption.all", (Pair<String, ?>) null);
    }
}

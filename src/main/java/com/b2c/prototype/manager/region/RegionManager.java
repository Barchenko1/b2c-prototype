package com.b2c.prototype.manager.region;


import com.b2c.prototype.dao.IGeneralEntityDao;
import com.b2c.prototype.modal.dto.payload.region.RegionDto;
import com.b2c.prototype.modal.entity.price.Currency;
import com.b2c.prototype.modal.entity.region.Region;
import com.b2c.prototype.transform.constant.IGeneralEntityTransformService;
import com.nimbusds.jose.util.Pair;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.ZoneId;
import java.util.List;
import java.util.Optional;

import static com.b2c.prototype.util.Constant.CODE;
import static com.b2c.prototype.util.Constant.KEY;

@Service
public class RegionManager implements IRegionManager {

    private final IGeneralEntityDao generalEntityDao;
    private final IGeneralEntityTransformService generalEntityTransformService;

    public RegionManager(IGeneralEntityDao generalEntityDao,
                         IGeneralEntityTransformService generalEntityTransformService) {
        this.generalEntityDao = generalEntityDao;
        this.generalEntityTransformService = generalEntityTransformService;
    }

    @Override
    @Transactional
    public void persistEntity(RegionDto regionDto) {
        Region region = generalEntityTransformService.mapRegionDtoToRegion(regionDto);
        Optional<Currency> currencyOptional = generalEntityDao.findOptionEntity("Currency.findByKey",
                Pair.of(KEY, regionDto.getPrimaryCurrency().getKey()));
        if (currencyOptional.isEmpty()) {
            region.setPrimaryCurrency(
                    Currency.builder()
                            .key(regionDto.getPrimaryCurrency().getKey())
                            .value(regionDto.getPrimaryCurrency().getValue())
                    .build()
            );
        } else {
            region.setPrimaryCurrency(currencyOptional.get());
        }
        generalEntityDao.persistEntity(region);
    }

    @Override
    @Transactional
    public void mergeEntity(String code, RegionDto regionDto) {
        Region region = generalEntityTransformService.mapRegionDtoToRegion(regionDto);
        Region existingRegion = generalEntityDao.findEntity("Region.findByCode", Pair.of(CODE, code));
        existingRegion.setCode(region.getCode());
        existingRegion.setValue(region.getValue());
        existingRegion.setLanguage(region.getLanguage());
        existingRegion.setDefaultLocale(region.getDefaultLocale());
        existingRegion.setPrimaryCurrency(region.getPrimaryCurrency());
        existingRegion.setTimezone(region.getTimezone());
        generalEntityDao.mergeEntity(existingRegion);
    }

    @Override
    @Transactional
    public void removeEntity(String code) {
        Region region = generalEntityDao.findEntity("Region.findByCode", Pair.of(CODE, code));
        Currency currency = region.getPrimaryCurrency();

        generalEntityDao.removeEntity(region);
        if (currency != null) {
            Long refs = generalEntityDao.findEntity(
                    "Region.countByCurrency",
                    Pair.of(KEY, currency.getKey())
            );
            if (refs != null && refs == 0L) {
                generalEntityDao.removeEntity(currency);
            }
        }
    }

    @Override
    @Transactional(readOnly = true)
    public List<Region> getEntityList() {
        return generalEntityDao.findEntityList("Region.findAll", (Pair<String, ?>) null);
    }

    @Override
    @Transactional(readOnly = true)
    public Region getEntity(String code) {
        return generalEntityDao.findEntity("Region.findByCode", Pair.of(CODE, code));
    }
}

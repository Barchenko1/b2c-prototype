package com.b2c.prototype.manager.region;


import com.b2c.prototype.dao.IGeneralEntityDao;
import com.b2c.prototype.modal.dto.payload.tenant.TenantDto;
import com.b2c.prototype.modal.entity.price.Currency;
import com.b2c.prototype.modal.entity.tenant.Tenant;
import com.b2c.prototype.transform.constant.IGeneralEntityTransformService;
import com.nimbusds.jose.util.Pair;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
    public void persistEntity(TenantDto tenantDto) {
        Tenant tenant = generalEntityTransformService.mapRegionDtoToRegion(tenantDto);
        Optional<Currency> currencyOptional = generalEntityDao.findOptionEntity("Currency.findByKey",
                Pair.of(KEY, tenantDto.getPrimaryCurrency().getKey()));
        if (currencyOptional.isEmpty()) {
            tenant.setPrimaryCurrency(
                    Currency.builder()
                            .key(tenantDto.getPrimaryCurrency().getKey())
                            .value(tenantDto.getPrimaryCurrency().getValue())
                    .build()
            );
        } else {
            tenant.setPrimaryCurrency(currencyOptional.get());
        }
        generalEntityDao.persistEntity(tenant);
    }

    @Override
    @Transactional
    public void mergeEntity(String code, TenantDto tenantDto) {
        Tenant tenant = generalEntityTransformService.mapRegionDtoToRegion(tenantDto);
        Tenant existingTenant = generalEntityDao.findEntity("Tenant.findByCode", Pair.of(CODE, code));
        existingTenant.setCode(tenant.getCode());
        existingTenant.setValue(tenant.getValue());
        existingTenant.setLanguage(tenant.getLanguage());
        existingTenant.setDefaultLocale(tenant.getDefaultLocale());
        existingTenant.setPrimaryCurrency(tenant.getPrimaryCurrency());
        existingTenant.setTimezone(tenant.getTimezone());
        generalEntityDao.mergeEntity(existingTenant);
    }

    @Override
    @Transactional
    public void removeEntity(String code) {
        Tenant tenant = generalEntityDao.findEntity("Tenant.findByCode", Pair.of(CODE, code));
        Currency currency = tenant.getPrimaryCurrency();

        generalEntityDao.removeEntity(tenant);
        if (currency != null) {
            Long refs = generalEntityDao.findEntity(
                    "Tenant.countByCurrency",
                    Pair.of(KEY, currency.getKey())
            );
            if (refs != null && refs == 0L) {
                generalEntityDao.removeEntity(currency);
            }
        }
    }

    @Override
    @Transactional(readOnly = true)
    public List<Tenant> getEntityList() {
        return generalEntityDao.findEntityList("Tenant.findAll", (Pair<String, ?>) null);
    }

    @Override
    @Transactional(readOnly = true)
    public Tenant getEntity(String code) {
        return generalEntityDao.findEntity("Tenant.findByCode", Pair.of(CODE, code));
    }
}

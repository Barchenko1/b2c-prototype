package com.b2c.prototype.manager.region;

import com.b2c.prototype.dao.IGeneralEntityDao;
import com.b2c.prototype.modal.dto.payload.tenant.LanguageDto;
import com.b2c.prototype.modal.dto.payload.tenant.TenantDto;
import com.b2c.prototype.modal.entity.price.Currency;
import com.b2c.prototype.modal.entity.store.StoreGeneralBoard;
import com.b2c.prototype.modal.entity.tenant.Language;
import com.b2c.prototype.modal.entity.tenant.Tenant;
import com.b2c.prototype.transform.constant.IGeneralEntityTransformService;
import com.nimbusds.jose.util.Pair;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import static com.b2c.prototype.util.Constant.CODE;
import static com.b2c.prototype.util.Constant.KEY;

@Service
public class TenantManager implements ITenantManager {

    private final IGeneralEntityDao generalEntityDao;
    private final IGeneralEntityTransformService generalEntityTransformService;

    public TenantManager(IGeneralEntityDao generalEntityDao,
                         IGeneralEntityTransformService generalEntityTransformService) {
        this.generalEntityDao = generalEntityDao;
        this.generalEntityTransformService = generalEntityTransformService;
    }

    @Override
    @Transactional
    public void persistEntity(TenantDto tenantDto) {
        Tenant tenant = generalEntityTransformService.mapTenantDtoToTenant(tenantDto);
        StoreGeneralBoard storeGeneralBoard = StoreGeneralBoard.builder()
                .tenant(tenant)
                .build();

        generalEntityDao.persistEntity(tenant);
        generalEntityDao.persistEntity(storeGeneralBoard);
    }

    @Override
    @Transactional
    public void mergeEntity(String code, TenantDto tenantDto) {
        Tenant tenant = generalEntityTransformService.mapTenantDtoToTenant(tenantDto);
        Tenant existingTenant = generalEntityDao.findEntity(
                "Tenant.findByCode",
                Pair.of(CODE, code));

        Set<String> dtoLanguageCodes = tenantDto.getLanguages().stream()
                .map(LanguageDto::getKey)
                .filter(Objects::nonNull)
                .collect(Collectors.toSet());

        existingTenant.getLanguages().stream()
                .filter(l -> !dtoLanguageCodes.contains(l.getLanguageCode()))
                .forEach(existingTenant::removeLanguage);

        tenantDto.getLanguages().forEach(languageDto -> {
            if (languageDto.getKey() != null) {
                Optional<Language> existingLanguage = existingTenant.getLanguages().stream()
                        .filter(lang -> lang.getLanguageCode().equals(languageDto.getKey()))
                        .findFirst();
                if (existingLanguage.isPresent()) {
                    existingLanguage.get().setLanguageCode(languageDto.getLanguageCode());
                    existingLanguage.get().setName(languageDto.getName());
                    existingLanguage.get().setActive(languageDto.isActive());
                }
            } else {
                boolean alreadyExists = existingTenant.getLanguages().stream()
                        .anyMatch(lang -> lang.getLanguageCode().equals(languageDto.getLanguageCode()));
                if (!alreadyExists) {
                    Language newLanguage = generalEntityTransformService.mapLanguageDtoToLanguage(languageDto);
                    existingTenant.addLanguage(newLanguage);
                }
            }
        });

        existingTenant.setValue(tenant.getValue());
        existingTenant.setDefaultLocale(tenant.getDefaultLocale());
        existingTenant.setPrimaryCurrency(tenant.getPrimaryCurrency());
        existingTenant.setTimezone(tenant.getTimezone());
    }

    @Override
    @Transactional
    public void removeEntity(String code) {
        Tenant tenant = generalEntityDao.findEntity(
                "Tenant.findByCode",
                Pair.of(CODE, code));
        Currency currency = tenant.getPrimaryCurrency();
        Set<Language> languages = tenant.getLanguages();
        StoreGeneralBoard storeGeneralBoard = generalEntityDao.findEntity(
                "StoreGeneralBoard.findByTenant",
                Pair.of(CODE, code));
        generalEntityDao.removeEntity(storeGeneralBoard);
        generalEntityDao.removeEntity(tenant);
        if (currency != null) {
            Long refs = generalEntityDao.findEntity(
                    "Tenant.countByCurrency",
                    Pair.of(KEY, currency.getKey()));
            if (refs != null && refs == 0L) {
                generalEntityDao.removeEntity(currency);
            }
        }
        if (languages != null && !languages.isEmpty()) {
            for (Language language : languages) {
                Long langRefs = generalEntityDao.findEntity(
                        "Language.countByLanguageCode",
                        Pair.of("languageCode", language.getLanguageCode()));
                if (langRefs != null && langRefs == 0L) {
                    generalEntityDao.removeEntity(language);
                }
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

package com.b2c.prototype.processor.region;

import com.b2c.prototype.manager.region.ITenantManager;
import com.b2c.prototype.modal.dto.payload.tenant.TenantDto;
import com.b2c.prototype.transform.constant.IGeneralEntityTransformService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TenantProcess implements ITenantProcess {

    private final ObjectMapper objectMapper;
    private final ITenantManager regionManager;
    private final IGeneralEntityTransformService generalEntityTransformService;

    public TenantProcess(ObjectMapper objectMapper,
                         ITenantManager regionManager,
                         IGeneralEntityTransformService generalEntityTransformService) {
        this.objectMapper = objectMapper;
        this.regionManager = regionManager;
        this.generalEntityTransformService = generalEntityTransformService;
    }

    @Override
    public void persistEntity(TenantDto tenantDto) {
        regionManager.persistEntity(tenantDto);
    }

    @Override
    public void mergeEntity(final String code, final TenantDto tenantDto) {
        regionManager.mergeEntity(code, tenantDto);
    }

    @Override
    public void removeEntity(final String code) {
        regionManager.removeEntity(code);
    }

    @Override
    public List<TenantDto> getEntityList() {
        return regionManager.getEntityList().stream()
                .map(generalEntityTransformService::mapTenantToTenantDto)
                .toList();
    }

    @Override
    public TenantDto getEntity(final String code) {
        return Optional.ofNullable(regionManager.getEntity(code))
                .map(generalEntityTransformService::mapTenantToTenantDto)
                .orElseThrow(() -> new RuntimeException("Tenant not found"));
    }
}

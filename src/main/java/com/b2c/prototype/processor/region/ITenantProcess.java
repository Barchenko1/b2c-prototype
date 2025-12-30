package com.b2c.prototype.processor.region;

import com.b2c.prototype.modal.dto.payload.tenant.TenantDto;

import java.util.List;

public interface ITenantProcess {
    void persistEntity(final TenantDto tenantDto);
    void mergeEntity(final String code, final TenantDto tenantDto);
    void removeEntity(final String code);

    List<TenantDto> getEntityList();
    TenantDto getEntity(final String code);
}

package com.b2c.prototype.manager.region;

import com.b2c.prototype.modal.dto.payload.tenant.TenantDto;
import com.b2c.prototype.modal.entity.tenant.Tenant;

import java.util.List;

public interface IRegionManager {
    void persistEntity(final TenantDto tenantDto);
    void mergeEntity(final String code, TenantDto tenantDto);
    void removeEntity(final String code);

    List<Tenant> getEntityList();
    Tenant getEntity(final String code);
}

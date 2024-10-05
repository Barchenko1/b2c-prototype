package com.b2c.prototype.service.base.item.base;

import com.b2c.prototype.dao.item.IBrandDao;
import com.b2c.prototype.modal.dto.common.RequestOneFieldEntityDto;
import com.b2c.prototype.modal.dto.common.RequestOneFieldEntityDtoUpdate;
import com.b2c.prototype.modal.entity.item.Brand;
import com.b2c.prototype.dao.cashed.IEntityStringMapWrapper;
import com.b2c.prototype.service.single.AbstractSingleEntityService;
import com.b2c.prototype.service.base.item.IBrandService;
import com.b2c.prototype.util.StringFormatConverter;
import com.tm.core.dao.single.ISingleEntityDao;
import com.tm.core.processor.finder.parameter.Parameter;

public class BrandSingleEntityService extends AbstractSingleEntityService implements IBrandService {

    private final IBrandDao brandDao;
    private final IEntityStringMapWrapper<Brand> brandEntityMapWrapper;

    public BrandSingleEntityService(IBrandDao brandDao, IEntityStringMapWrapper<Brand> brandEntityMapWrapper) {
        this.brandDao = brandDao;
        this.brandEntityMapWrapper = brandEntityMapWrapper;
    }

    @Override
    protected ISingleEntityDao getEntityDao() {
        return this.brandDao;
    }

    @Override
    public void saveBrand(RequestOneFieldEntityDto requestOneFieldEntityDto) {
        String formatName = StringFormatConverter.firstLetterToUpperCaseOtherLower(
                requestOneFieldEntityDto.getRequestValue());
        Brand newBrand = Brand.builder()
                .name(formatName)
                .build();
        brandDao.saveEntity(newBrand);
        brandEntityMapWrapper.putEntity(formatName, newBrand);
    }

    @Override
    public void updateBrand(RequestOneFieldEntityDtoUpdate requestOneFieldEntityDtoUpdate) {
        RequestOneFieldEntityDto newEntityDto = requestOneFieldEntityDtoUpdate.getNewEntityDto();
        RequestOneFieldEntityDto oldEntityDto = requestOneFieldEntityDtoUpdate.getOldEntityDto();

        String formatNewName = StringFormatConverter.firstLetterToUpperCaseOtherLower(
                newEntityDto.getRequestValue());
        Brand newBrand = Brand.builder()
                .name(formatNewName)
                .build();

        Parameter parameter = parameterFactory.createStringParameter("name", oldEntityDto.getRequestValue());
        getEntityDao().findEntityAndUpdate(newBrand, parameter);

        brandEntityMapWrapper.updateEntity(
                oldEntityDto.getRequestValue(),
                newEntityDto.getRequestValue(),
                newBrand);
    }

    @Override
    public void deleteBrand(RequestOneFieldEntityDto requestOneFieldEntityDto) {
        Parameter parameter = parameterFactory.createStringParameter("name", requestOneFieldEntityDto.getRequestValue());
        getEntityDao().findEntityAndDelete(parameter);

        brandEntityMapWrapper.removeEntity(requestOneFieldEntityDto.getRequestValue());
    }
}

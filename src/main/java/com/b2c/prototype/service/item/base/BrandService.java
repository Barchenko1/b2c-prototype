package com.b2c.prototype.service.item.base;

import com.b2c.prototype.dao.item.IBrandDao;
import com.b2c.prototype.modal.dto.common.RequestOneFieldEntityDto;
import com.b2c.prototype.modal.dto.common.RequestOneFieldEntityDtoUpdate;
import com.b2c.prototype.modal.entity.item.Brand;
import com.b2c.prototype.dao.wrapper.IEntityStringMapWrapper;
import com.b2c.prototype.service.item.IBrandService;
import com.b2c.prototype.util.StringFormatConverter;

import static com.b2c.prototype.util.Query.DELETE_BRAND_BY_NAME;
import static com.b2c.prototype.util.Query.UPDATE_BRAND_BY_NAME;

public class BrandService implements IBrandService {

    private final IBrandDao brandDao;
    private final IEntityStringMapWrapper<Brand> brandEntityMapWrapper;

    public BrandService(IBrandDao brandDao, IEntityStringMapWrapper<Brand> brandEntityMapWrapper) {
        this.brandDao = brandDao;
        this.brandEntityMapWrapper = brandEntityMapWrapper;
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

        brandDao.mutateEntityBySQLQueryWithParams(
                UPDATE_BRAND_BY_NAME,
                formatNewName,
                oldEntityDto.getRequestValue());

        brandEntityMapWrapper.updateEntity(
                oldEntityDto.getRequestValue(),
                newEntityDto.getRequestValue(),
                Brand.builder()
                        .name(formatNewName)
                        .build());
    }

    @Override
    public void deleteBrand(RequestOneFieldEntityDto requestOneFieldEntityDto) {
        brandDao.mutateEntityBySQLQueryWithParams(
                DELETE_BRAND_BY_NAME,
                requestOneFieldEntityDto.getRequestValue());

        brandEntityMapWrapper.removeEntity(requestOneFieldEntityDto.getRequestValue());
    }
}

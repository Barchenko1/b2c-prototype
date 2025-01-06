package com.b2c.prototype.service.processor.userprofile.basic;

import com.b2c.prototype.dao.user.IContactInfoDao;
import com.b2c.prototype.modal.dto.common.OneFieldEntityDto;
import com.b2c.prototype.modal.dto.request.ContactInfoDto;
import com.b2c.prototype.modal.dto.searchfield.ContactInfoSearchFieldEntityDto;
import com.b2c.prototype.modal.entity.user.ContactInfo;
import com.b2c.prototype.modal.entity.user.UserProfile;
import com.b2c.prototype.service.common.EntityOperationDao;
import com.b2c.prototype.service.common.IEntityOperationDao;
import com.b2c.prototype.service.function.ITransformationFunctionService;
import com.b2c.prototype.service.processor.query.IQueryService;
import com.b2c.prototype.service.processor.userprofile.IContactInfoService;
import com.b2c.prototype.service.supplier.ISupplierService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static com.b2c.prototype.util.Constant.USER_ID;

public class ContactInfoService implements IContactInfoService {

    private static final Logger LOGGER = LoggerFactory.getLogger(ContactInfoService.class);

    private final IEntityOperationDao entityOperationDao;
    private final IQueryService queryService;
    private final ITransformationFunctionService transformationFunctionService;
    private final ISupplierService supplierService;

    public ContactInfoService(IContactInfoDao contactInfoDao,
                              IQueryService queryService,
                              ITransformationFunctionService transformationFunctionService,
                              ISupplierService supplierService) {
        this.entityOperationDao = new EntityOperationDao(contactInfoDao);
        this.queryService = queryService;
        this.transformationFunctionService = transformationFunctionService;
        this.supplierService = supplierService;
    }

    @Override
    public void saveUpdateContactInfoByUserId(ContactInfoSearchFieldEntityDto contactInfoSearchFieldEntityDto) {
        entityOperationDao.executeConsumer(session -> {
            UserProfile userProfile = queryService.getEntity(
                    UserProfile.class,
                    supplierService.parameterStringSupplier(USER_ID, contactInfoSearchFieldEntityDto.getSearchField()));
            ContactInfo newContactInfo = transformationFunctionService
                    .getEntity(ContactInfo.class, contactInfoSearchFieldEntityDto.getNewEntity());
            ContactInfo contactInfo = userProfile.getContactInfo();
            if (contactInfo != null) {
                newContactInfo.setId(contactInfo.getId());
            }
            userProfile.setContactInfo(newContactInfo);
            session.merge(userProfile);
        });
    }

    @Override
    public void deleteContactInfoByUserId(OneFieldEntityDto oneFieldEntityDto) {
        entityOperationDao.deleteEntity(
                supplierService.entityFieldSupplier(
                        UserProfile.class,
                        supplierService.parameterStringSupplier(USER_ID, oneFieldEntityDto.getValue()),
                        transformationFunctionService.getTransformationFunction(UserProfile.class, ContactInfo.class))
        );
    }

    @Override
    public ContactInfoDto getContactInfoByUserId(OneFieldEntityDto oneFieldEntityDto) {
        return queryService.getEntityDto(
                UserProfile.class,
                supplierService.parameterStringSupplier(USER_ID, oneFieldEntityDto.getValue()),
                transformationFunctionService.getTransformationFunction(UserProfile.class, ContactInfoDto.class));
    }

}

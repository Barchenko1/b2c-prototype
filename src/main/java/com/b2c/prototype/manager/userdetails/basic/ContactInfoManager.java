package com.b2c.prototype.manager.userdetails.basic;

import com.b2c.prototype.dao.user.IContactInfoDao;
import com.b2c.prototype.modal.dto.payload.ContactInfoDto;
import com.b2c.prototype.modal.entity.user.ContactInfo;
import com.b2c.prototype.modal.entity.user.UserDetails;
import com.b2c.prototype.service.common.EntityOperationManager;
import com.b2c.prototype.service.common.IEntityOperationManager;
import com.b2c.prototype.service.function.ITransformationFunctionService;
import com.b2c.prototype.service.query.ISearchService;
import com.b2c.prototype.manager.userdetails.IContactInfoManager;
import com.b2c.prototype.service.supplier.ISupplierService;
import com.tm.core.finder.factory.IParameterFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static com.b2c.prototype.util.Constant.USER_ID;

public class ContactInfoManager implements IContactInfoManager {

    private static final Logger LOGGER = LoggerFactory.getLogger(ContactInfoManager.class);

    private final IEntityOperationManager entityOperationDao;
    private final ISearchService searchService;
    private final ITransformationFunctionService transformationFunctionService;
    private final ISupplierService supplierService;
    private final IParameterFactory parameterFactory;

    public ContactInfoManager(IContactInfoDao contactInfoDao,
                              ISearchService searchService,
                              ITransformationFunctionService transformationFunctionService,
                              ISupplierService supplierService,
                              IParameterFactory parameterFactory) {
        this.entityOperationDao = new EntityOperationManager(contactInfoDao);
        this.searchService = searchService;
        this.transformationFunctionService = transformationFunctionService;
        this.supplierService = supplierService;
        this.parameterFactory = parameterFactory;
    }

    @Override
    public void saveUpdateContactInfoByUserId(String userId, ContactInfoDto contactInfoDto) {
        entityOperationDao.executeConsumer(session -> {
            UserDetails userDetails = searchService.getNamedQueryEntity(
                    UserDetails.class,
                    "",
                    parameterFactory.createStringParameter(USER_ID, userId));
            ContactInfo newContactInfo = transformationFunctionService
                    .getEntity(ContactInfo.class, contactInfoDto);
            ContactInfo contactInfo = userDetails.getContactInfo();
            if (contactInfo != null) {
                newContactInfo.setId(contactInfo.getId());
            }
            userDetails.setContactInfo(newContactInfo);
            session.merge(userDetails);
        });
    }

    @Override
    public void deleteContactInfoByUserId(String userId) {
        entityOperationDao.deleteEntity(
                supplierService.entityFieldSupplier(
                        UserDetails.class,
                        "",
                        supplierService.parameterStringSupplier(USER_ID, userId),
                        transformationFunctionService.getTransformationFunction(UserDetails.class, ContactInfo.class))
        );
    }

    @Override
    public ContactInfoDto getContactInfoByUserId(String userId) {
        return searchService.getNamedQueryEntityDto(
                UserDetails.class,
                "",
                parameterFactory.createStringParameter(USER_ID, userId),
                transformationFunctionService.getTransformationFunction(UserDetails.class, ContactInfoDto.class));
    }

}

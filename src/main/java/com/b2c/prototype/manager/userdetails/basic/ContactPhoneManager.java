package com.b2c.prototype.manager.userdetails.basic;

import com.b2c.prototype.dao.user.IContactPhoneDao;
import com.b2c.prototype.modal.dto.payload.ContactInfoDto;
import com.b2c.prototype.modal.dto.payload.ContactPhoneDto;
import com.b2c.prototype.modal.entity.order.DeliveryArticularItemQuantity;
import com.b2c.prototype.modal.entity.user.ContactInfo;
import com.b2c.prototype.modal.entity.user.ContactPhone;
import com.b2c.prototype.modal.entity.user.UserDetails;
import com.b2c.prototype.service.function.ITransformationFunctionService;
import com.b2c.prototype.service.query.ISearchService;
import com.b2c.prototype.manager.userdetails.IContactPhoneManager;
import com.b2c.prototype.service.supplier.ISupplierService;
import com.tm.core.finder.factory.IParameterFactory;
import com.tm.core.process.manager.common.EntityOperationManager;
import com.tm.core.process.manager.common.IEntityOperationManager;

import java.util.List;

import static com.b2c.prototype.util.Constant.ORDER_ID;
import static com.b2c.prototype.util.Constant.USER_ID;

public class ContactPhoneManager implements IContactPhoneManager {

    private final IEntityOperationManager entityOperationDao;
    private final ISearchService searchService;
    private final ITransformationFunctionService transformationFunctionService;
    private final ISupplierService supplierService;
    private final IParameterFactory parameterFactory;

    public ContactPhoneManager(IContactPhoneDao contactPhoneDao,
                               ISearchService searchService,
                               ITransformationFunctionService transformationFunctionService,
                               ISupplierService supplierService,
                               IParameterFactory parameterFactory) {
        this.entityOperationDao = new EntityOperationManager(contactPhoneDao);
        this.transformationFunctionService = transformationFunctionService;
        this.supplierService = supplierService;
        this.searchService = searchService;
        this.parameterFactory = parameterFactory;
    }

    @Override
    public void saveUpdateContactPhoneByUserId(String userId, ContactPhoneDto contactPhoneDto) {
        entityOperationDao.executeConsumer(session -> {
            UserDetails userDetails = searchService.getGraphEntity(
                    UserDetails.class,
                    "",
                    parameterFactory.createStringParameter(USER_ID, userId));
            ContactPhone newContactPhone = transformationFunctionService.getEntity(
                    ContactPhone.class,
                    contactPhoneDto);
            ContactInfo contactInfo = userDetails.getContactInfo();
            contactInfo.setContactPhone(newContactPhone);
            session.merge(contactInfo);
        });
    }

    @Override
    public void saveUpdateContactPhoneByOrderId(String orderId, ContactInfoDto contactInfoDto) {
        entityOperationDao.executeConsumer(session -> {
            DeliveryArticularItemQuantity orderItemDataOption = searchService.getNamedQueryEntity(
                    DeliveryArticularItemQuantity.class,
                    "",
                    parameterFactory.createStringParameter(ORDER_ID, orderId));
            ContactPhone newContactPhone = transformationFunctionService
                    .getEntity(ContactPhone.class, contactInfoDto);
            ContactInfo beneficiary = orderItemDataOption.getBeneficiary();
            beneficiary.setContactPhone(newContactPhone);
            session.merge(beneficiary);
        });
    }

    @Override
    public void deleteContactPhoneByUserId(String userId) {
        entityOperationDao.deleteEntity(
                supplierService.entityFieldSupplier(
                        UserDetails.class,
                        "",
                        supplierService.parameterStringSupplier(USER_ID, userId),
                        transformationFunctionService.getTransformationFunction(UserDetails.class, ContactPhone.class)));
    }

    @Override
    public void deleteContactPhoneByOrderId(String orderId) {
        entityOperationDao.executeConsumer(session -> {
            DeliveryArticularItemQuantity orderItemDataOption = searchService.getNamedQueryEntity(
                    DeliveryArticularItemQuantity.class,
                    "",
                    parameterFactory.createStringParameter(ORDER_ID, orderId));
            ContactInfo beneficiary = orderItemDataOption.getBeneficiary();
            session.remove(beneficiary.getContactPhone());
        });
    }

    @Override
    public ContactPhoneDto getContactPhoneByUserId(String userId) {
        return searchService.getNamedQueryEntityDto(
                UserDetails.class,
                "",
                parameterFactory.createStringParameter(USER_ID, userId),
                transformationFunctionService.getTransformationFunction(UserDetails.class, ContactPhoneDto.class));
    }

    @Override
    public List<ContactPhoneDto> getContactPhoneByOrderId(String orderId) {
        return searchService.getSubNamedQueryEntityDtoList(
                DeliveryArticularItemQuantity.class,
                "",
                parameterFactory.createStringParameter(ORDER_ID, orderId),
                transformationFunctionService.getTransformationFunction(DeliveryArticularItemQuantity.class, ContactPhoneDto.class, "list"));
    }

    @Override
    public List<ContactPhoneDto> getContactPhoneList() {
        return entityOperationDao.getGraphEntityDtoList(
                "",
                transformationFunctionService.getTransformationFunction(ContactPhone.class, ContactPhoneDto.class));
    }

}

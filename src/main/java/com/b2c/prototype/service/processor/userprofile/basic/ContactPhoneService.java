package com.b2c.prototype.service.processor.userprofile.basic;

import com.b2c.prototype.dao.user.IContactPhoneDao;
import com.b2c.prototype.modal.dto.common.OneFieldEntityDto;
import com.b2c.prototype.modal.dto.request.ContactInfoSearchFieldOrderNumberDto;
import com.b2c.prototype.modal.dto.request.ContactPhoneDto;
import com.b2c.prototype.modal.dto.update.ContactPhoneDtoUpdate;
import com.b2c.prototype.modal.entity.order.OrderItemData;
import com.b2c.prototype.modal.entity.user.ContactInfo;
import com.b2c.prototype.modal.entity.user.ContactPhone;
import com.b2c.prototype.modal.entity.user.UserProfile;
import com.b2c.prototype.service.function.ITransformationFunctionService;
import com.b2c.prototype.service.processor.query.IQueryService;
import com.b2c.prototype.service.processor.userprofile.IContactPhoneService;
import com.b2c.prototype.service.common.EntityOperationDao;
import com.b2c.prototype.service.common.IEntityOperationDao;
import com.b2c.prototype.service.supplier.ISupplierService;

import java.util.List;
import java.util.function.Supplier;

public class ContactPhoneService implements IContactPhoneService {

    private final IEntityOperationDao entityOperationDao;
    private final IQueryService queryService;
    private final ITransformationFunctionService transformationFunctionService;
    private final ISupplierService supplierService;

    public ContactPhoneService(IContactPhoneDao contactPhoneDao,
                               IQueryService queryService,
                               ITransformationFunctionService transformationFunctionService,
                               ISupplierService supplierService) {
        this.entityOperationDao = new EntityOperationDao(contactPhoneDao);
        this.transformationFunctionService = transformationFunctionService;
        this.supplierService = supplierService;
        this.queryService = queryService;
    }

    @Override
    public void saveUpdateContactPhoneByUserId(ContactPhoneDtoUpdate contactPhoneDtoUpdate) {
        entityOperationDao.executeConsumer(session -> {
            UserProfile userProfile = queryService.getEntity(
                    UserProfile.class,
                    supplierService.parameterStringSupplier("user_id", contactPhoneDtoUpdate.getSearchField()));
            ContactPhone newContactPhone = transformationFunctionService.getEntity(
                    ContactPhone.class,
                    contactPhoneDtoUpdate.getNewEntity());
            ContactInfo contactInfo = userProfile.getContactInfo();
            contactInfo.setContactPhone(newContactPhone);
            session.merge(contactInfo);
        });
    }

    @Override
    public void saveUpdateContactPhoneByOrderId(ContactPhoneDtoUpdate contactPhoneDtoUpdate) {
        entityOperationDao.executeConsumer(session -> {
            OrderItemData orderItemData = queryService.getEntity(
                    OrderItemData.class,
                    supplierService.parameterStringSupplier("order_id", contactPhoneDtoUpdate.getSearchField()));
            ContactPhone newContactPhone = transformationFunctionService
                    .getEntity(ContactPhone.class, contactPhoneDtoUpdate.getNewEntity());
            List<ContactInfo> contactInfoList = orderItemData.getBeneficiaries();
            ContactInfo contactInfo = contactInfoList.get(contactPhoneDtoUpdate.getOrderNumber());
            contactInfo.setContactPhone(newContactPhone);
            session.merge(contactInfo);
        });
    }

    @Override
    public void deleteContactPhoneByUserId(OneFieldEntityDto oneFieldEntityDto) {
        entityOperationDao.deleteEntity(
                userProfileContactPhoneSupplier(oneFieldEntityDto.getValue()));
    }

    @Override
    public void deleteContactPhoneByOrderId(ContactInfoSearchFieldOrderNumberDto contactInfoSearchFieldOrderNumberDto) {
        entityOperationDao.deleteEntity(
                orderItemContactPhoneSupplier(contactInfoSearchFieldOrderNumberDto));
    }

    @Override
    public ContactPhoneDto getContactPhoneByUserId(OneFieldEntityDto oneFieldEntityDto) {
        return queryService.getEntityDto(
                UserProfile.class,
                supplierService.parameterStringSupplier("user_id", oneFieldEntityDto.getValue()),
                transformationFunctionService.getTransformationFunction(UserProfile.class, ContactPhoneDto.class));
    }

    @Override
    public List<ContactPhoneDto> getContactPhoneByOrderId(OneFieldEntityDto oneFieldEntityDto) {
        return queryService.getSubEntityDtoList(
                OrderItemData.class,
                supplierService.parameterStringSupplier("order_id", oneFieldEntityDto.getValue()),
                transformationFunctionService.getTransformationFunction(OrderItemData.class, ContactPhoneDto.class, "list"));
    }

    @Override
    public List<ContactPhoneDto> getContactPhoneList() {
        return entityOperationDao.getEntityDtoList(
                transformationFunctionService.getTransformationFunction(ContactPhone.class, ContactPhoneDto.class));
    }

    private Supplier<ContactPhone> userProfileContactPhoneSupplier(String userId) {
        return () -> {
            UserProfile userProfile = queryService.getEntity(UserProfile.class,
                    supplierService.parameterStringSupplier("user_id", userId));
            return userProfile.getContactInfo().getContactPhone();
        };
    }

    private Supplier<ContactPhone> orderItemContactPhoneSupplier(
            ContactInfoSearchFieldOrderNumberDto contactInfoSearchFieldOrderNumberDto) {
        return () -> {
            OrderItemData orderItemData = queryService.getEntity(OrderItemData.class,
                    supplierService.parameterStringSupplier("order_id", contactInfoSearchFieldOrderNumberDto.getValue()));
            List<ContactInfo> contactInfoList = orderItemData.getBeneficiaries();
            return contactInfoList.get(contactInfoSearchFieldOrderNumberDto.getOrderNumber()).getContactPhone();
        };
    }

}

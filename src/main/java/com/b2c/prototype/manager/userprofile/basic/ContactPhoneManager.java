package com.b2c.prototype.manager.userprofile.basic;

import com.b2c.prototype.dao.user.IContactPhoneDao;
import com.b2c.prototype.modal.dto.common.OneFieldEntityDto;
import com.b2c.prototype.modal.dto.searchfield.BeneficiarySearchFieldOrderNumberDto;
import com.b2c.prototype.modal.dto.payload.ContactPhoneDto;
import com.b2c.prototype.modal.dto.searchfield.ContactPhoneSearchFieldEntityDto;
import com.b2c.prototype.modal.entity.order.OrderArticularItem;
import com.b2c.prototype.modal.entity.order.Beneficiary;
import com.b2c.prototype.modal.entity.user.ContactInfo;
import com.b2c.prototype.modal.entity.user.ContactPhone;
import com.b2c.prototype.modal.entity.user.UserProfile;
import com.b2c.prototype.service.function.ITransformationFunctionService;
import com.b2c.prototype.service.query.IQueryService;
import com.b2c.prototype.manager.userprofile.IContactPhoneManager;
import com.b2c.prototype.service.common.EntityOperationManager;
import com.b2c.prototype.service.common.IEntityOperationManager;
import com.b2c.prototype.service.supplier.ISupplierService;

import java.util.List;

import static com.b2c.prototype.util.Constant.ORDER_ID;
import static com.b2c.prototype.util.Constant.USER_ID;

public class ContactPhoneManager implements IContactPhoneManager {

    private final IEntityOperationManager entityOperationDao;
    private final IQueryService queryService;
    private final ITransformationFunctionService transformationFunctionService;
    private final ISupplierService supplierService;

    public ContactPhoneManager(IContactPhoneDao contactPhoneDao,
                               IQueryService queryService,
                               ITransformationFunctionService transformationFunctionService,
                               ISupplierService supplierService) {
        this.entityOperationDao = new EntityOperationManager(contactPhoneDao);
        this.transformationFunctionService = transformationFunctionService;
        this.supplierService = supplierService;
        this.queryService = queryService;
    }

    @Override
    public void saveUpdateContactPhoneByUserId(ContactPhoneSearchFieldEntityDto contactPhoneSearchFieldEntityDto) {
        entityOperationDao.executeConsumer(session -> {
            UserProfile userProfile = queryService.getEntity(
                    UserProfile.class,
                    supplierService.parameterStringSupplier(USER_ID, contactPhoneSearchFieldEntityDto.getSearchField()));
            ContactPhone newContactPhone = transformationFunctionService.getEntity(
                    ContactPhone.class,
                    contactPhoneSearchFieldEntityDto.getNewEntity());
            ContactInfo contactInfo = userProfile.getContactInfo();
            contactInfo.setContactPhone(newContactPhone);
            session.merge(contactInfo);
        });
    }

    @Override
    public void saveUpdateContactPhoneByOrderId(ContactPhoneSearchFieldEntityDto contactPhoneSearchFieldEntityDto) {
        entityOperationDao.executeConsumer(session -> {
            OrderArticularItem orderItemDataOption = queryService.getEntity(
                    OrderArticularItem.class,
                    supplierService.parameterStringSupplier(ORDER_ID, contactPhoneSearchFieldEntityDto.getSearchField()));
            ContactPhone newContactPhone = transformationFunctionService
                    .getEntity(ContactPhone.class, contactPhoneSearchFieldEntityDto.getNewEntity());
            List<Beneficiary> beneficiaryList = orderItemDataOption.getBeneficiaries();
            Beneficiary beneficiary = beneficiaryList.get(contactPhoneSearchFieldEntityDto.getOrderNumber());
            beneficiary.setContactPhone(newContactPhone);
            session.merge(beneficiary);
        });
    }

    @Override
    public void deleteContactPhoneByUserId(OneFieldEntityDto oneFieldEntityDto) {
        entityOperationDao.deleteEntity(
                supplierService.entityFieldSupplier(
                        UserProfile.class,
                        supplierService.parameterStringSupplier(USER_ID, oneFieldEntityDto.getValue()),
                        transformationFunctionService.getTransformationFunction(UserProfile.class, ContactPhone.class)));
    }

    @Override
    public void deleteContactPhoneByOrderId(BeneficiarySearchFieldOrderNumberDto beneficiarySearchFieldOrderNumberDto) {
        entityOperationDao.executeConsumer(session -> {
            OrderArticularItem orderItemDataOption = queryService.getEntity(OrderArticularItem.class,
                    supplierService.parameterStringSupplier(ORDER_ID, beneficiarySearchFieldOrderNumberDto.getValue()));
            List<Beneficiary> beneficiaryList = orderItemDataOption.getBeneficiaries();
            ContactPhone contactPhone =
                    beneficiaryList.get(beneficiarySearchFieldOrderNumberDto.getOrderNumber()).getContactPhone();
            session.remove(contactPhone);
        });
    }

    @Override
    public ContactPhoneDto getContactPhoneByUserId(OneFieldEntityDto oneFieldEntityDto) {
        return queryService.getEntityDto(
                UserProfile.class,
                supplierService.parameterStringSupplier(USER_ID, oneFieldEntityDto.getValue()),
                transformationFunctionService.getTransformationFunction(UserProfile.class, ContactPhoneDto.class));
    }

    @Override
    public List<ContactPhoneDto> getContactPhoneByOrderId(OneFieldEntityDto oneFieldEntityDto) {
        return queryService.getSubEntityDtoList(
                OrderArticularItem.class,
                supplierService.parameterStringSupplier(ORDER_ID, oneFieldEntityDto.getValue()),
                transformationFunctionService.getTransformationFunction(OrderArticularItem.class, ContactPhoneDto.class, "list"));
    }

    @Override
    public List<ContactPhoneDto> getContactPhoneList() {
        return entityOperationDao.getEntityGraphDtoList("",
                transformationFunctionService.getTransformationFunction(ContactPhone.class, ContactPhoneDto.class));
    }

}

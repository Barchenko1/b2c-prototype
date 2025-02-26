package com.b2c.prototype.manager.userprofile.basic;

import com.b2c.prototype.dao.user.IContactPhoneDao;
import com.b2c.prototype.modal.dto.payload.BeneficiaryDto;
import com.b2c.prototype.modal.dto.payload.ContactPhoneDto;
import com.b2c.prototype.modal.entity.order.OrderArticularItem;
import com.b2c.prototype.modal.entity.order.Beneficiary;
import com.b2c.prototype.modal.entity.user.ContactInfo;
import com.b2c.prototype.modal.entity.user.ContactPhone;
import com.b2c.prototype.modal.entity.user.UserDetails;
import com.b2c.prototype.service.function.ITransformationFunctionService;
import com.b2c.prototype.service.query.ISearchService;
import com.b2c.prototype.manager.userprofile.IContactPhoneManager;
import com.b2c.prototype.service.common.EntityOperationManager;
import com.b2c.prototype.service.common.IEntityOperationManager;
import com.b2c.prototype.service.supplier.ISupplierService;
import com.tm.core.finder.factory.IParameterFactory;

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
            UserDetails userProfile = searchService.getEntity(
                    UserDetails.class,
                    supplierService.parameterStringSupplier(USER_ID, userId));
            ContactPhone newContactPhone = transformationFunctionService.getEntity(
                    ContactPhone.class,
                    contactPhoneDto);
            ContactInfo contactInfo = userProfile.getContactInfo();
            contactInfo.setContactPhone(newContactPhone);
            session.merge(contactInfo);
        });
    }

    @Override
    public void saveUpdateContactPhoneByOrderId(String orderId, BeneficiaryDto beneficiaryDto) {
        entityOperationDao.executeConsumer(session -> {
            OrderArticularItem orderItemDataOption = searchService.getEntity(
                    OrderArticularItem.class,
                    supplierService.parameterStringSupplier(ORDER_ID, orderId));
            ContactPhone newContactPhone = transformationFunctionService
                    .getEntity(ContactPhone.class, beneficiaryDto);
            List<Beneficiary> beneficiaryList = orderItemDataOption.getBeneficiaries();
            Beneficiary beneficiary = beneficiaryList.get(beneficiaryDto.getOrderNumber());
            beneficiary.setContactPhone(newContactPhone);
            session.merge(beneficiary);
        });
    }

    @Override
    public void deleteContactPhoneByUserId(String userId) {
        entityOperationDao.deleteEntity(
                supplierService.entityFieldSupplier(
                        UserDetails.class,
                        supplierService.parameterStringSupplier(USER_ID, userId),
                        transformationFunctionService.getTransformationFunction(UserDetails.class, ContactPhone.class)));
    }

    @Override
    public void deleteContactPhoneByOrderId(String orderId, int beneficiaryNumber) {
        entityOperationDao.executeConsumer(session -> {
            OrderArticularItem orderItemDataOption = searchService.getEntity(OrderArticularItem.class,
                    supplierService.parameterStringSupplier(ORDER_ID, orderId));
            List<Beneficiary> beneficiaryList = orderItemDataOption.getBeneficiaries();
            ContactPhone contactPhone =
                    beneficiaryList.get(beneficiaryNumber).getContactPhone();
            session.remove(contactPhone);
        });
    }

    @Override
    public ContactPhoneDto getContactPhoneByUserId(String userId) {
        return searchService.getEntityDto(
                UserDetails.class,
                supplierService.parameterStringSupplier(USER_ID, userId),
                transformationFunctionService.getTransformationFunction(UserDetails.class, ContactPhoneDto.class));
    }

    @Override
    public List<ContactPhoneDto> getContactPhoneByOrderId(String orderId) {
        return searchService.getSubEntityDtoList(
                OrderArticularItem.class,
                parameterFactory.createStringParameter(ORDER_ID, orderId),
                transformationFunctionService.getTransformationFunction(OrderArticularItem.class, ContactPhoneDto.class, "list"));
    }

    @Override
    public List<ContactPhoneDto> getContactPhoneList() {
        return entityOperationDao.getEntityGraphDtoList("",
                transformationFunctionService.getTransformationFunction(ContactPhone.class, ContactPhoneDto.class));
    }

}

package com.b2c.prototype.manager.userdetails.basic;

import com.b2c.prototype.dao.user.IContactInfoDao;
import com.b2c.prototype.modal.dto.payload.ContactInfoDto;
import com.b2c.prototype.modal.entity.order.DeliveryArticularItemQuantity;
import com.b2c.prototype.modal.entity.user.ContactInfo;
import com.b2c.prototype.modal.entity.user.UserDetails;
import com.b2c.prototype.service.function.ITransformationFunctionService;
import com.b2c.prototype.service.query.ISearchService;
import com.b2c.prototype.manager.userdetails.IContactInfoManager;
import com.tm.core.finder.factory.IParameterFactory;
import com.tm.core.process.manager.common.EntityOperationManager;
import com.tm.core.process.manager.common.IEntityOperationManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static com.b2c.prototype.util.Constant.ORDER_ID;
import static com.b2c.prototype.util.Constant.USER_ID;

public class ContactInfoManager implements IContactInfoManager {

    private static final Logger LOGGER = LoggerFactory.getLogger(ContactInfoManager.class);

    private final IEntityOperationManager entityOperationDao;
    private final ISearchService searchService;
    private final ITransformationFunctionService transformationFunctionService;
    private final IParameterFactory parameterFactory;

    public ContactInfoManager(IContactInfoDao contactInfoDao,
                              ISearchService searchService,
                              ITransformationFunctionService transformationFunctionService,
                              IParameterFactory parameterFactory) {
        this.entityOperationDao = new EntityOperationManager(contactInfoDao);
        this.searchService = searchService;
        this.transformationFunctionService = transformationFunctionService;
        this.parameterFactory = parameterFactory;
    }

    @Override
    public void saveUpdateContactInfoByUserId(String userId, ContactInfoDto contactInfoDto) {
        entityOperationDao.executeConsumer(session -> {
            UserDetails userDetails = searchService.getNamedQueryEntity(
                    UserDetails.class,
                    "UserDetails.findFullUserDetailsByUserId",
                    parameterFactory.createStringParameter(USER_ID, userId));
            ContactInfo newContactInfo = transformationFunctionService.getEntity(session, ContactInfo.class, contactInfoDto);
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
        entityOperationDao.executeConsumer(session -> {
            UserDetails userDetails = searchService.getNamedQueryEntity(
                    UserDetails.class,
                    "UserDetails.findFullUserDetailsByUserId",
                    parameterFactory.createStringParameter(USER_ID, userId));
            ContactInfo contactInfo = userDetails.getContactInfo();
            if (contactInfo != null) {
                userDetails.setContactInfo(null);
                session.remove(contactInfo);
                session.merge(userDetails);
            }
        });
    }

    @Override
    public ContactInfoDto getContactInfoByUserId(String userId) {
        return searchService.getNamedQueryEntityDto(
                UserDetails.class,
                "UserDetails.findFullUserDetailsByUserId",
                parameterFactory.createStringParameter(USER_ID, userId),
                transformationFunctionService.getTransformationFunction(UserDetails.class, ContactInfoDto.class));
    }

    @Override
    public void saveUpdateContactInfoByOrderId(String orderId, ContactInfoDto contactInfoDto) {
        entityOperationDao.executeConsumer(session -> {
            DeliveryArticularItemQuantity orderItemDataOption = searchService.getNamedQueryEntity(
                    DeliveryArticularItemQuantity.class,
                    "DeliveryArticularItemQuantity.findByOrderIdWithBeneficiaries",
                    parameterFactory.createStringParameter(ORDER_ID, orderId));

            ContactInfo existingBeneficiary = orderItemDataOption.getBeneficiary();
            ContactInfo newBeneficiary = transformationFunctionService.getEntity(session, ContactInfo.class, contactInfoDto);

            if (existingBeneficiary != null) {
                newBeneficiary.setId(existingBeneficiary.getId());
                newBeneficiary.getContactPhone().setId(existingBeneficiary.getContactPhone().getId());
            }

            orderItemDataOption.setBeneficiary(newBeneficiary);
            session.merge(orderItemDataOption);
        });
    }

    @Override
    public void deleteContactInfoByOrderId(String orderId) {
        entityOperationDao.executeConsumer(session -> {
            DeliveryArticularItemQuantity orderItemDataOption = searchService.getNamedQueryEntity(
                    DeliveryArticularItemQuantity.class,
                    "DeliveryArticularItemQuantity.findByOrderIdWithBeneficiaries",
                    parameterFactory.createStringParameter(ORDER_ID, orderId));
            ContactInfo beneficiary = orderItemDataOption.getBeneficiary();
            session.remove(beneficiary);
        });
    }

    @Override
    public ContactInfoDto getContactInfoByOrderId(String orderId) {
        return searchService.getNamedQueryEntityDto(
                DeliveryArticularItemQuantity.class,
                "DeliveryArticularItemQuantity.findByOrderIdWithBeneficiaries",
                parameterFactory.createStringParameter(ORDER_ID, orderId),
                transformationFunctionService.getTransformationFunction(DeliveryArticularItemQuantity.class, ContactInfoDto.class));
    }

}

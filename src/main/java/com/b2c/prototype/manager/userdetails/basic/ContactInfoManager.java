package com.b2c.prototype.manager.userdetails.basic;

import com.b2c.prototype.dao.user.IContactInfoDao;
import com.b2c.prototype.modal.dto.payload.order.ContactInfoDto;
import com.b2c.prototype.modal.entity.order.DeliveryArticularItemQuantity;
import com.b2c.prototype.modal.entity.user.ContactInfo;
import com.b2c.prototype.modal.entity.user.UserDetails;
import com.b2c.prototype.transform.function.ITransformationFunctionService;
import com.b2c.prototype.manager.userdetails.IContactInfoManager;
import com.tm.core.finder.factory.IParameterFactory;
import com.tm.core.process.dao.query.IFetchHandler;
import com.tm.core.process.manager.common.EntityOperationManager;
import com.tm.core.process.manager.common.IEntityOperationManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Optional;

import static com.b2c.prototype.util.Constant.ORDER_ID;
import static com.b2c.prototype.util.Constant.USER_ID;

public class ContactInfoManager implements IContactInfoManager {

    private static final Logger LOGGER = LoggerFactory.getLogger(ContactInfoManager.class);

    private final IEntityOperationManager entityOperationManager;
    private final IFetchHandler fetchHandler;
    private final ITransformationFunctionService transformationFunctionService;
    private final IParameterFactory parameterFactory;

    public ContactInfoManager(IContactInfoDao contactInfoDao,
                              IFetchHandler fetchHandler,
                              ITransformationFunctionService transformationFunctionService,
                              IParameterFactory parameterFactory) {
        this.entityOperationManager = new EntityOperationManager(contactInfoDao);
        this.fetchHandler = fetchHandler;
        this.transformationFunctionService = transformationFunctionService;
        this.parameterFactory = parameterFactory;
    }

    @Override
    public void saveUpdateContactInfoByUserId(String userId, ContactInfoDto contactInfoDto) {
        entityOperationManager.executeConsumer(session -> {
            UserDetails userDetails = fetchHandler.getNamedQueryEntity(
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
        entityOperationManager.executeConsumer(session -> {
            UserDetails userDetails = fetchHandler.getNamedQueryEntity(
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
        UserDetails userDetails = fetchHandler.getNamedQueryEntity(
                UserDetails.class,
                "UserDetails.findFullUserDetailsByUserId",
                parameterFactory.createStringParameter(USER_ID, userId));

        return Optional.of(userDetails)
                .map(transformationFunctionService.getTransformationFunction(UserDetails.class, ContactInfoDto.class))
                .orElseThrow(() -> new RuntimeException(""));
    }

    @Override
    public void saveUpdateContactInfoByOrderId(String orderId, ContactInfoDto contactInfoDto) {
        entityOperationManager.executeConsumer(session -> {
            DeliveryArticularItemQuantity orderItemDataOption = fetchHandler.getNamedQueryEntity(
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
        entityOperationManager.executeConsumer(session -> {
            DeliveryArticularItemQuantity orderItemDataOption = fetchHandler.getNamedQueryEntity(
                    DeliveryArticularItemQuantity.class,
                    "DeliveryArticularItemQuantity.findByOrderIdWithBeneficiaries",
                    parameterFactory.createStringParameter(ORDER_ID, orderId));
            ContactInfo beneficiary = orderItemDataOption.getBeneficiary();
            session.remove(beneficiary);
        });
    }

    @Override
    public ContactInfoDto getContactInfoByOrderId(String orderId) {
        DeliveryArticularItemQuantity deliveryArticularItemQuantity = fetchHandler.getNamedQueryEntity(
                DeliveryArticularItemQuantity.class,
                "DeliveryArticularItemQuantity.findByOrderIdWithBeneficiaries",
                parameterFactory.createStringParameter(ORDER_ID, orderId));

        return Optional.of(deliveryArticularItemQuantity)
                .map(transformationFunctionService.getTransformationFunction(DeliveryArticularItemQuantity.class, ContactInfoDto.class))
                .orElseThrow(() -> new RuntimeException(""));
    }

}

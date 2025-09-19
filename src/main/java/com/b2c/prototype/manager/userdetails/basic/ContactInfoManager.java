package com.b2c.prototype.manager.userdetails.basic;

import com.b2c.prototype.dao.IGeneralEntityDao;
import com.b2c.prototype.modal.dto.payload.order.ContactInfoDto;
import com.b2c.prototype.modal.entity.order.DeliveryArticularItemQuantity;
import com.b2c.prototype.modal.entity.user.ContactInfo;
import com.b2c.prototype.modal.entity.user.UserDetails;
import com.b2c.prototype.transform.function.ITransformationFunctionService;
import com.b2c.prototype.manager.userdetails.IContactInfoManager;
import com.nimbusds.jose.util.Pair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.Optional;

import static com.b2c.prototype.util.Constant.ORDER_ID;
import static com.b2c.prototype.util.Constant.USER_ID;

@Service
public class ContactInfoManager implements IContactInfoManager {

    private static final Logger LOGGER = LoggerFactory.getLogger(ContactInfoManager.class);

    private final IGeneralEntityDao generalEntityDao;
    private final ITransformationFunctionService transformationFunctionService;

    public ContactInfoManager(IGeneralEntityDao generalEntityDao,
                              ITransformationFunctionService transformationFunctionService) {
        this.generalEntityDao = generalEntityDao;
        this.transformationFunctionService = transformationFunctionService;
    }

    @Override
    public void saveUpdateContactInfoByUserId(String userId, ContactInfoDto contactInfoDto) {
        UserDetails userDetails = generalEntityDao.findEntity(
                "UserDetails.findFullUserDetailsByUserId",
                Pair.of(USER_ID, userId));
        ContactInfo newContactInfo = transformationFunctionService.getEntity(ContactInfo.class, contactInfoDto);
        ContactInfo contactInfo = userDetails.getContactInfo();
        if (contactInfo != null) {
            newContactInfo.setId(contactInfo.getId());
        }
        userDetails.setContactInfo(newContactInfo);
        generalEntityDao.mergeEntity(userDetails);
    }

    @Override
    public void deleteContactInfoByUserId(String userId) {
        UserDetails userDetails = generalEntityDao.findEntity(
                "UserDetails.findFullUserDetailsByUserId",
                Pair.of(USER_ID, userId));
        ContactInfo contactInfo = userDetails.getContactInfo();
        if (contactInfo != null) {
            userDetails.setContactInfo(null);
            generalEntityDao.removeEntity(contactInfo);
            generalEntityDao.mergeEntity(userDetails);
        }
    }

    @Override
    public ContactInfoDto getContactInfoByUserId(String userId) {
        UserDetails userDetails = generalEntityDao.findEntity(
                "UserDetails.findFullUserDetailsByUserId",
                Pair.of(USER_ID, userId));

        return Optional.of(userDetails)
                .map(transformationFunctionService.getTransformationFunction(UserDetails.class, ContactInfoDto.class))
                .orElseThrow(() -> new RuntimeException(""));
    }

    @Override
    public void saveUpdateContactInfoByOrderId(String orderId, ContactInfoDto contactInfoDto) {
        DeliveryArticularItemQuantity orderItemDataOption = generalEntityDao.findEntity(
                "DeliveryArticularItemQuantity.findByOrderIdWithBeneficiaries",
                Pair.of(ORDER_ID, orderId));

//            ContactInfo existingBeneficiary = orderItemDataOption.getBeneficiary();
//            ContactInfo newBeneficiary = transformationFunctionService.getEntity((Session) session, ContactInfo.class, contactInfoDto);
//
//            if (existingBeneficiary != null) {
//                newBeneficiary.setId(existingBeneficiary.getId());
//                newBeneficiary.getContactPhone().setId(existingBeneficiary.getContactPhone().getId());
//            }

//            orderItemDataOption.setBeneficiary(newBeneficiary);
        generalEntityDao.mergeEntity(orderItemDataOption);
    }

    @Override
    public void deleteContactInfoByOrderId(String orderId) {
        DeliveryArticularItemQuantity orderItemDataOption = generalEntityDao.findEntity(
                "DeliveryArticularItemQuantity.findByOrderIdWithBeneficiaries",
                Pair.of(ORDER_ID, orderId));
//            ContactInfo beneficiary = orderItemDataOption.getBeneficiary();
//            session.remove(beneficiary);
    }

    @Override
    public ContactInfoDto getContactInfoByOrderId(String orderId) {
        DeliveryArticularItemQuantity deliveryArticularItemQuantity = generalEntityDao.findEntity(
                "DeliveryArticularItemQuantity.findByOrderIdWithBeneficiaries",
                Pair.of(ORDER_ID, orderId));

        return Optional.of(deliveryArticularItemQuantity)
                .map(transformationFunctionService.getTransformationFunction(DeliveryArticularItemQuantity.class, ContactInfoDto.class))
                .orElseThrow(() -> new RuntimeException(""));
    }

}

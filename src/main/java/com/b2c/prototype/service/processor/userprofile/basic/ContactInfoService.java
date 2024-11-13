package com.b2c.prototype.service.processor.userprofile.basic;

import com.b2c.prototype.dao.cashed.IEntityCachedMap;
import com.b2c.prototype.dao.order.IOrderItemDao;
import com.b2c.prototype.dao.user.IContactInfoDao;
import com.b2c.prototype.dao.user.IUserProfileDao;
import com.b2c.prototype.modal.dto.common.OneFieldEntityDto;
import com.b2c.prototype.modal.dto.request.ContactInfoDto;
import com.b2c.prototype.modal.dto.update.ContactInfoDtoUpdate;
import com.b2c.prototype.modal.entity.order.OrderItem;
import com.b2c.prototype.modal.entity.user.ContactInfo;
import com.b2c.prototype.modal.entity.user.ContactPhone;
import com.b2c.prototype.modal.entity.user.CountryPhoneCode;
import com.b2c.prototype.modal.entity.user.UserProfile;
import com.b2c.prototype.service.processor.userprofile.IContactInfoService;
import com.tm.core.dao.factory.IGeneralEntityFactory;
import com.tm.core.modal.GeneralEntity;
import com.tm.core.processor.finder.factory.IParameterFactory;
import com.tm.core.processor.finder.parameter.Parameter;
import org.hibernate.Session;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.function.Consumer;
import java.util.function.Supplier;

public class ContactInfoService implements IContactInfoService {
    private static final Logger LOG = LoggerFactory.getLogger(ContactInfoService.class);

    private final IParameterFactory parameterFactory;
    private final IGeneralEntityFactory generalEntityFactory;
    private final IContactInfoDao contactInfoDao;
    private final IUserProfileDao userProfileDao;
    private final IOrderItemDao orderItemDao;
    private final IEntityCachedMap entityCachedMap;

    public ContactInfoService(IParameterFactory parameterFactory,
                              IGeneralEntityFactory generalEntityFactory,
                              IContactInfoDao contactInfoDao,
                              IUserProfileDao userProfileDao,
                              IOrderItemDao orderItemDao,
                              IEntityCachedMap entityCachedMap) {
        this.parameterFactory = parameterFactory;
        this.generalEntityFactory = generalEntityFactory;
        this.contactInfoDao = contactInfoDao;
        this.userProfileDao = userProfileDao;
        this.orderItemDao = orderItemDao;
        this.entityCachedMap = entityCachedMap;
    }

    @Override
    public void saveContactInfo(ContactInfoDto contactInfoDto) {
        ContactInfo contactInfo = buildContactInfo(contactInfoDto);
        GeneralEntity generalEntity = generalEntityFactory.getOneGeneralEntity(contactInfo);
        contactInfoDao.saveGeneralEntity(generalEntity);
    }

    @Override
    public void saveContactInfoWithResponse(ContactInfoDto contactInfoDto) {
        ContactInfo contactInfo = buildContactInfo(contactInfoDto);
        GeneralEntity generalEntity = generalEntityFactory.getOneGeneralEntity(contactInfo);
        contactInfoDao.saveGeneralEntity(generalEntity);
    }

    @Override
    public void updateContactInfoByOrderId(ContactInfoDtoUpdate requestContactInfoDtoUpdate) {
        ContactInfoDto contactInfoDto = requestContactInfoDtoUpdate.getNewEntityDto();
        ContactInfo newContactInfo = buildContactInfo(contactInfoDto);

        Parameter parameter = parameterFactory
                .createStringParameter("orderId", requestContactInfoDtoUpdate.getSearchField());
        Consumer<Session> consumer = session -> {
            OrderItem orderItem = contactInfoDao.getGeneralEntity(OrderItem.class, parameter);
            ContactInfo contactInfo = orderItem.getBeneficiaries().get(0);
            newContactInfo.setId(contactInfo.getId());
            session.merge(newContactInfo);
        };
        contactInfoDao.updateGeneralEntity(consumer);
    }

    @Override
    public void updateContactInfoByUsername(ContactInfoDtoUpdate requestContactInfoDtoUpdate) {
        ContactInfoDto contactInfoDto = requestContactInfoDtoUpdate.getNewEntityDto();
        ContactInfo newContactInfo = buildContactInfo(contactInfoDto);

        Parameter parameter = parameterFactory
                .createStringParameter("username", requestContactInfoDtoUpdate.getSearchField());
        Consumer<Session> consumer = session -> {
            UserProfile userProfile = contactInfoDao.getGeneralEntity(UserProfile.class, parameter);
            ContactInfo contactInfo = userProfile.getContactInfo();
            newContactInfo.setId(contactInfo.getId());
            session.merge(newContactInfo);
        };
        contactInfoDao.updateGeneralEntity(consumer);
    }

    @Override
    public void deleteContactInfoByOrderId(OneFieldEntityDto oneFieldEntityDto) {
        Parameter parameter = parameterFactory
                .createStringParameter("orderId", oneFieldEntityDto.getValue());
        Supplier<ContactInfo> supplier = () -> {
            OrderItem orderItem = orderItemDao.getGeneralEntity(OrderItem.class, parameter);
            return orderItem.getBeneficiaries().get(0);
        };
//        contactInfoDao.deleteGeneralEntity(supplier);
    }

    @Override
    public void deleteContactInfoByUsername(OneFieldEntityDto oneFieldEntityDto) {
        Parameter parameter = parameterFactory
                .createStringParameter("username", oneFieldEntityDto.getValue());
//        Consumer<Session> consumer = session -> {
//            UserProfile userProfile = userProfileDao.getGeneralEntity(UserProfile.class, parameter);
//            ContactInfo contactInfo = userProfile.getContactInfo();
//            Parameter contactInfoParameter = parameterFactory
//                    .createLongParameter("id", contactInfo.getId());
//            contactInfoDao.deleteGeneralEntity(contactInfoParameter);
//        };
//        contactInfoDao.deleteGeneralEntity(consumer);
        Supplier<ContactInfo> supplier = () -> {
            UserProfile userProfile = userProfileDao.getGeneralEntity(UserProfile.class, parameter);
            return userProfile.getContactInfo();
        };
//        contactInfoDao.deleteGeneralEntity(supplier);
    }

    private ContactInfo buildContactInfo(ContactInfoDto contactInfoDto) {
        CountryPhoneCode countryPhoneCode = entityCachedMap.getEntity(
                CountryPhoneCode.class,
                "code",
                contactInfoDto.getCountryPhoneCode());
        ContactPhone contactPhone = ContactPhone.builder()
                .phoneNumber(contactInfoDto.getPhoneNumber())
                .countryPhoneCode(countryPhoneCode)
                .build();
        return ContactInfo.builder()
                .name(contactInfoDto.getName())
                .secondName(contactInfoDto.getSecondName())
                .contactPhone(contactPhone)
                .build();
    }

}

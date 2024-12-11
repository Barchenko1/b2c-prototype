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
import com.b2c.prototype.service.common.EntityOperationDao;
import com.b2c.prototype.service.common.IEntityOperationDao;
import com.b2c.prototype.service.processor.query.IQueryService;
import com.b2c.prototype.service.processor.userprofile.IContactInfoService;
import com.tm.core.processor.finder.factory.IParameterFactory;
import com.tm.core.processor.finder.parameter.Parameter;
import org.hibernate.Session;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

public class ContactInfoService implements IContactInfoService {
    private static final Logger LOGGER = LoggerFactory.getLogger(ContactInfoService.class);

    private final IParameterFactory parameterFactory;
    private final IEntityOperationDao entityOperationDao;
    private final IQueryService queryService;
    private final IEntityCachedMap entityCachedMap;

    public ContactInfoService(IParameterFactory parameterFactory,
                              IContactInfoDao contactInfoDao,
                              IQueryService queryService,
                              IEntityCachedMap entityCachedMap) {
        this.parameterFactory = parameterFactory;
        this.entityOperationDao = new EntityOperationDao(contactInfoDao);
        this.queryService = queryService;
        this.entityCachedMap = entityCachedMap;
    }

    @Override
    public void saveContactInfo(ContactInfoDto contactInfoDto) {
        entityOperationDao.saveEntity(mapToEntityFunction().apply(contactInfoDto));
    }

    @Override
    public void updateContactInfoByOrderId(ContactInfoDtoUpdate requestContactInfoDtoUpdate) {
        Consumer<Session> consumer = session -> {
            OrderItem orderItem = queryService.getEntity(
                    OrderItem.class,
                    orderIdParameterSupplier(requestContactInfoDtoUpdate.getSearchField()));
            List<ContactInfo> contactInfo = orderItem.getBeneficiaries();
            List<ContactInfo> newContactInfoList = Arrays.stream(requestContactInfoDtoUpdate.getNewEntityDto())
                    .map(contactInfoDto -> mapToEntityFunction().apply(contactInfoDto))
                    .toList();
            for (int i = 0; i < newContactInfoList.size(); i++) {
                if (i < contactInfo.size()) {
                    ContactInfo existingContact = contactInfo.get(i);
                    ContactInfo newContact = newContactInfoList.get(i);
                    newContact.setId(existingContact.getId());
                } else {
                    contactInfo.add(newContactInfoList.get(i));
                }
            }
            if (contactInfo.size() > newContactInfoList.size()) {
                contactInfo.subList(newContactInfoList.size(), contactInfo.size()).clear();
            }

            orderItem.setBeneficiaries(contactInfo);
            session.merge(orderItem);
        };
        entityOperationDao.updateEntity(consumer);
    }

    @Override
    public void updateContactInfoByEmail(ContactInfoDtoUpdate requestContactInfoDtoUpdate) {
        Consumer<Session> consumer = session -> {
            UserProfile userProfile = queryService.getEntity(
                    UserProfile.class,
                    emailParameterSupplier(requestContactInfoDtoUpdate.getSearchField()));
            ContactInfo contactInfo = userProfile.getContactInfo();
            ContactInfo newContactInfo = mapToEntityFunction().apply(requestContactInfoDtoUpdate.getNewEntityDto()[0]);
            newContactInfo.setId(contactInfo.getId());
            session.merge(newContactInfo);
        };
        entityOperationDao.updateEntity(consumer);
    }

    @Override
    public void deleteContactInfoByOrderId(OneFieldEntityDto oneFieldEntityDto) {
        entityOperationDao.deleteEntity(orderIdParameterSupplier(oneFieldEntityDto.getValue()));
    }

    @Override
    public void deleteContactInfoByEmail(OneFieldEntityDto oneFieldEntityDto) {
        entityOperationDao.deleteEntity(emailParameterSupplier(oneFieldEntityDto.getValue()));
    }

    private Supplier<Parameter> orderIdParameterSupplier(String value) {
        return () -> parameterFactory.createStringParameter("orderId", value);
    }

    private Supplier<Parameter> emailParameterSupplier(String value) {
        return () -> parameterFactory.createStringParameter("email", value);
    }

    private Function<ContactInfoDto, ContactInfo> mapToEntityFunction() {
        return contactInfoDto -> {
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
        };
    }

}

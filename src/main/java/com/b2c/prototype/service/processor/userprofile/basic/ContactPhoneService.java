package com.b2c.prototype.service.processor.userprofile.basic;

import com.b2c.prototype.dao.cashed.IEntityCachedMap;
import com.b2c.prototype.dao.order.IOrderItemDao;
import com.b2c.prototype.dao.user.IContactPhoneDao;
import com.b2c.prototype.dao.user.IUserProfileDao;
import com.b2c.prototype.modal.dto.common.OneFieldEntityDto;
import com.b2c.prototype.modal.dto.request.ContactPhoneDto;
import com.b2c.prototype.modal.dto.update.ContactPhoneDtoUpdate;
import com.b2c.prototype.modal.entity.order.OrderItem;
import com.b2c.prototype.modal.entity.user.ContactPhone;
import com.b2c.prototype.modal.entity.user.CountryPhoneCode;
import com.b2c.prototype.modal.entity.user.UserProfile;
import com.b2c.prototype.service.processor.query.IQueryService;
import com.b2c.prototype.service.processor.userprofile.IContactPhoneService;
import com.b2c.prototype.service.common.EntityOperationDao;
import com.b2c.prototype.service.common.IEntityOperationDao;
import com.tm.core.processor.finder.factory.IParameterFactory;
import com.tm.core.processor.finder.parameter.Parameter;

import java.util.List;
import java.util.function.Function;
import java.util.function.Supplier;

public class ContactPhoneService implements IContactPhoneService {

    private final IParameterFactory parameterFactory;
    private final IEntityOperationDao entityOperationDao;
    private final IUserProfileDao userProfileDao;
    private final IOrderItemDao orderItemDao;
    private final IQueryService queryService;
    private final IEntityCachedMap entityCachedMap;

    public ContactPhoneService(IParameterFactory parameterFactory,
                               IContactPhoneDao contactPhoneDao,
                               IUserProfileDao userProfileDao,
                               IOrderItemDao orderItemDao,
                               IQueryService queryService,
                               IEntityCachedMap entityCachedMap) {
        this.parameterFactory = parameterFactory;
        this.entityOperationDao = new EntityOperationDao(contactPhoneDao);
        this.userProfileDao = userProfileDao;
        this.orderItemDao = orderItemDao;
        this.queryService = queryService;
        this.entityCachedMap = entityCachedMap;
    }

    @Override
    public void saveContactPhone(ContactPhoneDto contactPhoneDto) {
        entityOperationDao.saveEntity(contactPhoneSupplier(contactPhoneDto));
    }

    @Override
    public void updateContactPhoneByEmail(ContactPhoneDtoUpdate contactPhoneDtoUpdate) {
        entityOperationDao.updateEntity(session -> {
            UserProfile userProfile = userProfileDao.getEntity(
                    emailParameterSupplier(contactPhoneDtoUpdate.getSearchField()).get());
            ContactPhone newContactPhone = mapToEntityFunction().apply(contactPhoneDtoUpdate.getNewEntityDto());
            newContactPhone.setId(userProfile.getContactInfo().getContactPhone().getId());
            session.merge(newContactPhone);
        });
    }

    @Override
    public void updateContactPhoneByOrderId(ContactPhoneDtoUpdate contactPhoneDtoUpdate) {
        entityOperationDao.updateEntity(session -> {
            OrderItem orderItem = orderItemDao.getEntity(
                    orderIdParameterSupplier(contactPhoneDtoUpdate.getSearchField()).get());
            ContactPhone newContactPhone = mapToEntityFunction().apply(contactPhoneDtoUpdate.getNewEntityDto());
            newContactPhone.setId(orderItem.getBeneficiaries().get(0).getContactPhone().getId());
            session.merge(newContactPhone);
        });
    }

    @Override
    public void deleteContactPhoneByEmail(OneFieldEntityDto oneFieldEntityDto) {
        entityOperationDao.deleteEntityByParameter(
                emailParameterSupplier(oneFieldEntityDto.getValue()));
    }

    @Override
    public void deleteContactPhoneByOrderId(OneFieldEntityDto oneFieldEntityDto) {
        entityOperationDao.deleteEntityByParameter(
                orderIdParameterSupplier(oneFieldEntityDto.getValue()));
    }

    @Override
    public ContactPhoneDto getContactPhoneByEmail(OneFieldEntityDto oneFieldEntityDto) {
        return queryService.getEntityDto(
                UserProfile.class,
                emailParameterSupplier(oneFieldEntityDto.getValue()),
                mapUserProfileToDtoFunction());
    }

    @Override
    public ContactPhoneDto getContactPhoneByOrderId(OneFieldEntityDto oneFieldEntityDto) {
        return queryService.getEntityDto(
                OrderItem.class,
                orderIdParameterSupplier(oneFieldEntityDto.getValue()),
                mapOrderItemToDtoFunction());
    }

    @Override
    public List<ContactPhoneDto> getContactPhoneList() {
        return entityOperationDao.getEntityDtoList(mapToDtoFunction());
    }

    private Function<ContactPhoneDto, ContactPhone> mapToEntityFunction() {
        return (contactPhoneDto) -> {
            CountryPhoneCode countryPhoneCode = entityCachedMap.getEntity(
                    CountryPhoneCode.class,
                    "code",
                    contactPhoneDto.getCountryPhoneCode());
            return ContactPhone.builder()
                    .phoneNumber(contactPhoneDto.getPhoneNumber())
                    .countryPhoneCode(countryPhoneCode)
                    .build();
        };
    }

    private Function<UserProfile, ContactPhoneDto> mapUserProfileToDtoFunction() {
        return (userProfile) -> {
            ContactPhone contactPhone = userProfile.getContactInfo().getContactPhone();
            return mapToDtoFunction().apply(contactPhone);
        };
    }

    private Function<OrderItem, ContactPhoneDto> mapOrderItemToDtoFunction() {
        return (orderItem) -> {
            ContactPhone contactPhone = orderItem.getBeneficiaries().get(0).getContactPhone();
            return mapToDtoFunction().apply(contactPhone);
        };
    }

    private Function<ContactPhone, ContactPhoneDto> mapToDtoFunction() {
        return (contactPhone) -> ContactPhoneDto.builder()
                .phoneNumber(contactPhone.getPhoneNumber())
                .countryPhoneCode(contactPhone.getCountryPhoneCode().getCode())
                .build();
    }

    private Supplier<ContactPhone> contactPhoneSupplier(ContactPhoneDto contactPhoneDto) {
        return () -> mapToEntityFunction().apply(contactPhoneDto);
    }

    private Supplier<Parameter> orderIdParameterSupplier(String value) {
        return () -> parameterFactory.createStringParameter(
                "order_id", value
        );
    }

    private Supplier<Parameter> emailParameterSupplier(String value) {
        return () -> parameterFactory.createStringParameter(
                "email", value
        );
    }
}

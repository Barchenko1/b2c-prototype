package com.b2c.prototype.service.processor.address.base;

import com.b2c.prototype.dao.address.IAddressDao;
import com.b2c.prototype.dao.cashed.IEntityCachedMap;
import com.b2c.prototype.modal.dto.common.OneFieldEntityDto;
import com.b2c.prototype.modal.dto.request.AddressDto;
import com.b2c.prototype.modal.dto.update.AddressDtoUpdate;
import com.b2c.prototype.modal.entity.address.Address;
import com.b2c.prototype.modal.entity.address.Country;
import com.b2c.prototype.modal.entity.order.OrderItem;
import com.b2c.prototype.modal.entity.user.UserProfile;
import com.b2c.prototype.service.common.IEntityOperationDao;
import com.b2c.prototype.service.processor.address.IAddressService;
import com.b2c.prototype.service.processor.query.IQueryService;
import com.b2c.prototype.service.common.EntityOperationDao;
import com.tm.core.processor.finder.factory.IParameterFactory;
import com.tm.core.processor.finder.parameter.Parameter;

import java.util.List;
import java.util.function.Function;
import java.util.function.Supplier;

public class AddressService implements IAddressService {

    private final IParameterFactory parameterFactory;
    private final IEntityOperationDao entityOperationDao;
    private final IQueryService queryService;
    private final IEntityCachedMap entityCachedMap;

    public AddressService(IParameterFactory parameterFactory,
                          IAddressDao addressDao,
                          IQueryService queryService,
                          IEntityCachedMap entityCachedMap) {
        this.parameterFactory = parameterFactory;
        this.entityOperationDao = new EntityOperationDao(addressDao);
        this.queryService = queryService;
        this.entityCachedMap = entityCachedMap;
    }

    @Override
    public void saveAddress(AddressDto addressDto) {
        entityOperationDao.saveEntity(addressSupplier(addressDto));
    }

    @Override
    public void updateAppUserAddress(AddressDtoUpdate addressDtoUpdate) {
        entityOperationDao.updateEntity(session -> {
            UserProfile userProfile = queryService.getEntity(
                    UserProfile.class,
                    emailParameterSupplier(addressDtoUpdate.getSearchField()));
            Address newAddress = mapToEntityFunction().apply(addressDtoUpdate.getNewEntityDto());
            newAddress.setId(userProfile.getAddress().getId());
            session.merge(newAddress);
        });
    }

    @Override
    public void updateDeliveryAddress(AddressDtoUpdate addressDtoUpdate) {
        entityOperationDao.updateEntity(session -> {
            OrderItem orderItem = queryService.getEntity(
                    OrderItem.class,
                    orderIdParameterSupplier(addressDtoUpdate.getSearchField()));
            Address newAddress = mapToEntityFunction().apply(addressDtoUpdate.getNewEntityDto());
            newAddress.setId(orderItem.getDelivery().getAddress().getId());
            session.merge(newAddress);
        });
    }

    @Override
    public void deleteAppUserAddress(OneFieldEntityDto oneFieldEntityDto) {
        entityOperationDao.deleteEntityByParameter(
                emailParameterSupplier(oneFieldEntityDto.getValue()));
    }

    @Override
    public void deleteDeliveryAddress(OneFieldEntityDto oneFieldEntityDto) {
        entityOperationDao.deleteEntityByParameter(
                orderIdParameterSupplier(oneFieldEntityDto.getValue()));
    }

    @Override
    public AddressDto getAddressByEmail(OneFieldEntityDto oneFieldEntityDto) {
        return queryService.getEntityDto(
                UserProfile.class,
                emailParameterSupplier(oneFieldEntityDto.getValue()),
                userProfileMapToDtoFunction());
    }

    @Override
    public AddressDto getAddressByUsername(OneFieldEntityDto oneFieldEntityDto) {
        return queryService.getEntityDto(
                UserProfile.class,
                usernameParameterSupplier(oneFieldEntityDto.getValue()),
                userProfileMapToDtoFunction());
    }

    @Override
    public AddressDto getAddressByOrderId(OneFieldEntityDto oneFieldEntityDto) {
        return queryService.getEntityDto(
                OrderItem.class,
                orderIdParameterSupplier(oneFieldEntityDto.getValue()),
                orderItemMapToDtoFunction());
    }

    @Override
    public List<AddressDto> getAddresses() {
        return entityOperationDao.getEntityDtoList(mapToDtoFunction());
    }

    private Function<AddressDto, Address> mapToEntityFunction() {
        return (addressDto) -> Address.builder()
                .country(entityCachedMap.getEntity(
                        Country.class,
                        "value",
                        addressDto.getCountry()))
                .city(addressDto.getCity())
                .street(addressDto.getStreet())
                .street2(addressDto.getStreet2())
                .buildingNumber(addressDto.getBuildingNumber())
                .florNumber(addressDto.getFlorNumber())
                .apartmentNumber(addressDto.getApartmentNumber())
                .zipCode(addressDto.getZipCode())
                .build();
    }

    private Function<Address, AddressDto> mapToDtoFunction() {
        return (address) -> AddressDto.builder()
                .country(address.getCountry().getValue())
                .city(address.getCity())
                .street(address.getStreet())
                .street2(address.getStreet2())
                .buildingNumber(address.getBuildingNumber())
                .florNumber(address.getFlorNumber())
                .apartmentNumber(address.getApartmentNumber())
                .zipCode(address.getZipCode())
                .build();
    }

    private Function<UserProfile, AddressDto> userProfileMapToDtoFunction() {
        return (userProfile) -> {
            Address address = userProfile.getAddress();
            return mapToDtoFunction().apply(address);
        };
    }

    private Function<OrderItem, AddressDto> orderItemMapToDtoFunction() {
        return (orderItem) -> {
            Address address = orderItem.getDelivery().getAddress();
            return mapToDtoFunction().apply(address);
        };
    }

    private Supplier<Address> addressSupplier(AddressDto dto) {
        return () -> mapToEntityFunction().apply(dto);
    }

    private Supplier<Parameter> usernameParameterSupplier(String value) {
        return () -> parameterFactory.createStringParameter(
                "username", value
        );
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

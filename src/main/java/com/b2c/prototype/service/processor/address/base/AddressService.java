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
import com.b2c.prototype.service.processor.address.IAddressService;
import com.tm.core.dao.query.ISearchWrapper;
import com.tm.core.dao.transaction.ITransactionWrapper;
import com.tm.core.processor.finder.factory.IParameterFactory;
import com.tm.core.processor.finder.parameter.Parameter;
import org.hibernate.Session;

import java.util.function.Consumer;

public class AddressService implements IAddressService {

    private final IParameterFactory parameterFactory;
    private final ITransactionWrapper transactionWrapper;
    private final ISearchWrapper searchWrapper;
    private final IAddressDao addressDao;
    private final IEntityCachedMap entityCachedMap;

    public AddressService(IParameterFactory parameterFactory,
                          ITransactionWrapper transactionWrapper,
                          ISearchWrapper searchWrapper,
                          IAddressDao addressDao, IEntityCachedMap entityCachedMap) {
        this.parameterFactory = parameterFactory;
        this.transactionWrapper = transactionWrapper;
        this.searchWrapper = searchWrapper;
        this.addressDao = addressDao;
        this.entityCachedMap = entityCachedMap;
    }

    @Override
    public void saveAddress(AddressDto addressDto) {
        Address address = buildAddress(addressDto);
        addressDao.saveEntity(address);
    }

    @Override
    public void updateAppUserAddress(AddressDtoUpdate AddressDtoUpdate) {
        AddressDto newAddressDto = AddressDtoUpdate.getNewEntityDto();
        Address newAddress = buildAddress(newAddressDto);

        Parameter parameter = parameterFactory.createStringParameter("username",
                AddressDtoUpdate.getSearchField());
        Consumer<Session> consumer = session -> {
            UserProfile userProfile = (UserProfile) searchWrapper.getEntitySupplier(UserProfile.class, parameter).get();
            userProfile.setAddress(newAddress);
            session.persist(newAddress);
            session.merge(userProfile);
        };
        transactionWrapper.updateEntity(consumer);
    }

    @Override
    public void updateDeliveryAddress(AddressDtoUpdate addressDtoUpdate) {
        AddressDto newAddressDto = addressDtoUpdate.getNewEntityDto();
        Address newAddress = buildAddress(newAddressDto);

        Parameter parameter = parameterFactory.createStringParameter("order_id",
                addressDtoUpdate.getSearchField());
        Consumer<Session> consumer = session -> {
            UserProfile userProfile = (UserProfile) searchWrapper.getEntitySupplier(OrderItem.class, parameter).get();
            userProfile.setAddress(newAddress);
            session.persist(newAddress);
            session.merge(userProfile);
        };
        transactionWrapper.updateEntity(consumer);
    }

    @Override
    public void deleteAppUserAddress(OneFieldEntityDto oneFieldEntityDto) {
        Parameter parameter = parameterFactory.createStringParameter("username",
                oneFieldEntityDto.getValue());
        addressDao.findEntityAndDelete(parameter);
    }

    @Override
    public void deleteDeliveryAddress(OneFieldEntityDto oneFieldEntityDto) {
        Parameter parameter = parameterFactory.createStringParameter("order_id",
                oneFieldEntityDto.getValue());
        addressDao.findEntityAndDelete(parameter);
    }

    @Override
    public AddressDto getAddressByEmail(String email) {
        Parameter parameter = parameterFactory.createStringParameter("email", email);
        UserProfile userProfile = (UserProfile) searchWrapper.getEntitySupplier(UserProfile.class, parameter).get();
        return buildAddressDto(userProfile.getAddress());
    }

    @Override
    public AddressDto getAddressByUsername(String username) {
        Parameter parameter = parameterFactory.createStringParameter("username", username);
        UserProfile userProfile = (UserProfile) searchWrapper.getEntitySupplier(UserProfile.class, parameter).get();
        return buildAddressDto(userProfile.getAddress());
    }

    @Override
    public AddressDto getAddressByOrderId(String orderId) {
        Parameter parameter = parameterFactory.createStringParameter("orderId", orderId);
        OrderItem orderItem = (OrderItem) searchWrapper.getEntitySupplier(OrderItem.class, parameter).get();
        return buildAddressDto(orderItem.getDelivery().getAddress());
    }

    private Address buildAddress(AddressDto addressDto) {
        Country country = entityCachedMap.getEntity(Country.class, "value", addressDto.getCountry());
        return Address.builder()
                .country(country)
                .street(addressDto.getStreet())
                .street2(addressDto.getStreet2())
                .buildingNumber(addressDto.getBuildingNumber())
                .flor(addressDto.getFlor())
                .apartmentNumber(addressDto.getApartmentNumber())
                .build();
    }

    private AddressDto buildAddressDto(Address address) {
        return AddressDto.builder()
                .country(address.getCountry().getValue())
                .street(address.getStreet())
                .street2(address.getStreet2())
                .buildingNumber(address.getBuildingNumber())
                .flor(address.getFlor())
                .apartmentNumber(address.getApartmentNumber())
                .build();
    }
}

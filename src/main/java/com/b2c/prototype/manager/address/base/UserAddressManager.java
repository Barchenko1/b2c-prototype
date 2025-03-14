package com.b2c.prototype.manager.address.base;

import com.b2c.prototype.dao.address.IAddressDao;
import com.b2c.prototype.modal.dto.payload.AddressDto;
import com.b2c.prototype.modal.dto.payload.UserAddressDto;
import com.b2c.prototype.modal.dto.response.ResponseCreditCardDto;
import com.b2c.prototype.modal.dto.response.ResponseUserAddressDto;
import com.b2c.prototype.modal.dto.response.ResponseUserCreditCardDto;
import com.b2c.prototype.modal.entity.address.Address;
import com.b2c.prototype.modal.entity.address.UserAddress;
import com.b2c.prototype.modal.entity.payment.CreditCard;
import com.b2c.prototype.modal.entity.user.UserCreditCard;
import com.b2c.prototype.modal.entity.user.UserDetails;
import com.b2c.prototype.service.function.ITransformationFunctionService;
import com.b2c.prototype.manager.address.IUserAddressManager;
import com.b2c.prototype.service.query.ISearchService;
import com.tm.core.finder.factory.IParameterFactory;
import com.tm.core.process.manager.common.EntityOperationManager;
import com.tm.core.process.manager.common.IEntityOperationManager;

import java.util.Comparator;
import java.util.List;

import static com.b2c.prototype.util.Constant.USER_ID;

public class UserAddressManager implements IUserAddressManager {

    private final IEntityOperationManager entityOperationManager;
    private final ISearchService searchService;
    private final ITransformationFunctionService transformationFunctionService;
    private final IParameterFactory parameterFactory;

    public UserAddressManager(IAddressDao addressDao,
                              ISearchService searchService,
                              ITransformationFunctionService transformationFunctionService,
                              IParameterFactory parameterFactory) {
        this.entityOperationManager = new EntityOperationManager(addressDao);
        this.searchService = searchService;
        this.transformationFunctionService = transformationFunctionService;
        this.parameterFactory = parameterFactory;
    }

    @Override
    public void saveUserAddress(String userId, UserAddressDto userAddressDto) {
        entityOperationManager.executeConsumer(session -> {
            UserDetails userDetails = searchService.getNamedQueryEntity(
                    UserDetails.class,
                    "UserDetails.findAddressesByUserId",
                    parameterFactory.createStringParameter(USER_ID, userId));
            UserAddress newUserAddress = transformationFunctionService.getEntity(session, UserAddress.class, userAddressDto);
            boolean isAllAddressesFalse = userDetails.getUserAddresses().stream()
                    .noneMatch(UserAddress::isDefault);

            if (userDetails.getUserAddresses().isEmpty() || isAllAddressesFalse) {
                newUserAddress.setDefault(true);
            }
            userDetails.getUserAddresses().add(newUserAddress);
            session.merge(userDetails);
        });
    }

    @Override
    public void updateUserAddress(String userId, String addressId, UserAddressDto userAddressDto) {
        entityOperationManager.executeConsumer(session -> {
            UserDetails userDetails = searchService.getNamedQueryEntity(
                    UserDetails.class,
                    "UserDetails.findAddressesByUserId",
                    parameterFactory.createStringParameter(USER_ID, userId));
            UserAddress newUserAddress = transformationFunctionService.getEntity(session, UserAddress.class, userAddressDto);
            boolean isAllAddressesFalse = userDetails.getUserAddresses().stream()
                    .noneMatch(UserAddress::isDefault);

            if (userDetails.getUserAddresses().isEmpty() || isAllAddressesFalse) {
                newUserAddress.setDefault(true);
            }
            if (newUserAddress.isDefault()) {
                userDetails.getUserAddresses().forEach(userAddress -> {
                    userAddress.setDefault(false);
                });
            }

            UserAddress existingUserAddress = userDetails.getUserAddresses().stream()
                    .filter(address -> addressId.equals(address.getUserAddressCombination()))
                    .findFirst()
                    .orElseThrow(() -> new RuntimeException("Credit Card not found"));
            existingUserAddress.setDefault(newUserAddress.isDefault());
            existingUserAddress.setUserAddressCombination(newUserAddress.getUserAddressCombination());
            existingUserAddress.getAddress().setCountry(newUserAddress.getAddress().getCountry());
            existingUserAddress.getAddress().setCity(newUserAddress.getAddress().getCity());
            existingUserAddress.getAddress().setStreet(newUserAddress.getAddress().getStreet());
            existingUserAddress.getAddress().setBuildingNumber(newUserAddress.getAddress().getBuildingNumber());
            existingUserAddress.getAddress().setApartmentNumber(newUserAddress.getAddress().getApartmentNumber());
            existingUserAddress.getAddress().setFlorNumber(newUserAddress.getAddress().getFlorNumber());
            existingUserAddress.getAddress().setZipCode(newUserAddress.getAddress().getZipCode());

            if (userDetails.getUserAddresses().stream().noneMatch(UserAddress::isDefault)) {
                existingUserAddress.setDefault(true);
            }

            session.merge(userDetails);
        });
    }

    @Override
    public void setDefaultUserCreditCard(String userId, String addressId) {
        entityOperationManager.executeConsumer(session -> {
            UserDetails userDetails = searchService.getNamedQueryEntity(
                    UserDetails.class,
                    "UserDetails.findAddressesByUserId",
                    parameterFactory.createStringParameter(USER_ID, userId));

            UserAddress userAddress = userDetails.getUserAddresses().stream()
                    .filter(uAddress -> addressId.equals(uAddress.getUserAddressCombination()))
                    .findFirst()
                    .orElseThrow(() -> new RuntimeException("Credit Card not found"));
            userDetails.getUserAddresses().stream()
                    .filter(card -> !userAddress.getUserAddressCombination().equals(card.getUserAddressCombination()))
                    .forEach(card -> card.setDefault(false));

            userAddress.setDefault(true);
            session.merge(userDetails);
        });
    }

    @Override
    public void deleteUserAddress(String userId, String addressId) {
        entityOperationManager.executeConsumer(session -> {
            UserDetails userDetails = searchService.getNamedQueryEntity(
                    UserDetails.class,
                    "UserDetails.findAddressesByUserId",
                    parameterFactory.createStringParameter(USER_ID, userId));
            UserAddress userAddress = userDetails.getUserAddresses().stream()
                    .filter(existingUserCreditCard -> addressId.equals(existingUserCreditCard.getUserAddressCombination()))
                    .findFirst()
                    .orElseThrow(() ->new RuntimeException("User has no such credit card"));
            userDetails.getUserAddresses().remove(userAddress);
            if (userAddress.isDefault()) {
                userDetails.getUserAddresses().stream()
                        .filter(address -> !address.equals(userAddress))
                        .max(Comparator.comparing(UserAddress::getUserAddressCombination))
                        .ifPresent(lastUserAddress -> lastUserAddress.setDefault(true));
            }

            session.merge(userDetails);

        });
    }

    @Override
    public List<ResponseUserAddressDto> getUserAddressesByUserId(String userId) {
        UserDetails userDetails = searchService.getNamedQueryEntity(
                UserDetails.class,
                "UserDetails.findAddressesByUserId",
                parameterFactory.createStringParameter(USER_ID, userId));

        return userDetails.getUserAddresses().stream()
                .map(transformationFunctionService.getTransformationFunction(UserAddress.class, ResponseUserAddressDto.class))
                .toList();
    }

    @Override
    public ResponseUserAddressDto getDefaultUserAddress(String userId) {
        UserDetails userDetails = searchService.getNamedQueryEntity(
                UserDetails.class,
                "UserDetails.findAddressesByUserId",
                parameterFactory.createStringParameter(USER_ID, userId));

        return userDetails.getUserAddresses().stream()
                .filter(UserAddress::isDefault)
                .map(transformationFunctionService.getTransformationFunction(UserAddress.class, ResponseUserAddressDto.class))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("User has no such default address"));
    }

    @Override
    public List<AddressDto> getAllAddressesByAddressId(String addressId) {
        return searchService.getSubNamedQueryEntityDtoList(
                UserAddress.class,
                "UserAddress.findByUserAddressCombination",
                parameterFactory.createStringParameter("userAddressCombination", addressId),
                transformationFunctionService.getTransformationFunction(UserAddress.class, AddressDto.class));
    }

}

package com.b2c.prototype.manager.address.base;

import com.b2c.prototype.dao.address.IAddressDao;
import com.b2c.prototype.modal.dto.payload.AddressDto;
import com.b2c.prototype.modal.dto.response.ResponseUserAddressDto;
import com.b2c.prototype.modal.entity.address.Address;
import com.b2c.prototype.modal.entity.delivery.Delivery;
import com.b2c.prototype.modal.entity.order.OrderArticularItemQuantity;
import com.b2c.prototype.modal.entity.user.UserDetails;
import com.b2c.prototype.service.function.ITransformationFunctionService;
import com.b2c.prototype.manager.address.IAddressManager;
import com.b2c.prototype.service.query.ISearchService;
import com.b2c.prototype.service.supplier.ISupplierService;
import com.tm.core.finder.factory.IParameterFactory;
import com.tm.core.process.manager.common.EntityOperationManager;
import com.tm.core.process.manager.common.IEntityOperationManager;

import java.util.List;
import java.util.Set;

import static com.b2c.prototype.util.Constant.ORDER_ID;
import static com.b2c.prototype.util.Constant.USER_ID;

public class AddressManager implements IAddressManager {

    private final IEntityOperationManager entityOperationDao;
    private final ISearchService searchService;
    private final ITransformationFunctionService transformationFunctionService;
    private final ISupplierService supplierService;
    private final IParameterFactory parameterFactory;

    public AddressManager(IAddressDao addressDao,
                          ISearchService searchService,
                          ITransformationFunctionService transformationFunctionService,
                          ISupplierService supplierService,
                          IParameterFactory parameterFactory) {
        this.entityOperationDao = new EntityOperationManager(addressDao);
        this.searchService = searchService;
        this.transformationFunctionService = transformationFunctionService;
        this.supplierService = supplierService;
        this.parameterFactory = parameterFactory;
    }

    @Override
    public void saveUpdateAppUserAddress(String userId, AddressDto addressDto) {
        entityOperationDao.executeConsumer(session -> {
            UserDetails userDetails = searchService.getNamedQueryEntity(
                    UserDetails.class,
                    "",
                    parameterFactory.createStringParameter(USER_ID, userId));
            Address newAddress = transformationFunctionService.getEntity(Address.class, addressDto);
//            userDetails.setUserAddresses(Set.of(newAddress));
            session.merge(userDetails);
        });
    }

    @Override
    public void saveUpdateDeliveryAddress(String orderId, AddressDto addressDto) {
        entityOperationDao.executeConsumer(session -> {
            OrderArticularItemQuantity orderItemDataOption = searchService.getNamedQueryEntity(
                    OrderArticularItemQuantity.class,
                    "",
                    parameterFactory.createStringParameter(ORDER_ID, orderId));
            Address newAddress = transformationFunctionService
                    .getEntity(Address.class, addressDto);
            Delivery delivery = orderItemDataOption.getDelivery();
            delivery.setAddress(newAddress);
            session.merge(delivery);
        });
    }

    @Override
    public void deleteAppUserAddress(String userId) {
        entityOperationDao.deleteEntity(
                supplierService.entityFieldSupplier(
                        UserDetails.class,
                        "",
                        supplierService.parameterStringSupplier(USER_ID, userId),
                        transformationFunctionService.getTransformationFunction(UserDetails.class, Address.class)));
    }

    @Override
    public void deleteDeliveryAddress(String orderId) {
        entityOperationDao.deleteEntity(
                supplierService.entityFieldSupplier(
                        OrderArticularItemQuantity.class,
                        "",
                        supplierService.parameterStringSupplier(ORDER_ID, orderId),
                        transformationFunctionService.getTransformationFunction(OrderArticularItemQuantity.class, Address.class)));
    }

    @Override
    public List<ResponseUserAddressDto> getAddressesByUserId(String userId) {
        return searchService.getSubNamedQueryEntityDtoList(
                UserDetails.class,
                "",
                parameterFactory.createStringParameter(USER_ID, userId),
                transformationFunctionService.getTransformationFunction(UserDetails.class, ResponseUserAddressDto.class));
    }

    @Override
    public AddressDto getAddressByOrderId(String orderId) {
        return searchService.getNamedQueryEntityDto(
                OrderArticularItemQuantity.class,
                "",
                parameterFactory.createStringParameter(ORDER_ID, orderId),
                transformationFunctionService.getTransformationFunction(OrderArticularItemQuantity.class, AddressDto.class));
    }

    @Override
    public List<AddressDto> getAddresses() {
        return entityOperationDao.getNamedQueryEntityDtoList(
                "",
                transformationFunctionService.getTransformationFunction(Address.class, AddressDto.class));
    }

}

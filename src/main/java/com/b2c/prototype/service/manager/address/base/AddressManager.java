package com.b2c.prototype.service.manager.address.base;

import com.b2c.prototype.dao.address.IAddressDao;
import com.b2c.prototype.modal.dto.common.OneFieldEntityDto;
import com.b2c.prototype.modal.dto.payload.AddressDto;
import com.b2c.prototype.modal.dto.searchfield.AddressSearchFieldEntityDto;
import com.b2c.prototype.modal.entity.address.Address;
import com.b2c.prototype.modal.entity.delivery.Delivery;
import com.b2c.prototype.modal.entity.order.OrderItemData;
import com.b2c.prototype.modal.entity.user.UserProfile;
import com.b2c.prototype.service.common.IEntityOperationDao;
import com.b2c.prototype.service.function.ITransformationFunctionService;
import com.b2c.prototype.service.manager.address.IAddressManager;
import com.b2c.prototype.service.query.IQueryService;
import com.b2c.prototype.service.common.EntityOperationDao;
import com.b2c.prototype.service.supplier.ISupplierService;

import java.util.List;

import static com.b2c.prototype.util.Constant.ORDER_ID;
import static com.b2c.prototype.util.Constant.USER_ID;

public class AddressManager implements IAddressManager {

    private final IEntityOperationDao entityOperationDao;
    private final IQueryService queryService;
    private final ITransformationFunctionService transformationFunctionService;
    private final ISupplierService supplierService;

    public AddressManager(IAddressDao addressDao,
                          IQueryService queryService,
                          ITransformationFunctionService transformationFunctionService,
                          ISupplierService supplierService) {
        this.entityOperationDao = new EntityOperationDao(addressDao);
        this.queryService = queryService;
        this.transformationFunctionService = transformationFunctionService;
        this.supplierService = supplierService;
    }

    @Override
    public void saveUpdateAppUserAddress(AddressSearchFieldEntityDto addressSearchFieldEntityDto) {
        entityOperationDao.executeConsumer(session -> {
            UserProfile userProfile = queryService.getEntity(
                    UserProfile.class,
                    supplierService.parameterStringSupplier(USER_ID, addressSearchFieldEntityDto.getSearchField()));
            Address newAddress = transformationFunctionService
                    .getEntity(Address.class, addressSearchFieldEntityDto.getNewEntity());
            userProfile.setAddress(newAddress);
            session.merge(userProfile);
        });
    }

    @Override
    public void saveUpdateDeliveryAddress(AddressSearchFieldEntityDto addressSearchFieldEntityDto) {
        entityOperationDao.executeConsumer(session -> {
            OrderItemData orderItemData = queryService.getEntity(
                    OrderItemData.class,
                    supplierService.parameterStringSupplier(ORDER_ID, addressSearchFieldEntityDto.getSearchField()));
            Address newAddress = transformationFunctionService
                    .getEntity(Address.class, addressSearchFieldEntityDto.getNewEntity());
            Delivery delivery = orderItemData.getDelivery();
            delivery.setAddress(newAddress);
            session.merge(delivery);
        });
    }

    @Override
    public void deleteAppUserAddress(OneFieldEntityDto oneFieldEntityDto) {
        entityOperationDao.deleteEntity(
                supplierService.entityFieldSupplier(
                        UserProfile.class,
                        supplierService.parameterStringSupplier(USER_ID, oneFieldEntityDto.getValue()),
                        transformationFunctionService.getTransformationFunction(UserProfile.class, Address.class)));
    }

    @Override
    public void deleteDeliveryAddress(OneFieldEntityDto oneFieldEntityDto) {
        entityOperationDao.deleteEntity(
                supplierService.entityFieldSupplier(
                        OrderItemData.class,
                        supplierService.parameterStringSupplier(ORDER_ID, oneFieldEntityDto.getValue()),
                        transformationFunctionService.getTransformationFunction(OrderItemData.class, Address.class)));
    }

    @Override
    public AddressDto getAddressByUserId(OneFieldEntityDto oneFieldEntityDto) {
        return queryService.getEntityDto(
                UserProfile.class,
                supplierService.parameterStringSupplier(USER_ID, oneFieldEntityDto.getValue()),
                transformationFunctionService.getTransformationFunction(UserProfile.class, AddressDto.class));
    }

    @Override
    public AddressDto getAddressByOrderId(OneFieldEntityDto oneFieldEntityDto) {
        return queryService.getEntityDto(
                OrderItemData.class,
                supplierService.parameterStringSupplier(ORDER_ID, oneFieldEntityDto.getValue()),
                transformationFunctionService.getTransformationFunction(OrderItemData.class, AddressDto.class));
    }

    @Override
    public List<AddressDto> getAddresses() {
        return entityOperationDao.getEntityDtoList("",
                transformationFunctionService.getTransformationFunction(Address.class, AddressDto.class));
    }

}

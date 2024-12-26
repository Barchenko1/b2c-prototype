package com.b2c.prototype.service.processor.address.base;

import com.b2c.prototype.dao.address.IAddressDao;
import com.b2c.prototype.modal.dto.common.OneFieldEntityDto;
import com.b2c.prototype.modal.dto.request.AddressDto;
import com.b2c.prototype.modal.dto.update.AddressSearchFieldDto;
import com.b2c.prototype.modal.entity.address.Address;
import com.b2c.prototype.modal.entity.delivery.Delivery;
import com.b2c.prototype.modal.entity.order.OrderItemData;
import com.b2c.prototype.modal.entity.user.UserProfile;
import com.b2c.prototype.service.common.IEntityOperationDao;
import com.b2c.prototype.service.function.ITransformationFunctionService;
import com.b2c.prototype.service.processor.address.IAddressService;
import com.b2c.prototype.service.processor.query.IQueryService;
import com.b2c.prototype.service.common.EntityOperationDao;
import com.b2c.prototype.service.supplier.ISupplierService;

import java.util.List;

public class AddressService implements IAddressService {

    private final IEntityOperationDao entityOperationDao;
    private final IQueryService queryService;
    private final ITransformationFunctionService transformationFunctionService;
    private final ISupplierService supplierService;

    public AddressService(IAddressDao addressDao,
                          IQueryService queryService,
                          ITransformationFunctionService transformationFunctionService,
                          ISupplierService supplierService) {
        this.entityOperationDao = new EntityOperationDao(addressDao);
        this.queryService = queryService;
        this.transformationFunctionService = transformationFunctionService;
        this.supplierService = supplierService;
    }

    @Override
    public void saveUpdateAppUserAddress(AddressSearchFieldDto addressSearchFieldDto) {
        entityOperationDao.executeConsumer(session -> {
            UserProfile userProfile = queryService.getEntity(
                    UserProfile.class,
                    supplierService.parameterStringSupplier("user_id", addressSearchFieldDto.getSearchField()));
            Address newAddress = transformationFunctionService
                    .getEntity(Address.class, addressSearchFieldDto.getNewEntity());
            userProfile.setAddress(newAddress);
            session.merge(userProfile);
        });
    }

    @Override
    public void saveUpdateDeliveryAddress(AddressSearchFieldDto addressSearchFieldDto) {
        entityOperationDao.executeConsumer(session -> {
            OrderItemData orderItemData = queryService.getEntity(
                    OrderItemData.class,
                    supplierService.parameterStringSupplier("order_id", addressSearchFieldDto.getSearchField()));
            Address newAddress = transformationFunctionService
                    .getEntity(Address.class, addressSearchFieldDto.getNewEntity());
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
                        "user_id",
                        oneFieldEntityDto.getValue(),
                        transformationFunctionService.getTransformationFunction(UserProfile.class, Address.class)));
    }

    @Override
    public void deleteDeliveryAddress(OneFieldEntityDto oneFieldEntityDto) {
        entityOperationDao.deleteEntity(
                supplierService.entityFieldSupplier(
                        OrderItemData.class,
                        "order_id",
                        oneFieldEntityDto.getValue(),
                        transformationFunctionService.getTransformationFunction(OrderItemData.class, Address.class)));
    }

    @Override
    public AddressDto getAddressByUserId(OneFieldEntityDto oneFieldEntityDto) {
        return queryService.getEntityDto(
                UserProfile.class,
                supplierService.parameterStringSupplier("user_id", oneFieldEntityDto.getValue()),
                transformationFunctionService.getTransformationFunction(UserProfile.class, AddressDto.class));
    }

    @Override
    public AddressDto getAddressByOrderId(OneFieldEntityDto oneFieldEntityDto) {
        return queryService.getEntityDto(
                OrderItemData.class,
                supplierService.parameterStringSupplier("order_id", oneFieldEntityDto.getValue()),
                transformationFunctionService.getTransformationFunction(OrderItemData.class, AddressDto.class));
    }

    @Override
    public List<AddressDto> getAddresses() {
        return entityOperationDao.getEntityDtoList(
                transformationFunctionService.getTransformationFunction(Address.class, AddressDto.class));
    }

}

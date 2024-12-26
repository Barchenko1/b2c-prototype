package com.b2c.prototype.service.processor.order.base;

import com.b2c.prototype.dao.order.IOrderItemDao;
import com.b2c.prototype.modal.dto.common.OneFieldEntityDto;
import com.b2c.prototype.modal.dto.request.OrderItemDataDto;
import com.b2c.prototype.modal.dto.update.OrderItemDataDtoUpdate;
import com.b2c.prototype.modal.entity.order.OrderItemData;
import com.b2c.prototype.service.common.EntityOperationDao;
import com.b2c.prototype.service.common.IEntityOperationDao;
import com.b2c.prototype.service.function.ITransformationFunctionService;
import com.b2c.prototype.service.processor.order.IOrderItemDataService;
import com.b2c.prototype.service.supplier.ISupplierService;

import java.util.List;

import static com.b2c.prototype.util.UniqueIdUtil.getUUID;

public class OrderItemDataService implements IOrderItemDataService {

    private final IEntityOperationDao entityOperationDao;
    private final ITransformationFunctionService transformationFunctionService;
    private final ISupplierService supplierService;


    public OrderItemDataService(IOrderItemDao orderItemDao,
                            ITransformationFunctionService transformationFunctionService,
                            ISupplierService supplierService) {
        this.entityOperationDao = new EntityOperationDao(orderItemDao);
        this.transformationFunctionService = transformationFunctionService;
        this.supplierService = supplierService;
    }

    @Override
    public void saveOrderItemData(OrderItemDataDto orderItemDataDto) {
        entityOperationDao.executeConsumer(session -> {
            OrderItemData orderItemData =
                    transformationFunctionService.getEntity(OrderItemData.class, orderItemDataDto);
            orderItemData.setOrderId(getUUID());
            session.merge(orderItemData);
        });
    }

    @Override
    public void updateOrderItemData(OrderItemDataDtoUpdate orderItemDataDtoUpdate) {
        entityOperationDao.executeConsumer(session -> {
            OrderItemData orderItemData = entityOperationDao.getEntity(
                    supplierService.parameterStringSupplier("order_id", orderItemDataDtoUpdate.getSearchField()));
            OrderItemData newOrderItemData =
                    transformationFunctionService.getEntity(OrderItemData.class, orderItemDataDtoUpdate.getNewEntity());
            newOrderItemData.setOrderId(orderItemData.getOrderId());
            newOrderItemData.setId(orderItemData.getId());
            session.merge(orderItemData);
        });
    }

    @Override
    public void deleteOrderItemData(OneFieldEntityDto oneFieldEntityDto) {
        entityOperationDao.deleteEntityByParameter(
                supplierService.parameterStringSupplier("order_id", oneFieldEntityDto.getValue()));
    }

    @Override
    public OrderItemDataDto getOrderItemData(OneFieldEntityDto oneFieldEntityDto) {
        return entityOperationDao.getEntityDto(
                supplierService.parameterStringSupplier("order_id", oneFieldEntityDto.getValue()),
                transformationFunctionService.getTransformationFunction(OrderItemData.class, OrderItemDataDto.class));
    }

    @Override
    public List<OrderItemDataDto> getOrderItemListData(OneFieldEntityDto oneFieldEntityDto) {
        return entityOperationDao.getEntityDtoList(
                transformationFunctionService.getTransformationFunction(OrderItemData.class, OrderItemDataDto.class));
    }
}

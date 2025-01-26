package com.b2c.prototype.service.processor.order.base;

import com.b2c.prototype.dao.order.IOrderItemDataDao;
import com.b2c.prototype.modal.dto.common.OneFieldEntityDto;
import com.b2c.prototype.modal.dto.payload.OrderItemDataDto;
import com.b2c.prototype.modal.dto.response.ResponseOrderItemDataDto;
import com.b2c.prototype.modal.dto.searchfield.OrderItemDataSearchFieldEntityDto;
import com.b2c.prototype.modal.entity.order.OrderItemData;
import com.b2c.prototype.service.common.EntityOperationDao;
import com.b2c.prototype.service.common.IEntityOperationDao;
import com.b2c.prototype.service.function.ITransformationFunctionService;
import com.b2c.prototype.service.processor.order.IOrderItemDataService;
import com.b2c.prototype.service.supplier.ISupplierService;

import java.util.List;

import static com.b2c.prototype.util.Constant.ORDER_ID;

public class OrderItemDataService implements IOrderItemDataService {

    private final IEntityOperationDao entityOperationDao;
    private final ITransformationFunctionService transformationFunctionService;
    private final ISupplierService supplierService;

    public OrderItemDataService(IOrderItemDataDao orderItemDao,
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
            session.merge(orderItemData);
        });
    }

    @Override
    public void updateOrderItemData(OrderItemDataSearchFieldEntityDto orderItemDataSearchFieldEntityDto) {
        entityOperationDao.executeConsumer(session -> {
            OrderItemData orderItemData = entityOperationDao.getEntity(
                    supplierService.parameterStringSupplier(ORDER_ID, orderItemDataSearchFieldEntityDto.getSearchField()));
            OrderItemData newOrderItemData =
                    transformationFunctionService.getEntity(OrderItemData.class, orderItemDataSearchFieldEntityDto.getNewEntity());
            newOrderItemData.setOrderId(orderItemData.getOrderId());
            newOrderItemData.setId(orderItemData.getId());
            session.merge(orderItemData);
        });
    }

    @Override
    public void deleteOrderItemData(OneFieldEntityDto oneFieldEntityDto) {
        entityOperationDao.deleteEntityByParameter(
                supplierService.parameterStringSupplier(ORDER_ID, oneFieldEntityDto.getValue()));
    }

    @Override
    public ResponseOrderItemDataDto getResponseOrderItemData(OneFieldEntityDto oneFieldEntityDto) {
        return entityOperationDao.getEntityDto("",
                supplierService.parameterStringSupplier(ORDER_ID, oneFieldEntityDto.getValue()),
                transformationFunctionService.getTransformationFunction(OrderItemData.class, ResponseOrderItemDataDto.class));
    }

    @Override
    public List<ResponseOrderItemDataDto> getResponseOrderItemDataList() {
        return entityOperationDao.getEntityDtoList("",
                transformationFunctionService.getTransformationFunction(OrderItemData.class, ResponseOrderItemDataDto.class));
    }
}

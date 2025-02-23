package com.b2c.prototype.manager.order.base;

import com.b2c.prototype.dao.order.IOrderItemDataDao;
import com.b2c.prototype.modal.dto.common.OneFieldEntityDto;
import com.b2c.prototype.modal.dto.payload.OrderItemDataDto;
import com.b2c.prototype.modal.dto.response.ResponseOrderItemDataDto;
import com.b2c.prototype.modal.dto.searchfield.OrderItemDataSearchFieldEntityDto;
import com.b2c.prototype.modal.entity.order.OrderArticularItem;
import com.b2c.prototype.service.common.EntityOperationManager;
import com.b2c.prototype.service.common.IEntityOperationManager;
import com.b2c.prototype.service.function.ITransformationFunctionService;
import com.b2c.prototype.manager.order.IOrderItemDataOptionManager;
import com.b2c.prototype.service.supplier.ISupplierService;
import com.tm.core.finder.factory.IParameterFactory;

import java.util.List;

import static com.b2c.prototype.util.Constant.ORDER_ID;

public class OrderItemDataOptionManager implements IOrderItemDataOptionManager {

    private final IEntityOperationManager entityOperationDao;
    private final ITransformationFunctionService transformationFunctionService;
    private final ISupplierService supplierService;
    private final IParameterFactory parameterFactory;

    public OrderItemDataOptionManager(IOrderItemDataDao orderItemDao,
                                      ITransformationFunctionService transformationFunctionService,
                                      ISupplierService supplierService,
                                      IParameterFactory parameterFactory) {
        this.entityOperationDao = new EntityOperationManager(orderItemDao);
        this.transformationFunctionService = transformationFunctionService;
        this.supplierService = supplierService;
        this.parameterFactory = parameterFactory;
    }

    @Override
    public void saveOrderItemData(OrderItemDataDto orderItemDataDto) {
        entityOperationDao.executeConsumer(session -> {
            OrderArticularItem orderItemDataOption =
                    transformationFunctionService.getEntity(OrderArticularItem.class, orderItemDataDto);
            session.merge(orderItemDataOption);
        });
    }

    @Override
    public void updateOrderItemData(OrderItemDataSearchFieldEntityDto orderItemDataSearchFieldEntityDto) {
        entityOperationDao.executeConsumer(session -> {
            OrderArticularItem orderItemDataOption = entityOperationDao.getEntity(
                    parameterFactory.createStringParameter(ORDER_ID, orderItemDataSearchFieldEntityDto.getSearchField()));
            OrderArticularItem newOrderItemDataOption =
                    transformationFunctionService.getEntity(OrderArticularItem.class, orderItemDataSearchFieldEntityDto.getNewEntity());
            newOrderItemDataOption.setOrderId(orderItemDataOption.getOrderId());
            newOrderItemDataOption.setId(orderItemDataOption.getId());
            session.merge(orderItemDataOption);
        });
    }

    @Override
    public void deleteOrderItemData(OneFieldEntityDto oneFieldEntityDto) {
        entityOperationDao.deleteEntityByParameter(
                supplierService.parameterStringSupplier(ORDER_ID, oneFieldEntityDto.getValue()));
    }

    @Override
    public ResponseOrderItemDataDto getResponseOrderItemData(OneFieldEntityDto oneFieldEntityDto) {
        return entityOperationDao.getEntityGraphDto(
                "",
                parameterFactory.createStringParameter(ORDER_ID, oneFieldEntityDto.getValue()),
                transformationFunctionService.getTransformationFunction(OrderArticularItem.class, ResponseOrderItemDataDto.class));
    }

    @Override
    public List<ResponseOrderItemDataDto> getResponseOrderItemDataList() {
        return entityOperationDao.getEntityGraphDtoList("",
                transformationFunctionService.getTransformationFunction(OrderArticularItem.class, ResponseOrderItemDataDto.class));
    }
}

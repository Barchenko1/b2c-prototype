package com.b2c.prototype.manager.payment.base;

import com.b2c.prototype.dao.payment.IPaymentDao;
import com.b2c.prototype.modal.dto.common.OneFieldEntityDto;
import com.b2c.prototype.modal.dto.payload.PaymentDto;
import com.b2c.prototype.modal.dto.searchfield.PaymentSearchFieldEntityDto;
import com.b2c.prototype.modal.entity.order.OrderArticularItem;
import com.b2c.prototype.modal.entity.payment.Payment;
import com.b2c.prototype.service.common.EntityOperationManager;
import com.b2c.prototype.service.common.IEntityOperationManager;
import com.b2c.prototype.service.function.ITransformationFunctionService;
import com.b2c.prototype.manager.payment.IPaymentManager;
import com.b2c.prototype.service.query.IQueryService;
import com.b2c.prototype.service.supplier.ISupplierService;

import java.util.List;

import static com.b2c.prototype.util.Constant.ORDER_ID;
import static com.b2c.prototype.util.Constant.PAYMENT_ID;
import static com.b2c.prototype.util.Util.getUUID;

public class PaymentManager implements IPaymentManager {

    private final IEntityOperationManager entityOperationDao;
    private final ITransformationFunctionService transformationFunctionService;
    private final IQueryService queryService;
    private final ISupplierService supplierService;

    public PaymentManager(IPaymentDao paymentDao,
                          IQueryService queryService,
                          ITransformationFunctionService transformationFunctionService,
                          ISupplierService supplierService) {
        this.entityOperationDao = new EntityOperationManager(paymentDao);
        this.queryService = queryService;
        this.transformationFunctionService = transformationFunctionService;
        this.supplierService = supplierService;
    }

    @Override
    public void saveUpdatePayment(PaymentSearchFieldEntityDto paymentSearchFieldEntityDto) {
        entityOperationDao.executeConsumer(session -> {
            OrderArticularItem orderItemDataOption = queryService.getEntity(
                    OrderArticularItem.class,
                    supplierService.parameterStringSupplier(ORDER_ID, paymentSearchFieldEntityDto.getSearchField()));
            PaymentDto paymentDto = paymentSearchFieldEntityDto.getNewEntity();
            Payment newPayment = transformationFunctionService.getEntity(
                    Payment.class,
                    paymentDto);
            Payment payment = orderItemDataOption.getPayment();
            if (payment != null) {
                newPayment.setPaymentId(payment.getPaymentId());
            } else {
                newPayment.setPaymentId(getUUID());
            }
            orderItemDataOption.setPayment(newPayment);
            session.merge(orderItemDataOption);
        });
    }

    @Override
    public void deletePaymentByOrderId(OneFieldEntityDto oneFieldEntityDto) {
        entityOperationDao.deleteEntity(
                supplierService.entityFieldSupplier(
                        OrderArticularItem.class,
                        supplierService.parameterStringSupplier(ORDER_ID, oneFieldEntityDto.getValue()),
                        transformationFunctionService.getTransformationFunction(OrderArticularItem.class, Payment.class)));
    }

    @Override
    public void deletePaymentByPaymentId(OneFieldEntityDto oneFieldEntityDto) {
        entityOperationDao.deleteEntity(
                supplierService.entityFieldSupplier(
                        Payment.class,
                        supplierService.parameterStringSupplier(PAYMENT_ID, oneFieldEntityDto.getValue())));
    }

    @Override
    public PaymentDto getPaymentByOrderId(OneFieldEntityDto oneFieldEntityDto) {
        return queryService.getEntityDto(
                OrderArticularItem.class,
                supplierService.parameterStringSupplier(ORDER_ID, oneFieldEntityDto.getValue()),
                transformationFunctionService.getTransformationFunction(OrderArticularItem.class, PaymentDto.class));
    }

    @Override
    public PaymentDto getPaymentByPaymentId(OneFieldEntityDto oneFieldEntityDto) {
        return entityOperationDao.getEntityGraphDto("",
                supplierService.parameterStringSupplier(PAYMENT_ID, oneFieldEntityDto.getValue()),
                transformationFunctionService.getTransformationFunction(Payment.class, PaymentDto.class));
    }

    @Override
    public List<PaymentDto> getAllPayments() {
        return entityOperationDao.getEntityGraphDtoList("",
                transformationFunctionService.getTransformationFunction(Payment.class, PaymentDto.class));
    }
}

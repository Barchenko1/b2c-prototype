package com.b2c.prototype.service.processor.payment.base;

import com.b2c.prototype.dao.payment.IPaymentDao;
import com.b2c.prototype.modal.dto.common.OneFieldEntityDto;
import com.b2c.prototype.modal.dto.payload.PaymentDto;
import com.b2c.prototype.modal.dto.searchfield.PaymentSearchFieldEntityDto;
import com.b2c.prototype.modal.entity.order.OrderItemData;
import com.b2c.prototype.modal.entity.payment.Payment;
import com.b2c.prototype.service.common.EntityOperationDao;
import com.b2c.prototype.service.common.IEntityOperationDao;
import com.b2c.prototype.service.function.ITransformationFunctionService;
import com.b2c.prototype.service.processor.payment.IPaymentService;
import com.b2c.prototype.service.processor.query.IQueryService;
import com.b2c.prototype.service.supplier.ISupplierService;

import java.util.List;

import static com.b2c.prototype.util.Constant.ORDER_ID;
import static com.b2c.prototype.util.Constant.PAYMENT_ID;
import static com.b2c.prototype.util.UniqueIdUtil.getUUID;

public class PaymentService implements IPaymentService {

    private final IEntityOperationDao entityOperationDao;
    private final ITransformationFunctionService transformationFunctionService;
    private final IQueryService queryService;
    private final ISupplierService supplierService;

    public PaymentService(IPaymentDao paymentDao,
                          IQueryService queryService,
                          ITransformationFunctionService transformationFunctionService,
                          ISupplierService supplierService) {
        this.entityOperationDao = new EntityOperationDao(paymentDao);
        this.queryService = queryService;
        this.transformationFunctionService = transformationFunctionService;
        this.supplierService = supplierService;
    }

    @Override
    public void saveUpdatePayment(PaymentSearchFieldEntityDto paymentSearchFieldEntityDto) {
        entityOperationDao.executeConsumer(session -> {
            OrderItemData orderItemData = queryService.getEntity(
                    OrderItemData.class,
                    supplierService.parameterStringSupplier(ORDER_ID, paymentSearchFieldEntityDto.getSearchField()));
            PaymentDto paymentDto = paymentSearchFieldEntityDto.getNewEntity();
            Payment newPayment = transformationFunctionService.getEntity(
                    Payment.class,
                    paymentDto);
            Payment payment = orderItemData.getPayment();
            if (payment != null) {
                newPayment.setPaymentId(payment.getPaymentId());
            } else {
                newPayment.setPaymentId(getUUID());
            }
            orderItemData.setPayment(newPayment);
            session.merge(orderItemData);
        });
    }

    @Override
    public void deletePaymentByOrderId(OneFieldEntityDto oneFieldEntityDto) {
        entityOperationDao.deleteEntity(
                supplierService.entityFieldSupplier(
                        OrderItemData.class,
                        supplierService.parameterStringSupplier(ORDER_ID, oneFieldEntityDto.getValue()),
                        transformationFunctionService.getTransformationFunction(OrderItemData.class, Payment.class)));
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
                OrderItemData.class,
                supplierService.parameterStringSupplier(ORDER_ID, oneFieldEntityDto.getValue()),
                transformationFunctionService.getTransformationFunction(OrderItemData.class, PaymentDto.class));
    }

    @Override
    public PaymentDto getPaymentByPaymentId(OneFieldEntityDto oneFieldEntityDto) {
        return entityOperationDao.getEntityDto(
                supplierService.parameterStringSupplier(PAYMENT_ID, oneFieldEntityDto.getValue()),
                transformationFunctionService.getTransformationFunction(Payment.class, PaymentDto.class));
    }

    @Override
    public List<PaymentDto> getAllPayments() {
        return entityOperationDao.getEntityDtoList(
                transformationFunctionService.getTransformationFunction(Payment.class, PaymentDto.class));
    }
}

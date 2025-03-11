package com.b2c.prototype.manager.payment.base;

import com.b2c.prototype.dao.payment.IPaymentDao;
import com.b2c.prototype.modal.dto.payload.PaymentDto;
import com.b2c.prototype.modal.entity.order.DeliveryArticularItemQuantity;
import com.b2c.prototype.modal.entity.payment.Payment;
import com.b2c.prototype.service.function.ITransformationFunctionService;
import com.b2c.prototype.manager.payment.IPaymentManager;
import com.b2c.prototype.service.query.ISearchService;
import com.b2c.prototype.service.supplier.ISupplierService;
import com.tm.core.finder.factory.IParameterFactory;
import com.tm.core.process.manager.common.EntityOperationManager;
import com.tm.core.process.manager.common.IEntityOperationManager;

import java.util.List;

import static com.b2c.prototype.util.Constant.ORDER_ID;
import static com.b2c.prototype.util.Constant.PAYMENT_ID;

public class PaymentManager implements IPaymentManager {

    private final IEntityOperationManager entityOperationDao;
    private final ITransformationFunctionService transformationFunctionService;
    private final ISearchService searchService;
    private final ISupplierService supplierService;
    private final IParameterFactory parameterFactory;

    public PaymentManager(IPaymentDao paymentDao,
                          ISearchService searchService,
                          ITransformationFunctionService transformationFunctionService,
                          ISupplierService supplierService,
                          IParameterFactory parameterFactory) {
        this.entityOperationDao = new EntityOperationManager(paymentDao);
        this.searchService = searchService;
        this.transformationFunctionService = transformationFunctionService;
        this.supplierService = supplierService;
        this.parameterFactory = parameterFactory;
    }

    @Override
    public void saveUpdatePayment(String orderId, PaymentDto paymentDto) {
        entityOperationDao.executeConsumer(session -> {
            DeliveryArticularItemQuantity orderItemDataOption = searchService.getNamedQueryEntity(
                    DeliveryArticularItemQuantity.class,
                    "",
                    parameterFactory.createStringParameter(ORDER_ID, orderId));
            Payment newPayment = transformationFunctionService.getEntity(
                    Payment.class,
                    paymentDto);
//            Payment payment = orderItemDataOption.getPayment();
//            if (payment != null) {
//                newPayment.setPaymentId(payment.getPaymentId());
//            } else {
//                newPayment.setPaymentId(getUUID());
//            }
//            orderItemDataOption.setPayment(newPayment);
//            session.merge(orderItemDataOption);
        });
    }

    @Override
    public void deletePaymentByOrderId(String orderId) {
        entityOperationDao.deleteEntity(
                supplierService.entityFieldSupplier(
                        DeliveryArticularItemQuantity.class,
                        "",
                        supplierService.parameterStringSupplier(ORDER_ID, orderId),
                        transformationFunctionService.getTransformationFunction(DeliveryArticularItemQuantity.class, Payment.class)));
    }

    @Override
    public void deletePaymentByPaymentId(String paymentId) {
        entityOperationDao.deleteEntity(
                supplierService.entityFieldSupplier(
                        Payment.class,
                        "",
                        supplierService.parameterStringSupplier(PAYMENT_ID, paymentId)));
    }

    @Override
    public PaymentDto getPaymentByOrderId(String orderId) {
        return searchService.getNamedQueryEntityDto(
                DeliveryArticularItemQuantity.class,
                "",
                parameterFactory.createStringParameter(ORDER_ID, orderId),
                transformationFunctionService.getTransformationFunction(DeliveryArticularItemQuantity.class, PaymentDto.class));
    }

    @Override
    public PaymentDto getPaymentByPaymentId(String paymentId) {
        return entityOperationDao.getGraphEntityDto(
                "",
                parameterFactory.createStringParameter(PAYMENT_ID, paymentId),
                transformationFunctionService.getTransformationFunction(Payment.class, PaymentDto.class));
    }

    @Override
    public List<PaymentDto> getAllPayments() {
        return entityOperationDao.getGraphEntityDtoList(
                "",
                transformationFunctionService.getTransformationFunction(Payment.class, PaymentDto.class));
    }
}

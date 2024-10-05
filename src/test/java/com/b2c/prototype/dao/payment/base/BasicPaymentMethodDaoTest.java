package com.b2c.prototype.dao.payment.base;

import com.b2c.prototype.dao.AbstractSingleEntityDaoTest;
import com.b2c.prototype.dao.EntityDataSet;
import com.b2c.prototype.modal.entity.payment.PaymentMethod;
import org.junit.jupiter.api.BeforeAll;

class BasicPaymentMethodDaoTest extends AbstractSingleEntityDaoTest {

    @BeforeAll
    public static void setup() {
        dao = new BasicPaymentMethodDao(sessionFactory, entityIdentifierDao);
    }

    @Override
    protected String getEmptyDataSetPath() {
        return "/datasets/payment/payment_method/emptyPaymentMethodDataSet.yml";
    }

    @Override
    protected EntityDataSet<?> getTestDataSet() {
        PaymentMethod paymentMethod = PaymentMethod.builder()
                .id(1L)
                .method("Card")
                .build();
        return new EntityDataSet<>(paymentMethod, "/datasets/payment/payment_method/testPaymentMethodDataSet.yml");
    }

    @Override
    protected EntityDataSet<?> getSaveDataSet() {
        PaymentMethod paymentMethod = PaymentMethod.builder()
                .method("Card")
                .build();
        return new EntityDataSet<>(paymentMethod, "/datasets/payment/payment_method/savePaymentMethodDataSet.yml");
    }

    @Override
    protected EntityDataSet<?> getUpdateDataSet() {
        PaymentMethod paymentMethod = PaymentMethod.builder()
                .id(1L)
                .method("Update Card")
                .build();
        return new EntityDataSet<>(paymentMethod, "/datasets/payment/payment_method/updatePaymentMethodDataSet.yml");
    }

}
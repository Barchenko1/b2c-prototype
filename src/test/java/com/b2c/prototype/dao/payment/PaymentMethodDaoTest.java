package com.b2c.prototype.dao.payment;

import com.b2c.prototype.dao.AbstractDaoTest;
import com.b2c.prototype.dao.IGeneralEntityDao;
import com.b2c.prototype.modal.entity.payment.PaymentMethod;
import com.github.database.rider.core.api.dataset.DataSet;
import com.github.database.rider.core.api.dataset.ExpectedDataSet;
import com.nimbusds.jose.util.Pair;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class PaymentMethodDaoTest extends AbstractDaoTest {

    @Autowired
    private IGeneralEntityDao generalEntityDao;

    @Test
    @DataSet(value = "datasets/payment/payment_method/emptyPaymentMethodDataSet.yml", cleanBefore = true)
    @ExpectedDataSet(value = "datasets/payment/payment_method/savePaymentMethodDataSet.yml", orderBy = "id")
    public void persistEntity_success() {
        PaymentMethod entity = getPaymentMethod();
        entity.setId(0);

        generalEntityDao.persistEntity(entity);
    }

    @Test
    @DataSet(value = "datasets/payment/payment_method/testPaymentMethodDataSet.yml", cleanBefore = true)
    @ExpectedDataSet(value = "datasets/payment/payment_method/updatePaymentMethodDataSet.yml", orderBy = "id")
    public void mergeEntity_success() {
        PaymentMethod entity = getPaymentMethod();
        entity.setValue("Update Card");

        generalEntityDao.mergeEntity(entity);
    }

    @Test
    @DataSet(value = "datasets/payment/payment_method/testPaymentMethodDataSet.yml", cleanBefore = true)
    @ExpectedDataSet(value = "datasets/payment/payment_method/emptyPaymentMethodDataSet.yml", orderBy = "id")
    public void removeEntity_success() {
        PaymentMethod entity = getPaymentMethod();

        generalEntityDao.removeEntity(entity);
    }

    @Test
    @DataSet(value = "datasets/payment/payment_method/testPaymentMethodDataSet.yml", cleanBefore = true)
    public void findEntity_success() {
        PaymentMethod expected = getPaymentMethod();

        Pair<String, Long> pair = Pair.of("id", 1L);
        PaymentMethod entity = generalEntityDao.findEntity("PaymentMethod.findById", List.of(pair));

        assertEquals(expected, entity);
    }

    @Test
    @DataSet(value = "datasets/payment/payment_method/testPaymentMethodDataSet.yml", cleanBefore = true)
    public void findOptionEntity_success() {
        PaymentMethod expected = getPaymentMethod();

        Pair<String, Long> pair = Pair.of("id", 1L);
        Optional<PaymentMethod> optionEntity = generalEntityDao.findOptionEntity("PaymentMethod.findById", List.of(pair));

        assertTrue(optionEntity.isPresent());

        PaymentMethod entity = optionEntity.get();
        assertEquals(expected, entity);
    }

    @Test
    @DataSet(value = "datasets/payment/payment_method/testPaymentMethodDataSet.yml", cleanBefore = true)
    public void findEntityList_success() {
        PaymentMethod entity = getPaymentMethod();

        Pair<String, Long> pair = Pair.of("id", 1L);
        List<PaymentMethod> entityList = generalEntityDao.findEntityList("PaymentMethod.findById", List.of(pair));

        assertEquals(List.of(entity), entityList);
    }

    private PaymentMethod getPaymentMethod() {
        return PaymentMethod.builder()
                .id(1L)
                .value("Card")
                .label("Card")
                .build();
    }

}
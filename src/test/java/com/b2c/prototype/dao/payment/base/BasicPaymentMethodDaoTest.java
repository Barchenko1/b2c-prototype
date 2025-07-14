package com.b2c.prototype.dao.payment.base;

import com.b2c.prototype.dao.AbstractConstantEntityDaoTest;
import com.b2c.prototype.dao.EntityDataSet;
import com.b2c.prototype.modal.entity.payment.PaymentMethod;
import com.tm.core.process.dao.query.QueryService;
import com.tm.core.finder.manager.EntityMappingManager;
import com.tm.core.finder.manager.IEntityMappingManager;
import com.tm.core.finder.table.EntityTable;
import org.junit.jupiter.api.BeforeAll;

class BasicPaymentMethodDaoTest extends AbstractConstantEntityDaoTest {

    @BeforeAll
    public static void setup() {
        IEntityMappingManager entityMappingManager = new EntityMappingManager();
        entityMappingManager.addEntityTable(new EntityTable(PaymentMethod.class, "payment_method"));
        queryService = new QueryService(entityMappingManager);
        dao = new BasicPaymentMethodDao(sessionFactory, queryService);
    }

    @Override
    protected String getEmptyDataSetPath() {
        return "/datasets/payment/payment_method/emptyPaymentMethodDataSet.yml";
    }

    @Override
    protected EntityDataSet<?> getTestDataSet() {
        PaymentMethod paymentMethod = PaymentMethod.builder()
                .id(1L)
                .value("Card")
                .label("Card")
                .build();
        return new EntityDataSet<>(paymentMethod, "/datasets/payment/payment_method/testPaymentMethodDataSet.yml");
    }

    @Override
    protected EntityDataSet<?> getSaveDataSet() {
        PaymentMethod paymentMethod = PaymentMethod.builder()
                .value("Card")
                .label("Card")
                .build();
        return new EntityDataSet<>(paymentMethod, "/datasets/payment/payment_method/savePaymentMethodDataSet.yml");
    }

    @Override
    protected EntityDataSet<?> getUpdateDataSet() {
        PaymentMethod paymentMethod = PaymentMethod.builder()
                .id(1L)
                .value("Update Card")
                .label("Card")
                .build();
        return new EntityDataSet<>(paymentMethod, "/datasets/payment/payment_method/updatePaymentMethodDataSet.yml");
    }

}
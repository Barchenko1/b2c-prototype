package com.b2c.prototype.dao.order.base;

import com.b2c.prototype.dao.AbstractSingleEntityDaoTest;
import com.b2c.prototype.dao.EntityDataSet;
import com.b2c.prototype.modal.entity.order.OrderStatus;
import org.junit.jupiter.api.BeforeAll;

class BasicOrderStatusDaoTest extends AbstractSingleEntityDaoTest {

    @BeforeAll
    public static void setup() {
        dao = new BasicOrderStatusDao(sessionFactory, entityIdentifierDao);
    }

    @Override
    protected String getEmptyDataSetPath() {
        return "/datasets/order/order_status/emptyOrderStatusDataSet.yml";
    }

    @Override
    protected EntityDataSet<?> getTestDataSet() {
        OrderStatus orderStatus = OrderStatus.builder()
                .id(1L)
                .name("Pending")
                .build();
        return new EntityDataSet<>(orderStatus, "/datasets/order/order_status/testOrderStatusDataSet.yml");
    }

    @Override
    protected EntityDataSet<?> getSaveDataSet() {
        OrderStatus orderStatus = OrderStatus.builder()
                .name("Pending")
                .build();
        return new EntityDataSet<>(orderStatus, "/datasets/order/order_status/saveOrderStatusDataSet.yml");
    }

    @Override
    protected EntityDataSet<?> getUpdateDataSet() {
        OrderStatus orderStatus = OrderStatus.builder()
                .id(1L)
                .name("Update Pending")
                .build();
        return new EntityDataSet<>(orderStatus, "/datasets/order/order_status/updateOrderStatusDataSet.yml");
    }
}
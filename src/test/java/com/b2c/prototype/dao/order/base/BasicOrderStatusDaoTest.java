package com.b2c.prototype.dao.order.base;

import com.b2c.prototype.dao.AbstractSimpleEntityDaoTest;
import com.b2c.prototype.dao.EntityDataSet;
import com.b2c.prototype.modal.entity.order.OrderStatus;
import com.tm.core.dao.identifier.EntityIdentifierDao;
import com.tm.core.processor.finder.manager.EntityMappingManager;
import com.tm.core.processor.finder.manager.IEntityMappingManager;
import com.tm.core.processor.finder.table.EntityTable;
import org.junit.jupiter.api.BeforeAll;

class BasicOrderStatusDaoTest extends AbstractSimpleEntityDaoTest {

    @BeforeAll
    public static void setup() {
        IEntityMappingManager entityMappingManager = new EntityMappingManager();
        entityMappingManager.addEntityTable(new EntityTable(OrderStatus.class, "order_status"));
        entityIdentifierDao = new EntityIdentifierDao(sessionManager, entityMappingManager);
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
                .value("Pending")
                .label("Pending")
                .build();
        return new EntityDataSet<>(orderStatus, "/datasets/order/order_status/testOrderStatusDataSet.yml");
    }

    @Override
    protected EntityDataSet<?> getSaveDataSet() {
        OrderStatus orderStatus = OrderStatus.builder()
                .value("Pending")
                .label("Pending")
                .build();
        return new EntityDataSet<>(orderStatus, "/datasets/order/order_status/saveOrderStatusDataSet.yml");
    }

    @Override
    protected EntityDataSet<?> getUpdateDataSet() {
        OrderStatus orderStatus = OrderStatus.builder()
                .id(1L)
                .value("Update Pending")
                .label("Pending")
                .build();
        return new EntityDataSet<>(orderStatus, "/datasets/order/order_status/updateOrderStatusDataSet.yml");
    }
}
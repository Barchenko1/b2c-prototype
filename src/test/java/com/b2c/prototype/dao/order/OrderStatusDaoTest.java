package com.b2c.prototype.dao.order;

import com.b2c.prototype.dao.AbstractDaoTest;
import com.b2c.prototype.dao.IGeneralEntityDao;
import com.b2c.prototype.modal.entity.order.OrderStatus;
import com.github.database.rider.core.api.dataset.DataSet;
import com.github.database.rider.core.api.dataset.ExpectedDataSet;
import com.nimbusds.jose.util.Pair;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class OrderStatusDaoTest extends AbstractDaoTest {

    @Autowired
    private IGeneralEntityDao generalEntityDao;

    @Test
    @DataSet(value = "datasets/dao/order/order_status/emptyOrderStatusDataSet.yml", cleanBefore = true)
    @ExpectedDataSet(value = "datasets/dao/order/order_status/saveOrderStatusDataSet.yml", orderBy = "id")
    public void persistEntity_success() {
        OrderStatus entity = getOrderStatus();
        entity.setId(0);

        generalEntityDao.persistEntity(entity);
    }

    @Test
    @DataSet(value = "datasets/dao/order/order_status/testOrderStatusDataSet.yml", cleanBefore = true)
    @ExpectedDataSet(value = "datasets/dao/order/order_status/updateOrderStatusDataSet.yml", orderBy = "id")
    public void mergeEntity_success() {
        OrderStatus entity = getOrderStatus();
        entity.setValue("Update Pending");

        generalEntityDao.mergeEntity(entity);
    }

    @Test
    @DataSet(value = "datasets/dao/order/order_status/testOrderStatusDataSet.yml", cleanBefore = true)
    @ExpectedDataSet(value = "datasets/dao/order/order_status/emptyOrderStatusDataSet.yml", orderBy = "id")
    public void removeEntity_success() {
        OrderStatus entity = getOrderStatus();

        generalEntityDao.removeEntity(entity);
    }

    @Test
    @DataSet(value = "datasets/dao/order/order_status/testOrderStatusDataSet.yml", cleanBefore = true)
    public void findEntity_success() {
        OrderStatus expected = getOrderStatus();

        Pair<String, Long> pair = Pair.of("id", 1L);
        OrderStatus entity = generalEntityDao.findEntity("OrderStatus.findById", List.of(pair));

        assertEquals(expected, entity);
    }

    @Test
    @DataSet(value = "datasets/dao/order/order_status/testOrderStatusDataSet.yml", cleanBefore = true)
    public void findOptionEntity_success() {
        OrderStatus expected = getOrderStatus();

        Pair<String, Long> pair = Pair.of("id", 1L);
        Optional<OrderStatus> optionEntity = generalEntityDao.findOptionEntity("OrderStatus.findById", List.of(pair));

        assertTrue(optionEntity.isPresent());

        OrderStatus entity = optionEntity.get();
        assertEquals(expected, entity);
    }

    @Test
    @DataSet(value = "datasets/dao/order/order_status/testOrderStatusDataSet.yml", cleanBefore = true)
    public void findEntityList_success() {
        OrderStatus entity = getOrderStatus();

        Pair<String, Long> pair = Pair.of("id", 1L);
        List<OrderStatus> entityList = generalEntityDao.findEntityList("OrderStatus.findById", List.of(pair));

        assertEquals(List.of(entity), entityList);
    }
    
    private OrderStatus getOrderStatus() {
        return OrderStatus.builder()
                .id(1L)
                .value("Pending")
                .label("Pending")
                .build();
    }

}

package com.b2c.prototype.manager.order.base;

import com.b2c.prototype.dao.IGeneralEntityDao;
import com.b2c.prototype.modal.entity.order.OrderStatus;
import com.b2c.prototype.manager.order.IOrderStatusManager;
import com.b2c.prototype.transform.order.IOrderTransformService;
import com.nimbusds.jose.util.Pair;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

import static com.b2c.prototype.util.Constant.KEY;

@Service
public class OrderStatusManager implements IOrderStatusManager {

    private final IGeneralEntityDao generalEntityDao;
    private final IOrderTransformService orderTransformService;

    public OrderStatusManager(IGeneralEntityDao orderStatusDao,
                              IOrderTransformService orderTransformService) {
        this.generalEntityDao = orderStatusDao;
        this.orderTransformService = orderTransformService;
    }

    public void persistEntity(OrderStatus entity) {
        generalEntityDao.persistEntity(entity);
    }

    public void mergeEntity(String searchValue, OrderStatus entity) {
        OrderStatus fetchedEntity =
                generalEntityDao.findEntity("OrderStatus.findByKey", Pair.of(KEY, searchValue));
        entity.setId(fetchedEntity.getId());
        generalEntityDao.mergeEntity(entity);
    }

    public void removeEntity(String value) {
        OrderStatus fetchedEntity = generalEntityDao.findEntity("OrderStatus.findByKey", Pair.of(KEY, value));
        generalEntityDao.removeEntity(fetchedEntity);
    }

    public OrderStatus getEntity(String value) {
        return generalEntityDao.findEntity("OrderStatus.findByKey", Pair.of(KEY, value));
    }

    public Optional<OrderStatus> getEntityOptional(String value) {
        OrderStatus entity = generalEntityDao.findEntity("OrderStatus.findByKey", Pair.of(KEY, value));
        return Optional.of(entity);
    }


    public List<OrderStatus> getEntities() {
        return generalEntityDao.findEntityList("OrderStatus.all", (Pair<String, ?>) null);
    }
}

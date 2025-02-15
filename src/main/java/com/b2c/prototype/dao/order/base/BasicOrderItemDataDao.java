package com.b2c.prototype.dao.order.base;

import com.b2c.prototype.modal.entity.order.OrderArticularItem;
import com.tm.core.process.dao.common.AbstractEntityDao;
import com.b2c.prototype.dao.order.IOrderItemDataDao;
import com.tm.core.process.dao.identifier.IQueryService;
import org.hibernate.SessionFactory;

public class BasicOrderItemDataDao extends AbstractEntityDao implements IOrderItemDataDao {
    public BasicOrderItemDataDao(SessionFactory sessionFactory, IQueryService queryService) {
        super(sessionFactory, queryService, OrderArticularItem.class);
    }
}

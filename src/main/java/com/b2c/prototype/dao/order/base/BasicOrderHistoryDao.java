package com.b2c.prototype.dao.order.base;

import com.b2c.prototype.modal.entity.order.OrderHistory;
import com.tm.core.dao.single.AbstractSingleEntityDao;
import com.b2c.prototype.dao.order.IOrderHistoryDao;
import org.hibernate.SessionFactory;

public class BasicOrderHistoryDao extends AbstractSingleEntityDao implements IOrderHistoryDao {
    public BasicOrderHistoryDao(SessionFactory sessionFactory) {
        super(sessionFactory, OrderHistory.class);
    }
}

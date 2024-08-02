package com.b2c.prototype.dao.basic;

import com.b2c.prototype.modal.client.entity.order.OrderHistory;
import com.tm.core.dao.single.AbstractSingleEntityDao;
import com.b2c.prototype.dao.order.IOrderHistoryDao;
import org.hibernate.SessionFactory;

public class BasicOrderHistoryDao extends AbstractSingleEntityDao implements IOrderHistoryDao {
    public BasicOrderHistoryDao(SessionFactory sessionFactory) {
        super(sessionFactory, OrderHistory.class);
    }
}

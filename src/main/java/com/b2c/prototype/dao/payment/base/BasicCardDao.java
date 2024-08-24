package com.b2c.prototype.dao.payment.base;

import com.b2c.prototype.modal.entity.payment.Card;
import com.tm.core.dao.single.AbstractSingleEntityDao;
import com.b2c.prototype.dao.payment.ICardDao;
import org.hibernate.SessionFactory;

public class BasicCardDao extends AbstractSingleEntityDao implements ICardDao {
    public BasicCardDao(SessionFactory sessionFactory) {
        super(sessionFactory, Card.class);
    }
}

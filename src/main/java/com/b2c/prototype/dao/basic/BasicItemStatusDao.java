package com.b2c.prototype.dao.basic;

import com.b2c.prototype.modal.client.entity.item.ItemStatus;
import com.tm.core.dao.single.AbstractSingleEntityDao;
import com.b2c.prototype.dao.item.IItemStatusDao;
import org.hibernate.SessionFactory;

public class BasicItemStatusDao extends AbstractSingleEntityDao implements IItemStatusDao {
    public BasicItemStatusDao(SessionFactory sessionFactory) {
        super(sessionFactory, ItemStatus.class);
    }
}

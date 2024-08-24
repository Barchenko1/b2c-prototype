package com.b2c.prototype.dao.item.base;

import com.b2c.prototype.modal.entity.item.Item;
import com.tm.core.dao.single.AbstractSingleEntityDao;
import com.b2c.prototype.dao.item.IItemDao;
import org.hibernate.SessionFactory;

public class BasicItemDao extends AbstractSingleEntityDao implements IItemDao {
    public BasicItemDao(SessionFactory sessionFactory) {
        super(sessionFactory, Item.class);
    }
}

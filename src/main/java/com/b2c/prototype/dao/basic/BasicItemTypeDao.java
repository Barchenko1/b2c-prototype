package com.b2c.prototype.dao.basic;

import com.b2c.prototype.modal.client.entity.item.ItemType;
import com.tm.core.dao.single.AbstractSingleEntityDao;
import com.b2c.prototype.dao.item.IItemTypeDao;
import org.hibernate.SessionFactory;

public class BasicItemTypeDao extends AbstractSingleEntityDao implements IItemTypeDao {
    public BasicItemTypeDao(SessionFactory sessionFactory) {
        super(sessionFactory, ItemType.class);
    }
}

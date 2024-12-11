package com.b2c.prototype.dao.item.base;

import com.b2c.prototype.modal.entity.item.Item;
import com.tm.core.dao.common.AbstractEntityDao;
import com.b2c.prototype.dao.item.IItemDao;
import com.tm.core.dao.identifier.IEntityIdentifierDao;
import org.hibernate.SessionFactory;

public class BasicItemDao extends AbstractEntityDao implements IItemDao {
    public BasicItemDao(SessionFactory sessionFactory, IEntityIdentifierDao entityIdentifierDao) {
        super(sessionFactory, entityIdentifierDao, Item.class);
    }
}

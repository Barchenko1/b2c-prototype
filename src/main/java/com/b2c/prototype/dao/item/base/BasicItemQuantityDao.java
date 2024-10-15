package com.b2c.prototype.dao.item.base;

import com.b2c.prototype.dao.item.IItemQuantityDao;
import com.b2c.prototype.modal.entity.item.ItemQuantity;
import com.tm.core.dao.identifier.IEntityIdentifierDao;
import com.tm.core.dao.single.AbstractSingleEntityDao;
import org.hibernate.SessionFactory;

public class BasicItemQuantityDao extends AbstractSingleEntityDao implements IItemQuantityDao {
    public BasicItemQuantityDao(SessionFactory sessionFactory, IEntityIdentifierDao entityIdentifierDao) {
        super(sessionFactory, entityIdentifierDao, ItemQuantity.class);
    }
}

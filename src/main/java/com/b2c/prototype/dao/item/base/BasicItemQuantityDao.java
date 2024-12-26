package com.b2c.prototype.dao.item.base;

import com.b2c.prototype.dao.item.IItemQuantityDao;
import com.b2c.prototype.modal.entity.item.ItemDataOptionQuantity;
import com.tm.core.dao.common.AbstractEntityDao;
import com.tm.core.dao.identifier.IEntityIdentifierDao;
import org.hibernate.SessionFactory;

public class BasicItemQuantityDao extends AbstractEntityDao implements IItemQuantityDao {
    public BasicItemQuantityDao(SessionFactory sessionFactory, IEntityIdentifierDao entityIdentifierDao) {
        super(sessionFactory, entityIdentifierDao, ItemDataOptionQuantity.class);
    }
}

package com.b2c.prototype.dao.item.base;

import com.b2c.prototype.dao.item.IItemDataOptionQuantityDao;
import com.b2c.prototype.modal.entity.item.ArticularItemQuantity;
import com.tm.core.dao.common.AbstractEntityDao;
import com.tm.core.dao.identifier.IEntityIdentifierDao;
import org.hibernate.SessionFactory;

public class BasicItemDataOptionQuantityDao extends AbstractEntityDao implements IItemDataOptionQuantityDao {
    public BasicItemDataOptionQuantityDao(SessionFactory sessionFactory, IEntityIdentifierDao entityIdentifierDao) {
        super(sessionFactory, entityIdentifierDao, ArticularItemQuantity.class);
    }
}

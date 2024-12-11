package com.b2c.prototype.dao.item.base;

import com.b2c.prototype.dao.item.IItemDataDao;
import com.b2c.prototype.modal.entity.item.ItemData;
import com.tm.core.dao.common.AbstractEntityDao;
import com.tm.core.dao.identifier.IEntityIdentifierDao;
import org.hibernate.SessionFactory;

public class BasicItemDataDao extends AbstractEntityDao implements IItemDataDao {
    public BasicItemDataDao(SessionFactory sessionFactory, IEntityIdentifierDao entityIdentifierDao) {
        super(sessionFactory, entityIdentifierDao, ItemData.class);
    }
}

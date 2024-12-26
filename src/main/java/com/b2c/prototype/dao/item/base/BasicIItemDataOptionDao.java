package com.b2c.prototype.dao.item.base;

import com.b2c.prototype.dao.item.IItemDataOptionDao;
import com.b2c.prototype.modal.entity.item.ItemDataOption;
import com.tm.core.dao.common.AbstractEntityDao;
import com.tm.core.dao.identifier.IEntityIdentifierDao;
import org.hibernate.SessionFactory;

public class BasicIItemDataOptionDao extends AbstractEntityDao implements IItemDataOptionDao {
    public BasicIItemDataOptionDao(SessionFactory sessionFactory, IEntityIdentifierDao entityIdentifierDao) {
        super(sessionFactory, entityIdentifierDao, ItemDataOption.class);
    }
}

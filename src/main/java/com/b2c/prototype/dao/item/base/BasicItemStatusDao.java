package com.b2c.prototype.dao.item.base;

import com.b2c.prototype.modal.entity.item.ItemStatus;
import com.tm.core.dao.single.AbstractSingleEntityDao;
import com.b2c.prototype.dao.item.IItemStatusDao;
import com.tm.core.dao.identifier.IEntityIdentifierDao;
import org.hibernate.SessionFactory;

public class BasicItemStatusDao extends AbstractSingleEntityDao implements IItemStatusDao {
    public BasicItemStatusDao(SessionFactory sessionFactory, IEntityIdentifierDao entityIdentifierDao) {
        super(sessionFactory, entityIdentifierDao, ItemStatus.class);
    }
}

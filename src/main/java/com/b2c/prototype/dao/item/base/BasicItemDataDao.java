package com.b2c.prototype.dao.item.base;

import com.b2c.prototype.modal.entity.item.ItemData;
import com.tm.core.dao.identifier.IEntityIdentifierDao;
import com.tm.core.dao.single.AbstractSingleEntityDao;
import com.tm.core.dao.single.ISingleEntityDao;
import org.hibernate.SessionFactory;

public class BasicItemDataDao extends AbstractSingleEntityDao implements ISingleEntityDao {
    public BasicItemDataDao(SessionFactory sessionFactory, IEntityIdentifierDao entityIdentifierDao) {
        super(sessionFactory, entityIdentifierDao, ItemData.class);
    }
}

package com.b2c.prototype.dao.item.base;

import com.b2c.prototype.dao.item.ITempItemData;
import com.b2c.prototype.modal.embedded.item.TempItemData;
import com.tm.core.dao.identifier.IEntityIdentifierDao;
import com.tm.core.dao.single.AbstractSingleEntityDao;
import org.hibernate.SessionFactory;

public class BasicTempItemDataDao extends AbstractSingleEntityDao implements ITempItemData {
    public BasicTempItemDataDao(SessionFactory sessionFactory, IEntityIdentifierDao entityIdentifierDao) {
        super(sessionFactory, entityIdentifierDao, TempItemData.class);
    }
}

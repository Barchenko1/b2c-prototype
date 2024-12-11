package com.b2c.prototype.dao.item.base;

import com.b2c.prototype.dao.item.ITempItemData;
import com.b2c.prototype.modal.embedded.item.TempItemData;
import com.tm.core.dao.common.AbstractEntityDao;
import com.tm.core.dao.identifier.IEntityIdentifierDao;
import org.hibernate.SessionFactory;

public class BasicTempItemDataDao extends AbstractEntityDao implements ITempItemData {
    public BasicTempItemDataDao(SessionFactory sessionFactory, IEntityIdentifierDao entityIdentifierDao) {
        super(sessionFactory, entityIdentifierDao, TempItemData.class);
    }
}

package com.b2c.prototype.dao.option.base;

import com.b2c.prototype.modal.entity.option.OptionItem;
import com.tm.core.dao.common.AbstractEntityDao;
import com.b2c.prototype.dao.option.IOptionItemDao;
import com.tm.core.dao.identifier.IEntityIdentifierDao;
import org.hibernate.SessionFactory;

public class BasicOptionItemDao extends AbstractEntityDao implements IOptionItemDao {
    public BasicOptionItemDao(SessionFactory sessionFactory, IEntityIdentifierDao entityIdentifierDao) {
        super(sessionFactory, entityIdentifierDao, OptionItem.class);
    }
}

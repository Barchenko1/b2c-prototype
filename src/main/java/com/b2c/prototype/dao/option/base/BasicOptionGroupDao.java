package com.b2c.prototype.dao.option.base;

import com.b2c.prototype.modal.entity.option.OptionGroup;
import com.tm.core.dao.common.AbstractEntityDao;
import com.b2c.prototype.dao.option.IOptionGroupDao;
import com.tm.core.dao.identifier.IEntityIdentifierDao;
import org.hibernate.SessionFactory;

public class BasicOptionGroupDao extends AbstractEntityDao implements IOptionGroupDao {
    public BasicOptionGroupDao(SessionFactory sessionFactory, IEntityIdentifierDao entityIdentifierDao) {
        super(sessionFactory, entityIdentifierDao, OptionGroup.class);
    }
}

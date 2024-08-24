package com.b2c.prototype.dao.option.base;

import com.b2c.prototype.modal.entity.option.OptionGroup;
import com.tm.core.dao.single.AbstractSingleEntityDao;
import com.b2c.prototype.dao.option.IOptionGroupDao;
import org.hibernate.SessionFactory;

public class BasicOptionGroupDao extends AbstractSingleEntityDao implements IOptionGroupDao {
    public BasicOptionGroupDao(SessionFactory sessionFactory) {
        super(sessionFactory, OptionGroup.class);
    }
}

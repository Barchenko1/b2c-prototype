package com.b2c.prototype.dao.option.base;

import com.b2c.prototype.modal.entity.option.OptionItem;
import com.tm.core.dao.single.AbstractSingleEntityDao;
import com.b2c.prototype.dao.option.IOptionItemDao;
import org.hibernate.SessionFactory;

public class BasicOptionItemDao extends AbstractSingleEntityDao implements IOptionItemDao {
    public BasicOptionItemDao(SessionFactory sessionFactory) {
        super(sessionFactory, OptionItem.class);
    }
}

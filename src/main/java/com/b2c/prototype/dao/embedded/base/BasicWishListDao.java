package com.b2c.prototype.dao.embedded.base;

import com.b2c.prototype.dao.embedded.IWishListDao;
import com.b2c.prototype.modal.embedded.wishlist.Wishlist;
import com.tm.core.dao.common.AbstractEntityDao;
import com.tm.core.dao.identifier.IEntityIdentifierDao;
import org.hibernate.SessionFactory;

public class BasicWishListDao extends AbstractEntityDao implements IWishListDao {
    public BasicWishListDao(SessionFactory sessionFactory, IEntityIdentifierDao entityIdentifierDao) {
        super(sessionFactory, entityIdentifierDao, Wishlist.class);
    }
}

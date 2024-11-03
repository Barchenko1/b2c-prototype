package com.b2c.prototype.dao.embedded.base;

import com.b2c.prototype.dao.embedded.IWishListDao;
import com.b2c.prototype.modal.embedded.wishlist.Wishlist;
import com.tm.core.dao.identifier.IEntityIdentifierDao;
import com.tm.core.dao.single.AbstractSingleEntityDao;
import org.hibernate.SessionFactory;

public class BasicWishListDao extends AbstractSingleEntityDao implements IWishListDao {
    public BasicWishListDao(SessionFactory sessionFactory, IEntityIdentifierDao entityIdentifierDao) {
        super(sessionFactory, entityIdentifierDao, Wishlist.class);
    }
}

package com.b2c.prototype.dao.wishlist.base;

import com.b2c.prototype.modal.entity.wishlist.Wishlist;
import com.tm.core.dao.general.AbstractGeneralEntityDao;
import com.b2c.prototype.dao.wishlist.IWishListDao;
import com.tm.core.dao.identifier.IEntityIdentifierDao;
import org.hibernate.SessionFactory;

public class BasicWishListDao extends AbstractGeneralEntityDao implements IWishListDao {
    public BasicWishListDao(SessionFactory sessionFactory, IEntityIdentifierDao entityIdentifierDao) {
        super(sessionFactory, entityIdentifierDao, Wishlist.class);
    }
}

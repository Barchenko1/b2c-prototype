package com.b2c.prototype.dao.wishlist.base;

import com.b2c.prototype.modal.entity.wishlist.Wishlist;
import com.tm.core.dao.single.AbstractSingleEntityDao;
import com.b2c.prototype.dao.wishlist.IWishListDao;
import org.hibernate.SessionFactory;

public class BasicWishListDao extends AbstractSingleEntityDao implements IWishListDao {
    public BasicWishListDao(SessionFactory sessionFactory) {
        super(sessionFactory, Wishlist.class);
    }
}

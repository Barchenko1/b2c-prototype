package com.b2c.prototype.dao.embedded.base;

import com.b2c.prototype.dao.embedded.IWishListDao;
import com.b2c.prototype.modal.embedded.wishlist.Wishlist;
import com.tm.core.process.dao.common.AbstractEntityDao;
import com.tm.core.process.dao.identifier.IQueryService;
import org.hibernate.SessionFactory;

public class BasicWishListDao extends AbstractEntityDao implements IWishListDao {
    public BasicWishListDao(SessionFactory sessionFactory, IQueryService queryService) {
        super(sessionFactory, queryService, Wishlist.class);
    }
}

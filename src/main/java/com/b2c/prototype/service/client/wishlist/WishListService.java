package com.b2c.prototype.service.client.wishlist;

import com.b2c.prototype.modal.client.dto.request.RequestItemBucketDto;
import com.b2c.prototype.dao.wishlist.IWishListDao;
import lombok.extern.slf4j.Slf4j;

import static com.b2c.prototype.modal.client.query.WishListQuery.INSERT_INTO_WISHLIST;
import static com.b2c.prototype.modal.client.query.WishListQuery.delete_FROM_WISHLIST;

@Slf4j
public class WishListService implements IWishListService {
    private final IWishListDao wishListDao;

    public WishListService(IWishListDao wishListDao) {
        this.wishListDao = wishListDao;
    }

    @Override
    public void addToWishList(RequestItemBucketDto requestItemBucketDto) {
        wishListDao.mutateEntityBySQLQueryWithParams(INSERT_INTO_WISHLIST,
                requestItemBucketDto.getItemName(),
                "username1",
                1);
    }

    @Override
    public void deleteFromWishList(RequestItemBucketDto requestItemBucketDto) {
        wishListDao.mutateEntityBySQLQueryWithParams(delete_FROM_WISHLIST,
                "username1",
                requestItemBucketDto.getItemName());
    }
}

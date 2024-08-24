package com.b2c.prototype.service.wishlist;

import com.b2c.prototype.modal.dto.request.RequestItemBucketDto;
import com.b2c.prototype.dao.wishlist.IWishListDao;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class WishListService implements IWishListService {
    private final IWishListDao wishListDao;

    public WishListService(IWishListDao wishListDao) {
        this.wishListDao = wishListDao;
    }

    @Override
    public void addToWishList(RequestItemBucketDto requestItemBucketDto) {
        wishListDao.mutateEntityBySQLQueryWithParams("",
                requestItemBucketDto.getItemName(),
                "username1",
                1);
    }

    @Override
    public void deleteFromWishList(RequestItemBucketDto requestItemBucketDto) {
        wishListDao.mutateEntityBySQLQueryWithParams("",
                "username1",
                requestItemBucketDto.getItemName());
    }
}

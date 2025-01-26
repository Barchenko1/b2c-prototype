package com.b2c.prototype.service.embedded.wishlist;

import com.b2c.prototype.dao.embedded.IWishListDao;
import com.b2c.prototype.modal.dto.payload.WishListDto;
import com.b2c.prototype.modal.embedded.wishlist.Wishlist;
import com.tm.core.dao.common.IEntityDao;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class WishListManager implements IWishListManager {
    private final IEntityDao wishListDao;

    public WishListManager(IWishListDao wishListDao) {
        this.wishListDao = wishListDao;
    }

    @Override
    public void addToWishList(WishListDto requestItemBucketDto) {
        Wishlist wishlist = Wishlist.builder()
                .dateOfUpdate(System.currentTimeMillis())
//                .userProfile(null)
//                .item(null)
                .build();
//        super.saveEntity(generalEntity);
    }

    @Override
    public void deleteFromWishList(WishListDto requestItemBucketDto) {
//        Parameter[] parameter = parameterFactory.createParameterArray(
//                parameterFactory.createStringParameter("username", requestItemBucketDto.getUserId()),
//                parameterFactory.createStringParameter(articularId, requestItemBucketDto.getArticularId())
//        );

//        super.deleteEntityByParameter(parameter);
    }
}

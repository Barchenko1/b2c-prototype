package com.b2c.prototype.service.wishlist;

import com.b2c.prototype.modal.dto.request.RequestItemBucketDto;

public interface IWishListService {
    void addToWishList(RequestItemBucketDto requestItemBucketDto);
    void deleteFromWishList(RequestItemBucketDto requestItemBucketDto);
}

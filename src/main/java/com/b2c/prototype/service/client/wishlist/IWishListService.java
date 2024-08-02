package com.b2c.prototype.service.client.wishlist;

import com.b2c.prototype.modal.client.dto.request.RequestItemBucketDto;

public interface IWishListService {
    void addToWishList(RequestItemBucketDto requestItemBucketDto);
    void deleteFromWishList(RequestItemBucketDto requestItemBucketDto);
}

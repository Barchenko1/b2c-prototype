package com.b2c.prototype.service.embedded.wishlist;

import com.b2c.prototype.modal.dto.payload.WishListDto;

public interface IWishListManager {
    void addToWishList(WishListDto wishListDto);
    void deleteFromWishList(WishListDto wishListDto);
}

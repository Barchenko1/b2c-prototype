package com.b2c.prototype.service.embedded.wishlist;

import com.b2c.prototype.modal.dto.request.WishListDto;

public interface IWishListService {
    void addToWishList(WishListDto wishListDto);
    void deleteFromWishList(WishListDto wishListDto);
}

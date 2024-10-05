package com.b2c.prototype.service.base.wishlist;

import com.b2c.prototype.modal.dto.request.RequestWishListDto;

public interface IWishListService {
    void addToWishList(RequestWishListDto requestWishListDto);
    void deleteFromWishList(RequestWishListDto requestWishListDto);
}

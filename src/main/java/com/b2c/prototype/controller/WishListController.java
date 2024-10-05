package com.b2c.prototype.controller;

import com.b2c.prototype.service.base.wishlist.IWishListService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1")
public class WishListController {
    private IWishListService wishListService;

    WishListController(IWishListService wishListService) {
        this.wishListService = wishListService;
    }
}

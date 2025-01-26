package com.b2c.prototype.controller.basic;

import com.b2c.prototype.service.embedded.wishlist.IWishListManager;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1")
public class WishListController {
    private final IWishListManager wishListService;

    WishListController(IWishListManager wishListService) {
        this.wishListService = wishListService;
    }
}

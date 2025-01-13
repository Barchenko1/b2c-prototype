package com.b2c.prototype.controller.advanse;

import com.b2c.prototype.service.embedded.wishlist.IWishListService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1")
public class WishListController {
    private final IWishListService wishListService;

    WishListController(IWishListService wishListService) {
        this.wishListService = wishListService;
    }
}

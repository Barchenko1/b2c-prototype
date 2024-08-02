package com.b2c.prototype.modal.client.query;

public interface WishListQuery {
    String FIND_ALL_WISHLIST_LIST = "select * from wishlist";

    String INSERT_INTO_WISHLIST = "INSERT INTO wishlist (userid, itemid) " +
            "SELECT u.id, p.id, ? " +
            "FROM users u " +
            "JOIN items i ON i.name = ? " +
            "WHERE u.username = ?";

    String delete_FROM_WISHLIST = "DELETE FROM wishlist WHERE userid = ? AND itemid = ?";


}

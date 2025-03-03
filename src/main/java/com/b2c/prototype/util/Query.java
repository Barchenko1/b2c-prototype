package com.b2c.prototype.util;

public interface Query {
    String SELECT_STORE_BY_ARTICULAR_ID =
            "SELECT s FROM Store s JOIN s.articularItem ido WHERE ido.articular_id = ?";
    String SELECT_MESSAGEBOX_BY_USER_ID =
            "SELECT mb.* FROM message_box mb JOIN user_details up ON up.id = mb.user_details_id WHERE up.user_id = ?";
    String SELECT_ITEM_BY_ITEM_ID =
            "SELECT i FROM Item i JOIN FETCH i.itemData id WHERE id.itemId = ?";
}

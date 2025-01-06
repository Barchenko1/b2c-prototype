package com.b2c.prototype.util;

public interface Query {
    String SELECT_STORE_BY_ARTICULAR_ID =
            "SELECT s FROM Store s JOIN s.itemDataOption ido WHERE ido.articular_id = ?";
    String SELECT_MESSAGEBOX_BY_USER_ID =
            "SELECT mb.* FROM message_box mb JOIN user_profile up ON up.id = mb.user_profile_id WHERE up.user_id = ?";
    String SELECT_OPTION_ITEM_BY_OPTION_GROUP_AND_OPTION_ITEM_NAME =
            "SELECT oi.* FROM option_item oi JOIN option_group og ON oi.option_group_id = og.id WHERE og.value = ? AND oi.option_name = ?";
    String SELECT_ITEM_BY_ITEM_ID =
            "SELECT i FROM Item i JOIN FETCH i.itemData id WHERE id.itemId = ?";
}

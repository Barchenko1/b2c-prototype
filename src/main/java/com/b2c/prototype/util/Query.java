package com.b2c.prototype.util;

public interface Query {
    // app_user
    String SELECT_APP_USER_BY_USERNAME = "SELECT * FROM app_user a where a.username = ?";
    String SELECT_APP_USER_BY_EMAIL = "SELECT * FROM app_user a where a.email = ?";
    String DELETE_APP_USER_BY_USERNAME = "DELETE FROM app_user a WHERE a.username = ?";
    String DELETE_APP_USER_BY_EMAIL = "DELETE FROM app_user a WHERE a.email = ?";

    // user_info
    String SELECT_ALL_USER_INFO = "SELECT * FROM user_info;";
    String SELECT_USER_INFO_BY_USERNAME = "SELECT ui FROM user_info ui " +
            "JOIN app_user au ON ui.id = au.user_info_id " +
            "WHERE au.username = ?";
    String SELECT_USER_INFO_BY_EMAIL = "SELECT ui FROM user_info ui " +
            "JOIN app_user au ON ui.id = au.user_info_id " +
            "WHERE au.email = ?";
    String UPDATE_USER_INFO_BY_USERNAME = "UPDATE user_info ui " +
            "SET ui.name = ?, " +
            "ui.secondName = ?, " +
            "ui.phoneNumber = ? " +
            "FROM user_info ui " +
            "JOIN app_user au ON au.user_info_id = ui.id " +
            "WHERE au.username = ?";
    String UPDATE_USER_INFO_BY_EMAIL = "UPDATE user_info ui " +
            "SET ui.name = ?, " +
            "    ui.secondName = ?, " +
            "    ui.phoneNumber = ? " +
            "FROM user_info ui " +
            "JOIN app_user au ON au.user_info_id = ui.id " +
            "WHERE au.email = ?;";
    String DELETE_USER_INFO_BY_USERNAME = "DELETE ui FROM user_info ui " +
            "JOIN app_user au ON ui.id = au.user_info_id " +
            "WHERE au.username = ?";
    String DELETE_USER_INFO_BY_EMAIL = "DELETE ui FROM user_info ui " +
            "JOIN app_user au ON ui.id = au.user_info_id " +
            "WHERE au.email = ?";

    // address
    String SELECT_ADDRESS_BY_USERNAME = "SELECT a FROM Address a " +
            "JOIN AppUser u ON a.id = u.address_id WHERE u.username = ?";
    String SELECT_ADDRESS_BY_DELIVERY_ID = "SELECT a FROM Address a " +
            "JOIN Delivery d ON a.id = d.address_id WHERE d.delivery_id = ?";
    String UPDATE_ADDRESS_BY_USERNAME = """
            UPDATE address a \
            SET a.country = ?, \
                a.street = ?, \
                a.building = ?, \
                a.flor = ?, \
                a.apartmentNumber = ? \
            FROM address a
            JOIN app_user au ON au.address_id = a.id
            WHERE au.username = ?;""";
    String UPDATE_ADDRESS_BY_ORDER_ID = "UPDATE address a " +
            "SET " +
            "a.country = ?, " +
            "a.street = ?, " +
            "a.building = ?, " +
            "a.flor = ?, " +
            "a.apartment_number = ? " +
            "FROM address a" +
            "JOIN delivery d ON d.address_id = a.id " +
            "JOIN order_item oi ON oi.delivery_id = d.id " +
            "WHERE oi.order_id = ?;";
    String DELETE_ADDRESS_BY_USERNAME = "DELETE a " +
            "FROM address a " +
            "JOIN app_user u ON a.id = u.address_id " +
            "WHERE u.username = ?";
    String DELETE_ADDRESS_BY_ORDER_ID = "DELETE a " +
            "FROM address a " +
            "JOIN delivery d ON d.address_id = a.id " +
            "JOIN order_item oi ON oi.delivery_id = d.id " +
            "WHERE oi.order_id = ?;";

    // delivery
    String SELECT_DELIVERY_BY_ORDER_ID = "SELECT d " +
            "FROM delivery d" +
            "JOIN order_item oi ON oi.delivery_id = d.id" +
            "WHERE oi.order_id = ?;";
    String DELETE_DELIVERY_BY_ORDER_ID = "DELETE d " +
            "FROM delivery d " +
            "JOIN order_item oi ON oi.delivery_id = d.id " +
            "WHERE oi.order_id = ?;";


    // discount
    String SELECT_DISCOUNT_BY_AMOUNT_AND_IS_CURRENCY = "SELECT FROM discount d " +
            "WHERE d.amount = ? AND d.is_currency = ?";
    String UPDATE_DISCOUNT_BY_AMOUNT_AND_IS_CURRENCY = "UPDATE discount d " +
            "SET d.amount = ?, d.is_currency = ? " +
            "WHERE d.amount = ? AND d.is_currency = ?";
    String DELETE_DISCOUNT_BY_AMOUNT_AND_IS_CURRENCY = "DELETE FROM discount d " +
            "WHERE d.amount = ? AND d.is_currency = ?";
    String SELECT_DISCOUNT_BY_AMOUNT_AND_IS_PERCENTS = "SELECT FROM discount d " +
            "WHERE d.amount = ? AND d.is_percents = ?";
    String UPDATE_DISCOUNT_BY_AMOUNT_AND_IS_PERCENTS = "UPDATE discount d" +
            "SET d.amount = ?, d.is_percents = ? " +
            "WHERE d.amount = ? AND d.is_percents = ?";
    String DELETE_DISCOUNT_BY_AMOUNT_AND_IS_PERCENTS = "DELETE FROM discount d " +
            "WHERE d.amount = ? AND d.is_percents = ?";

    // card
    String INSERT_INTO_CARD = "INSERT INTO card (cartNumber, dateOfExpire, cvv, isActive, ownerName, ownerSecondName, appUser_id) " +
            "SELECT ?, ?, ?, ?, ?, ?, u.id " +
            "FROM app_user u " +
            "WHERE u.username = ?";
    String UPDATE_CARD_BY_CARD_NUMBER = "UPDATE Card c SET " +
            "c.cardNumber = ?, " +
            "c.dateOfExpire = ?, " +
            "c.cvv = ?, " +
            "c.isActive = ?, " +
            "c.ownerName = ?, " +
            "c.ownerSecondName = ? " +
            "WHERE c.cardNumber = ?";
    String SELECT_CARD_BY_CARD_NUMBER = "SELECT * FROM card WHERE c.cardnumber = ?";
    String DELETE_CARD_BY_CARD_NUMBER = "DELETE FROM card c WHERE c.cartNumber = ?";
    String SELECT_CARD_BY_OWNER_USERNAME = "SELECT c.cartNumber, c.dateOfExpire, c.cvv " +
            "FROM card c " +
            "INNER JOIN app_user u ON c.appUser_id = u.id " +
            "WHERE u.username = ?";

    // payment
    String SELECT_ALL_PAYMENTS = "SELECT * FROM payment p";
    String SELECT_PAYMENT_BY_ORDER_ID = "SELECT * FROM payment WHERE id = " +
            "(SELECT payment_id FROM order_item WHERE order_id = ?";
    String DELETE_PAYMENT_BY_ORDER_ID = "DELETE FROM payment WHERE id = " +
            "(SELECT payment_id FROM order_item WHERE order_id = ?";
    String DELETE_PAYMENT_HAVE_NOT_ORDER = "DELETE FROM payment WHERE id NOT IN " +
            "(SELECT payment_id FROM order_item)";

    // item
    String SELECT_ALL_ITEMS = "SELECT * FROM item i";
    String SELECT_ITEM_BY_ARTICLE = "SELECT * FROM item WHERE article_id = ?";
    String UPDATE_ITEM_BY_ARTICLE = "";
    String DELETE_ITEM_BY_ARTICLE = "DELETE FROM item WHERE article_id = ?";

    // category
    String SELECT_ALL_CATEGORIES = "SELECT * FROM category c";
    String SELECT_CATEGORY_BY_NAME = "SELECT * FROM category c WHERE c.name = ?";

    // post
    String SELECT_ALL_POSTS = "SELECT * FROM POST p";
    String SELECT_POST_BY_TITLE = "SELECT * FROM POST p where p.title = ?";
    String SELECT_POST_BY_EMAIL = "SELECT * FROM POST p where p.email = ?";
    String SELECT_POST_BY_USERNAME = "SELECT * FROM POST p where p.username = ?";
    String SELECT_POST_BY_UNIQUE_ID = "SELECT * FROM POST p where p.uniquePostId = ?";

    // bucket
    String INSERT_INTO_BUCKET = "INSERT INTO bucket (userid, itemid, count) " +
            "SELECT u.id, p.id, ? " +
            "FROM users u " +
            "JOIN items p ON p.name = ? " +
            "WHERE u.username = ?";
    String UPDATE_BUCKET = "UPDATE bucket SET count = ? WHERE userid = ? AND itemid = ?";
    String DELETE_FROM_BUCKET = "DELETE FROM bucket WHERE userid = ? AND itemid = ?";

    // delivery type
    String SELECT_ALL_DELIVERY_TYPE = "SELECT * FROM delivery_type";
    String SELECT_DELIVERY_TYPE_BY_NAME = "SELECT * FROM delivery_type d WHERE d.name = ?";
    String UPDATE_DELIVERY_TYPE_BY_NAME = "UPDATE delivery_type d SET d.name = ? WHERE d.name = ?";
    String DELETE_DELIVERY_TYPE_BY_NAME = "DELETE FROM delivery_type d WHERE d.name = ?";

    // order status
    String SELECT_ALL_ORDER_STATUS = "SELECT * FROM order_status";
    String SELECT_ORDER_STATUS_BY_NAME = "SELECT * FROM order_status d WHERE d.name = ?";
    String UPDATE_ORDER_STATUS_BY_NAME = "UPDATE order_status d SET d.name = ? WHERE d.name = ?";
    String DELETE_ORDER_STATUS_BY_NAME = "DELETE FROM order_status d WHERE d.name = ?";

    // payment method
    String SELECT_ALL_PAYMENT_METHOD = "SELECT * FROM payment_method";
    String SELECT_PAYMENT_METHOD_BY_NAME = "SELECT * FROM payment_method p WHERE p.method = ?";
    String UPDATE_PAYMENT_METHOD_BY_NAME = "UPDATE payment_method p SET p.method = ? WHERE p.method = ?";
    String DELETE_PAYMENT_METHOD_BY_NAME = "DELETE FROM payment_method WHERE p.method = ?";

    // brand
    String SELECT_ALL_BRANDS = "SELECT * FROM brand b";
    String SELECT_BRAND_BY_NAME = "SELECT * FROM brand b WHERE b.name = ?";
    String UPDATE_BRAND_BY_NAME = "UPDATE brand b SET name = ? WHERE name = ?";
    String DELETE_BRAND_BY_NAME = "DELETE FROM brand b WHERE b.name = ?";

    // itemStatus
    String SELECT_ALL_ITEM_STATUS = "SELECT * FROM item_status";
    String SELECT_ITEM_STATUS_BY_NAME = "SELECT * FROM item_status i WHERE i.name = ?";
    String UPDATE_ITEM_STATUS_BY_NAME = "UPDATE item_status SET status = ? WHERE name = ?";
    String DELETE_ITEM_STATUS_BY_NAME = "DELETE FROM item_status i WHERE i.name = ?";

    // itemType
    String SELECT_ALL_ITEM_TYPE = "SELECT * FROM item_type";
    String SELECT_ITEM_TYPE_BY_NAME = "SELECT * FROM item_type i WHERE i.name = ?";
    String UPDATE_ITEM_TYPE_BY_NAME = "UPDATE item_type i SET i.name = ? WHERE i.name = ?";
    String DELETE_ITEM_TYPE_BY_NAME = "DELETE FROM item_type i WHERE i.name = ?";

    // option group
    String SELECT_ALL_OPTION_GROUP = "SELECT * FROM option_group";
    String SELECT_OPTION_GROUP_BY_NAME = "SELECT * FROM option_group o WHERE o.name = ?";
    String UPDATE_OPTION_GROUP_BY_NAME = "UPDATE option_group o SET o.name = ?";
    String DELETE_OPTION_GROUP_BY_NAME = "DELETE FROM option_group o WHERE o.name = ?";

    // order
    String SELECT_ALL_ORDER = "SELECT * FROM order";
    String SELECT_ORDER_BY_ORDER_ID = "SELECT * FROM order o WHERE o.order_id = ?";
    String DELETE_ORDER_BY_ORDER_ID = "DELETE FROM order o WHERE o.order_id = ?";

}

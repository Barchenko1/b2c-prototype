package com.b2c.prototype.dao;

import com.github.database.rider.core.api.connection.ConnectionHolder;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public final class DatabaseQueries {

    public static void cleanDatabase(ConnectionHolder connectionHolder) {
        try (Connection connection = connectionHolder.getConnection();
             Statement statement = connection.createStatement()) {
            // Clean up tables in the correct order
            statement.execute("DELETE FROM order_item_contact_info");
            statement.execute("DELETE FROM order_quantity_item");
            statement.execute("DELETE FROM order_item");
            statement.execute("DELETE FROM message_box_message");
            statement.execute("DELETE FROM message_receivers");
            statement.execute("DELETE FROM item_quantity_item_data");
            statement.execute("DELETE FROM item_quantity");
            statement.execute("DELETE FROM item_review");
            statement.execute("DELETE FROM item_post");
            statement.execute("DELETE FROM item_data_option");
            statement.execute("DELETE FROM item");
            statement.execute("DELETE FROM store");
            statement.execute("DELETE FROM item_data");
            statement.execute("DELETE FROM option_item");
            statement.execute("DELETE FROM option_group");
            statement.execute("DELETE FROM payment");
            statement.execute("DELETE FROM review");
            statement.execute("DELETE FROM rating");
            statement.execute("DELETE FROM price");

            statement.execute("DELETE FROM message_box");
            statement.execute("DELETE FROM user_profile_credit_card");
            statement.execute("DELETE FROM user_profile_post");
            statement.execute("DELETE FROM user_profile");
            statement.execute("DELETE FROM contact_info");
            statement.execute("DELETE FROM contact_phone");
            // Add other child tables in correct order

            statement.execute("DELETE FROM delivery");

            statement.execute("DELETE FROM payment_method");
            statement.execute("DELETE FROM currency_discount");
            statement.execute("DELETE FROM currency");
        } catch (SQLException e) {
            throw new RuntimeException("Failed to clean up database after tests", e);
        }
    }

}

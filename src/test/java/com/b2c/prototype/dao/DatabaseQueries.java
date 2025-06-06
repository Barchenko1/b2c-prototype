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
            statement.execute("DELETE FROM message_box_message");
            statement.execute("DELETE FROM message_box");
            statement.execute("DELETE FROM message");
            statement.execute("DELETE FROM message_template_receivers");
            statement.execute("DELETE FROM message_template");
            statement.execute("DELETE FROM articular_item_option_item");
            statement.execute("DELETE FROM articular_item_quantity_price");
            statement.execute("DELETE FROM articular_item_quantity");
            statement.execute("DELETE FROM store");
            statement.execute("DELETE FROM user_address");
            statement.execute("DELETE FROM customer_single_delivery_order");
            statement.execute("DELETE FROM delivery");
            statement.execute("DELETE FROM address");
            statement.execute("DELETE FROM delivery_articular_item_quantity");
            statement.execute("DELETE FROM post");
            statement.execute("DELETE FROM review_comment");
            statement.execute("DELETE FROM review");
            statement.execute("DELETE FROM review_status");
            statement.execute("DELETE FROM rating");
            statement.execute("DELETE FROM item");
            statement.execute("DELETE FROM articular_item");
            statement.execute("DELETE FROM option_item");
            statement.execute("DELETE FROM option_group");
            statement.execute("DELETE FROM payment");
            statement.execute("DELETE FROM time_duration_option");
            statement.execute("DELETE FROM zone_option");

            statement.execute("DELETE FROM user_credit_card");
            statement.execute("DELETE FROM credit_card");
            statement.execute("DELETE FROM device");
            statement.execute("DELETE FROM user_details");
            statement.execute("DELETE FROM contact_info");
            statement.execute("DELETE FROM contact_phone");
            // Add other child tables in correct order
            statement.execute("DELETE FROM payment_method");
            statement.execute("DELETE FROM discount");
            statement.execute("DELETE FROM min_max_commission");
            statement.execute("DELETE FROM commission_value");
            statement.execute("DELETE FROM price");
            statement.execute("DELETE FROM currency");
            statement.execute("DELETE FROM item_data");
        } catch (SQLException e) {
            throw new RuntimeException("Failed to clean up database after tests", e);
        }
    }

}

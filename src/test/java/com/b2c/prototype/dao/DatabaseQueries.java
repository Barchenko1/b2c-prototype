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
            statement.execute("DELETE FROM item_post");
            statement.execute("DELETE FROM item_option");
            statement.execute("DELETE FROM item");
            statement.execute("DELETE FROM option_item");
            statement.execute("DELETE FROM option_group");
            statement.execute("DELETE FROM payment");
            statement.execute("DELETE FROM price");

            // Add other child tables in correct order

            statement.execute("DELETE FROM payment_method");
            statement.execute("DELETE FROM currency_discount");
            statement.execute("DELETE FROM currency");
        } catch (SQLException e) {
            throw new RuntimeException("Failed to clean up database after tests", e);
        }
    }

}

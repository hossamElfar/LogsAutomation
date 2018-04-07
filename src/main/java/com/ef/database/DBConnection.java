package com.ef.database;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {
    private Connection connect = null;
    private static final Logger logger = LoggerFactory.getLogger(DBConnection.class);

    /**
     * DBConnection constructor
     *
     * @throws SQLException
     */
    public DBConnection(String dbHost, String dbName, String user, String password) throws SQLException, ClassNotFoundException {
        Class.forName("com.mysql.jdbc.Driver");
        logger.info("Connecting to database host...");
        connect = DriverManager
                .getConnection(String.format("jdbc:mysql://%s/%s?"
                        + "user=%s&password=%s", dbHost == null ? "localhost" : dbHost, dbName == null ? "wallethub" : dbName, user == null ? "root" : user, password == null ? "" : password));

        logger.info("Connected to the database host");
    }

    /**
     * Get the DBConnection
     *
     * @return Connection
     */
    public Connection getConnection() {
        return this.connect;
    }

    /**
     * Close the DBConnection
     *
     * @throws SQLException
     */
    public void close() throws SQLException {
        logger.info("Closing the database connection");
        if (connect != null) {
            connect.close();
        }
    }
}

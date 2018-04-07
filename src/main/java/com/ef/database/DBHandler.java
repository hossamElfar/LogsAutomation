package com.ef.database;

import com.ef.models.Entry;
import com.ef.utils.Utiles;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;
import java.text.ParseException;
import java.util.ArrayList;

public class DBHandler {
    DBConnection dbConnection;
    private static final Logger logger = LoggerFactory.getLogger(DBHandler.class);

    /**
     * DBHandler constructor
     *
     * @param dbConnection
     */
    public DBHandler(DBConnection dbConnection) {

        this.dbConnection = dbConnection;
    }

    /**
     * Get DBConnection
     *
     * @return DBConnection
     */
    public DBConnection getDbConnection() {
        return dbConnection;
    }

    /**
     * Exec an SQL query -INSERT UPDATE-
     *
     * @param query
     * @return ResultSet
     * @throws SQLException
     */
    public int updateQuery(String query) throws SQLException {
        logger.info("Executing an update query...");
        Statement statement = this.dbConnection.getConnection().createStatement();
        return statement
                .executeUpdate(query);
    }

    /**
     * Exec an SQL query -SELECT-
     *
     * @param query
     * @return ResultSet
     * @throws SQLException
     */
    public ResultSet execQuery(String query) throws SQLException {
        logger.info("Executing a db query...");
        Statement statement = this.dbConnection.getConnection().createStatement();
        return statement
                .executeQuery(query);
    }

    /**
     * Insert many entries in batches
     *
     * @param entries
     * @throws SQLException
     */
    public void insertMany(ArrayList<Entry> entries) throws SQLException {
        Connection connection = this.dbConnection.getConnection();
        PreparedStatement statement = connection.prepareStatement(Utiles.SQL_INSERT_LOG);
        logger.info("Inserting " + entries.size() + " entry...");
        int i = 0;
        for (Entry entry : entries) {
            statement.setString(1, entry.getDate());
            statement.setString(2, entry.getIP());
            statement.setString(3, entry.getRequest());
            statement.setString(4, entry.getStatus());
            statement.setString(5, entry.getUserAgent());
            statement.addBatch();
            i++;
            if (i % 10000 == 0 || i == entries.size()) {
                logger.info("Inserted batch " + i + "/" + entries.size());
                statement.executeBatch(); // Execute every 1000 items.
            }
        }
    }

    /**
     * Block a certain IP after exceeding a given threshold within a given interval
     *
     * @param ip
     * @param threshold
     * @param startDate
     * @param isHourly
     * @throws SQLException
     * @throws ParseException
     */
    public void blockIps(String ip, String threshold, String startDate, boolean isHourly) throws SQLException, ParseException {

        this.updateQuery(String.format(Utiles.SQL_INSERT_BLOCKED_IP,
                ip, "exceded the threshold " + threshold
                        + " between the following interval " + "[" + startDate + " --> " + Utiles.getStringDateAfterAdding(startDate, isHourly) + "]"));


    }
}

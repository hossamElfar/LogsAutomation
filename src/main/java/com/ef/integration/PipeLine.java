package com.ef.integration;

import com.ef.database.DBConnection;
import com.ef.database.DBHandler;
import com.ef.models.Entry;
import com.ef.parser.ParseFile;
import com.ef.utils.DBTablePrinter;
import com.ef.utils.Utiles;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;

public class PipeLine {
    private static final Logger logger = LoggerFactory.getLogger(PipeLine.class);

    public PipeLine(HashMap<String, String> args) throws IOException, ParseException, SQLException, ClassNotFoundException {
        logger.info("Initializing the app with the given argsuments...");
        init(args);
    }

    /**
     * Initialize the app with a given args
     *
     * @param args
     * @throws IOException
     * @throws ParseException
     * @throws SQLException
     * @throws ClassNotFoundException
     */
    public void init(HashMap<String, String> args) throws IOException, ParseException, SQLException, ClassNotFoundException {
        logger.info("Reading file status --> [PROCESSING]");
        ParseFile parseFile = new ParseFile(args.get("accesslog"));
        logger.info("Reading file status --> [DONE]");
        ArrayList<Entry> entries = parseFile.getLogs();
        logger.info("Processing " + entries.size() + " logs");
        DBConnection dbConnection = new DBConnection(args.get("dbHost"), args.get("dbName"), args.get("dbusername"), args.get("password"));
        DBHandler dbHandler = new DBHandler(dbConnection);
        logger.info("Crearting logs table if not exist");
        dbHandler.updateQuery(Utiles.CREATE_TABLE_LOGS_IF_NOT_EXIST_QUERY);
        dbHandler.updateQuery(Utiles.CREATE_TABLE_BLOCKED_IF_NOT_EXIST_QUERY);
        logger.info("Inserting " + entries.size() + " logs into MYSQl DB, this may take some time, be patient...");
        dbHandler.insertMany(entries);
        logger.info("Inserting logs status --> [DONE]");
        logger.info("Executing the required query status --> [Processing]");
        ResultSet resultSet = dbHandler.execQuery(Utiles.getThresholdQuery(args));
        DBTablePrinter.printResultSetAndBlockIps(resultSet, dbHandler, args.get("threshold"), args.get("startDate"), args.get("startDate").equals("hourly"));
        dbConnection.close();
    }
}

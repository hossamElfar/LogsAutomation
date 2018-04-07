package com.ef.utils;

import com.ef.models.Entry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Utiles {

    public static final String CREATE_TABLE_LOGS_IF_NOT_EXIST_QUERY = "CREATE TABLE IF NOT EXISTS logs"
            + "  (date           DATETIME ,"
            + "   ip            VARCHAR(20),"
            + "   request          VARCHAR(30),"
            + "   status           VARCHAR(10),"
            + "   user_agent           TEXT);";

    public static final String CREATE_TABLE_BLOCKED_IF_NOT_EXIST_QUERY = "CREATE TABLE IF NOT EXISTS blocked"
            + "   (ip            VARCHAR(20) UNIQUE,"
            + "   comment           TEXT);";

    public static final String SQL_INSERT_LOG = "insert into logs values (?, ?, ?, ?, ?)";

    public static final String SQL_INSERT_BLOCKED_IP = "insert into blocked values ('%s', '%s')";

    private static final Logger logger = LoggerFactory.getLogger(Utiles.class);

    /**
     * Get an Entry model from raw line
     *
     * @param line
     * @return Entry
     * @throws ParseException
     */
    public static Entry getEntry(String line) throws ParseException {
        String[] inputData = line.split(Pattern.quote("|"));
        return new Entry()
                .setDate(inputData[0])
                .setIP(inputData[1])
                .setRequest(inputData[2])
                .setStatus(inputData[3])
                .setUserAgent(inputData[4])
                .build();
    }

    /**
     * Read the lines of a log file
     *
     * @param fileName
     * @return ArrayList<String>
     * @throws IOException
     */
    public static ArrayList<String> getLinesFromFile(String fileName) throws IOException {
        File file = new File(fileName);
        BufferedReader br = new BufferedReader(new FileReader(file));
        String st;
        ArrayList<String> rawLines = new ArrayList<>();
        logger.info("Reading logs records from file: " + fileName);
        while ((st = br.readLine()) != null) {
            rawLines.add(st);
        }
        return rawLines;
    }

    /**
     * Decode the args options
     *
     * @param array
     * @return HashMap
     */
    public static HashMap<String, String> decodeArgs(String[] array) {
        logger.info("Decoding the given arguments...");
        return (HashMap<String, String>) Stream.of(array)
                .map(elem -> elem.split("\\="))
                .filter(elem -> elem.length == 2)
                .collect(Collectors.toMap(e -> e[0], e -> e[1]))
                .entrySet()
                .stream()
                .collect(
                        Collectors.toMap(e -> e.getKey().split("\\-\\-")[1], e -> e.getValue()));

    }


    /**
     * Builf the threshold query from the given args
     *
     * @param args
     * @return String
     * @throws ParseException
     */
    public static String getThresholdQuery(HashMap<String, String> args) throws ParseException {
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
        String formattedDate = Utiles.getStringDateAfterAdding(args.get("startDate"), args.get("duration").equals("hourly"));
        return String.format("SELECT ip, COUNT(ip) as req FROM logs WHERE date BETWEEN \"%s\" and \"%s\" GROUP BY ip HAVING req > %s",
                df.format(new SimpleDateFormat("yyyy-MM-dd.HH:mm:ss").parse(args.get("startDate"))),
                formattedDate,
                args.get("threshold"));
    }

    /**
     * Add hours to a date
     *
     * @param date
     * @param isHourly
     * @return String
     * @throws ParseException
     */
    public static String getStringDateAfterAdding(String date, boolean isHourly) throws ParseException {
        Calendar cal = Calendar.getInstance();
        Date simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd.HH:mm:ss").parse(date);
        cal.setTime(simpleDateFormat);
        if (isHourly)
            cal.add(Calendar.HOUR_OF_DAY, 1);
        else
            cal.add(Calendar.HOUR_OF_DAY, 24);
        Date newDate = cal.getTime();
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
        return df.format(newDate);
    }
}

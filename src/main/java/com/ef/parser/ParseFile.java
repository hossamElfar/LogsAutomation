package com.ef.parser;

import com.ef.utils.Utiles;
import com.ef.models.Entry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;

public class ParseFile {
    private ArrayList<Entry> logs = new ArrayList<>();
    private static final Logger logger = LoggerFactory.getLogger(ParseFile.class);

    /**
     * Parse a log file
     *
     * @param fileName
     * @throws IOException
     * @throws ParseException
     */
    public ParseFile(String fileName) throws IOException, ParseException {

        ArrayList<String> rawLines = Utiles.getLinesFromFile(fileName);
        logger.info("Parsing file lines into logs entries...");
        for (String line : rawLines) {
            logs.add(Utiles.getEntry(line));
        }
    }

    /**
     * Get the parsed logs entry
     *
     * @return ArrayList<Entry>
     */
    public ArrayList<Entry> getLogs() {
        return logs;
    }
}

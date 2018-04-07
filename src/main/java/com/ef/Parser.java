package com.ef;

import com.ef.integration.PipeLine;
import com.ef.utils.Utiles;

import java.io.IOException;
import java.sql.SQLException;
import java.text.ParseException;

public class Parser {
    public static void main(String[] args) throws ClassNotFoundException, SQLException, ParseException, IOException {
        if (args.length > 0)
            new PipeLine(Utiles.decodeArgs(args));
        else
            System.out.println("Not sufficient args to run the app");
    }
}

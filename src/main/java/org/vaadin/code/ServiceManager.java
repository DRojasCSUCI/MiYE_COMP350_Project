package org.vaadin.code;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.sql.Connection;
import java.sql.*;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Random;
import java.util.Scanner;
import java.util.spi.CalendarDataProvider;


public class ServiceManager {

    public ServiceManager(){
        //Default Constructor
    }

    public int getMaxDurationOptions(Connection conn, String serviceId) throws SQLException
    {
        String sql = "SELECT * FROM SERVICES WHERE SERVICE_ID = ?";
        PreparedStatement pstmt = conn.prepareStatement(sql);
        pstmt.setString(1, serviceId);
        ResultSet rs = pstmt.executeQuery();

        if (!rs.next()){
            System.out.println("Service does not exist");
            return -1;
        }

        String test = rs.getString("DURATION_OPTIONS");
        String[] testArray = test.split("/");

        int max = Integer.MIN_VALUE;

        for (String s : testArray) {
            if (Integer.parseInt(s) > max) {
                max = Integer.parseInt(s);
            }
        }

        return max;
    }

    public void printServices(Connection conn) throws SQLException {

        //Preparing the Query
        String sql = "SELECT * FROM SERVICES;";
        PreparedStatement pstmt = conn.prepareStatement(sql);

        //Executing Query
        ResultSet rs = pstmt.executeQuery();

        //Iterating Over Query Results
        if (!rs.next()) {
            System.out.println("No Services Have Been Added");
        } else {
            System.out.println("\n     SERVICES    ");
            System.out.println("===================");
            do {
                System.out.println("\n" + rs.getString("SERVICE_ID") + ": " + rs.getString("SERVICE_NAME") +
                        "\nDescription: " + rs.getString("SERVICE_DESC") + "\nPrice Per Minute: $" +
                        rs.getFloat("PRICE_PER_MINUTE") + "\nDuration Options: " +
                        rs.getString("DURATION_OPTIONS")
                );
            } while (rs.next());
        }

    }

}

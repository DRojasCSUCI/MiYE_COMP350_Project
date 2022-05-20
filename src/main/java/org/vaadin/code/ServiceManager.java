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

    public void addService(Connection conn, String serviceID, String serviceName, String serviceDescription, float pricePerMinute, String duration) throws SQLException{

        // Preparing the query
        String sql =
                "INSERT INTO SERVICES " +
                        "(SERVICE_ID, SERVICE_NAME, SERVICE_DESC, PRICE_PER_MINUTE, DURATION_OPTIONS)" +
                        "VALUES (?, ?, ?, ?, ?)";
        PreparedStatement pstmt = conn.prepareStatement(sql);

        pstmt.setString(1, serviceID);
        pstmt.setString(2, serviceName);
        pstmt.setString(3, serviceDescription);
        pstmt.setFloat(4, pricePerMinute);
        pstmt.setString(5, duration);
        pstmt.executeUpdate();
        System.out.println("Added new service.");
    }

    public void removeService(Connection conn, String serviceID) throws SQLException {
        // Preparing the query
        String sql = "DELETE FROM SERVICES WHERE SERVICE_ID = \"" + serviceID + "\"";
        PreparedStatement pstmt = conn.prepareStatement(sql);

        pstmt.executeUpdate();
        System.out.println("Deleted Service.");
    }

    public void modifyService(Connection conn, String serviceID) throws SQLException{
        // check if service ID is in table
        System.out.println("What would you like to modify?\n" +
                "[1] Name\n" +
                "[2] Description\n" +
                "[3] Price per Minute\n" +
                "[4] Duration Options\n");
        Scanner scan = new Scanner(System.in);

        int userIn = scan.nextInt();
        scan.nextLine();

        String sql;
        PreparedStatement pstmt;

        switch (userIn){
            case 1:
                // edit the name
                System.out.print("Please input the new name: ");
                String name = scan.nextLine();
                // Preparing the query
                sql = "UPDATE SERVICES SET SERVICE_NAME = \"" + name + "\" WHERE SERVICE_ID = \"" + serviceID + "\"";
                pstmt = conn.prepareStatement(sql);
                pstmt.executeUpdate();
                System.out.println("Name Changed.");
                break;
            case 2:
                // edit the description
                System.out.print("Please input the new description: ");
                String description = scan.nextLine();
                // Preparing the query
                sql = "UPDATE SERVICES SET SERVICE_DESC = \"" + description + "\" WHERE SERVICE_ID = \"" + serviceID + "\"";
                pstmt = conn.prepareStatement(sql);
                pstmt.executeUpdate();
                System.out.println("Description Changed.");
                break;
            case 3:
                // edit the price
                System.out.print("Please input the new price per minute: ");
                float price = scan.nextFloat();
                scan.nextLine();
                // Preparing the query
                sql = "UPDATE SERVICES SET PRICE_PER_MINUTE = " + price + " WHERE SERVICE_ID = \"" + serviceID + "\"";
                pstmt = conn.prepareStatement(sql);
                pstmt.executeUpdate();
                System.out.println("Price Changed.");
                break;
            case 4:
                // edit the duration
                System.out.print("Please input the new duration options: ");
                String duration = scan.nextLine();
                // Preparing the query
                sql = "UPDATE SERVICES SET PRICE_PER_MINUTE = \"" + duration + "\" WHERE SERVICE_ID = \"" + serviceID + "\"";
                pstmt = conn.prepareStatement(sql);
                pstmt.executeUpdate();
                System.out.println("Duration Changed.");
                break;
            default:
                System.out.println("Invalid Input");
        }
    }

}

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

public class ConnectionManager {

    public Connection connect() {
        Connection conn = null;
        try {

            //Database Location
            String url = "jdbc:sqlite:./tempDB";  //Used for testing on local machines
            // String url = "jdbc:sqlite:/MiYE_Project/db/MiYEDB.db";  //Used for the Docker Image

            // create a connection to the database
            conn = DriverManager.getConnection(url);

            // System.out.println("Connection to SQLite has been established.");

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return conn;
    }

    public boolean authentication(Connection conn, String userID, String password) throws SQLException {
        boolean check = true;

        //Preparing the Query
        String sql = "SELECT ADMIN_FLAG FROM USERS WHERE USER_ID = ?";
        PreparedStatement pstmt = conn.prepareStatement(sql);
        pstmt.setString(1, userID);

        //Executing Query
        ResultSet rs = pstmt.executeQuery();

        //Iterating Over Query Results
        if (!rs.next()) {
            System.out.println("User Not Found");
            check = false;
        } else {
            if (rs.getBoolean("ADMIN_FLAG")) {

                String sql2 = "SELECT A.user_id " +
                        "FROM ADMINS A " +
                        "JOIN USERS U ON U.user_id = A.user_id " +
                        "WHERE A.user_id = ? AND A.ADMIN_KEY = ?";

                PreparedStatement pstmt2 = conn.prepareStatement(sql2);


                pstmt2.setString(1, userID);
                pstmt2.setString(2, password);

                //Executing Query
                ResultSet rs2 = pstmt2.executeQuery();
                if (rs2.next()) {
                    System.out.println("\n     WELCOME TO MiYE    ");
                    System.out.println("==========================");
                } else {
                    check = false;
                }


            }

        }
        return check;
    }


}
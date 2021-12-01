package org.vaadin.code;

import java.sql.Connection;
import java.sql.*;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;


public class UserManager {

    public UserManager(){
        //Default Constructor
    }

    public void printUsers(Connection conn) throws SQLException {

        // Preparing the Query
        String query = "SELECT * FROM USERS";
        PreparedStatement pstmt = conn.prepareStatement(query);

        // Execute the Query
        ResultSet rs = pstmt.executeQuery();

        // check if there are users
        if (!rs.next()) {
            System.out.println("No Users in Database");
            return;
        }

        // loop through and print user information
        System.out.println("\n    USERS    ");
        System.out.println("================");
        do {
            System.out.println("\n" + rs.getString("USER_ID") + ": " + rs.getString("L_NAME") + ", " +
                    rs.getString("F_NAME") + "\n" + "Gender: " + rs.getString("GENDER") + "\n" +
                    "Date Start of Stay: " + rs.getString("DATE_START_OF_STAY") + "\n" + "Date End of Stay: " + rs.getString("DATE_END_OF_STAY"));
        } while (rs.next());

    }

    public boolean userExists(Connection conn, String id) throws SQLException {

        // Preparing the Query
        String query = "SELECT * FROM USERS WHERE USER_ID=\'" + id + "\'";
        PreparedStatement pstmt = conn.prepareStatement(query);

        // Execute the Query
        ResultSet rs = pstmt.executeQuery();

        if(!rs.next())
            return false;
        return true;

    }

    public boolean updateUser(Connection conn, Customer user) throws SQLException {
        // Preparing the Query

        LocalDateTime startDate = user.getStartTime().atDate(user.getStartDate());
        LocalDateTime endDate = user.getEndTime().atDate(user.getEndDate());


        DateTimeFormatter outputFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");


        String startOfStay =  startDate.format(outputFormatter);
        String endofStay = endDate.format(outputFormatter);

        String query = "UPDATE USERS SET"
                + " F_NAME = \'" + user.getFirstName() + "\',"
                + " L_NAME = \'" + user.getLastName() + "\',"
                + " DATE_START_OF_STAY = \'" + startOfStay +"\',"
                + " DATE_END_OF_STAY = \'" + endofStay +"\'"
                + " WHERE USER_ID = \'" + user.getUserId() +"\'";

        System.out.println(query);

        Statement pstmt = conn.createStatement();

        // Execute the Query
        pstmt.executeUpdate(query);

        return true;
    }

    public ResultSet getCustomer(Connection conn) throws SQLException {
        // Preparing the Query
        String query = "SELECT * FROM USERS";
        PreparedStatement pstmt = conn.prepareStatement(query);

        // Execute the Query
        return pstmt.executeQuery();
    }

    public ResultSet getUser(Connection conn, String userId) throws SQLException
    {
        String query = "SELECT * FROM USERS WHERE USER_ID = \'" + userId + "\'";
        PreparedStatement pstmt = conn.prepareStatement(query);

        // Execute the Query
        return pstmt.executeQuery();
    }

}

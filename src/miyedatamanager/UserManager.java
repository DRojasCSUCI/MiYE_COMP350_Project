package miyedatamanager;

import java.sql.Connection;
import java.sql.*;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Scanner;


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

}

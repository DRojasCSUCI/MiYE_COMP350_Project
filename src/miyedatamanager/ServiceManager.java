package miyedatamanager;

import java.sql.Connection;
import java.sql.*;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Scanner;


public class ServiceManager {

    public ServiceManager(){
        //Default Constructor
    }

    public static void printServices(Connection conn) throws SQLException {

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

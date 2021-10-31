package miyedatamanager;

import java.sql.Connection;
import java.sql.*;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Scanner;


public class ReservationManager {

    public ReservationManager(){
        //Default constructor
    }

    public static boolean printReservationRS(ResultSet rs) throws SQLException{

        //Iterating Over Query Results
        if (!rs.next()) {

            System.out.println("\nNo Reservations");
            return false;

        } else {

            System.out.println("\n     RESERVATIONS    ");
            System.out.println("=======================");

            do {
                System.out.println("\n" + "Reservation ID: " + rs.getString("RESERVATION_ID") + "\n" +
                        "User: (" + rs.getString("USER_ID") + ") " + rs.getString("L_NAME") + ", " +
                        rs.getString("F_NAME") + "\nService: (" + rs.getString("SERVICE_ID") + ") " +
                        rs.getString("SERVICE_NAME") + "\n" + "Date-Time: " + rs.getString("DATE_TIME") +
                        "\t" + "Duration: " + rs.getInt("DURATION_PICKED") + "min\n" + "Total Cost: $" +
                        rs.getFloat("COST")
                );
            } while (rs.next());

            return true;

        }

    }

    public static void printPastReservations(Connection conn) throws SQLException {

        //Current Date Time
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        System.out.println("Current Date-Time: " + formatter.format(calendar.getTime()));

        //Preparing the Query
        String sql = "" +
                " SELECT USH.RESERVATION_ID, U.USER_ID, U.L_NAME, U.F_NAME, S.SERVICE_ID," +
                " S.SERVICE_NAME, USH.DATE_TIME, USH.DURATION_PICKED, USH.COST" +
                " FROM SERVICES S" +
                " JOIN USERS_SERVICES_HISTORY USH ON S.SERVICE_ID = USH.SERVICE_ID " +
                " JOIN USERS U ON USH.USER_ID = U.USER_ID " +
                " WHERE USH.DATE_TIME < \'" + formatter.format(calendar.getTime()) + "\'";

        //Executing Query and Printing Formatted Results
        PreparedStatement pstmt = conn.prepareStatement(sql);
        ResultSet rs = pstmt.executeQuery();
        printReservationRS(rs);

    }

    public static void printFutureReservations(Connection conn) throws SQLException {

        //Current Date Time
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        System.out.println("Current Date-Time: " + formatter.format(calendar.getTime()));

        //Preparing the Query
        String sql = "" +
                " SELECT USH.RESERVATION_ID, U.USER_ID, U.L_NAME, U.F_NAME, S.SERVICE_ID," +
                " S.SERVICE_NAME, USH.DATE_TIME, USH.DURATION_PICKED, USH.COST" +
                " FROM SERVICES S" +
                " JOIN USERS_SERVICES_HISTORY USH ON S.SERVICE_ID = USH.SERVICE_ID " +
                " JOIN USERS U ON USH.USER_ID = U.USER_ID " +
                " WHERE USH.DATE_TIME >= \'" + formatter.format(calendar.getTime()) + "\'";

        //Executing Query and Printing Formatted Results
        PreparedStatement pstmt = conn.prepareStatement(sql);
        ResultSet rs = pstmt.executeQuery();
        printReservationRS(rs);

    }

    public static void printAllReservations(Connection conn) throws SQLException {

        //Preparing the Query
        String sql = "" +
                " SELECT USH.RESERVATION_ID, U.USER_ID, U.L_NAME, U.F_NAME, S.SERVICE_ID," +
                " S.SERVICE_NAME, USH.DATE_TIME, USH.DURATION_PICKED, USH.COST" +
                " FROM SERVICES S" +
                " JOIN USERS_SERVICES_HISTORY USH ON S.SERVICE_ID = USH.SERVICE_ID" +
                " JOIN USERS U ON USH.USER_ID = U.USER_ID";

        //Executing Query and Printing Formatted Results
        PreparedStatement pstmt = conn.prepareStatement(sql);
        ResultSet rs = pstmt.executeQuery();
        printReservationRS(rs);

    }

    public static boolean listPastReservations(Connection conn, String id) throws SQLException {

        //Current Date Time
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        System.out.println("Current Date-Time: " + formatter.format(calendar.getTime()));

        //Preparing Query
        String sql = "" +
                " SELECT USH.RESERVATION_ID, U.USER_ID, U.F_NAME, U.L_NAME, USH.DATE_TIME," +
                " S.SERVICE_NAME, S.SERVICE_ID, USH.DURATION_PICKED, USH.COST" +
                " FROM USERS U" +
                " JOIN USERS_SERVICES_HISTORY USH ON USH.USER_ID = U.USER_ID" +
                " JOIN SERVICES S ON S.SERVICE_ID = USH.SERVICE_ID" +
                " WHERE U.USER_ID = USH.USER_ID AND U.USER_ID = ? AND USH.DATE_TIME < \'" + formatter.format(calendar.getTime()) + "\'";

        //Executing Query and Printing Formatted Results
        PreparedStatement pstmt = conn.prepareStatement(sql);
        pstmt.setString(1, id);
        ResultSet rs = pstmt.executeQuery();
        return printReservationRS(rs);

    }

    /**
     * Lists all the reservation for a specific user.
     * @param:  conn The database connection
     * @param:  id The user ID
     * @return: boolean Whether the user has any reservations (True = One or More, False = None)
     */
    public static boolean listFutureReservations(Connection conn, String id) throws SQLException {

        //Current Date Time
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        System.out.println("Current Date-Time: " + formatter.format(calendar.getTime()));

        //Preparing Query
        String sql = "" +
                "" +
                " SELECT USH.RESERVATION_ID, U.USER_ID, U.F_NAME, U.L_NAME, USH.DATE_TIME," +
                " S.SERVICE_NAME, S.SERVICE_ID, USH.DURATION_PICKED, USH.COST" +
                " FROM USERS U" +
                " JOIN USERS_SERVICES_HISTORY USH ON USH.USER_ID = U.USER_ID" +
                " JOIN SERVICES S ON S.SERVICE_ID = USH.SERVICE_ID" +
                " WHERE U.USER_ID = USH.USER_ID AND U.USER_ID = ? AND USH.DATE_TIME >= \'" + formatter.format(calendar.getTime()) + "\'";

        //Executing Query and Printing Formatted Results
        PreparedStatement pstmt = conn.prepareStatement(sql);
        pstmt.setString(1, id);
        ResultSet rs = pstmt.executeQuery();
        return printReservationRS(rs);

    }

    public static void listAllReservations(Connection conn, String id) throws SQLException {

        //Preparing Query
        String sql = "" +
                "" +
                " SELECT USH.RESERVATION_ID, U.USER_ID, U.F_NAME, U.L_NAME, USH.DATE_TIME," +
                " S.SERVICE_NAME, S.SERVICE_ID, USH.DURATION_PICKED, USH.COST" +
                " FROM USERS U" +
                " JOIN USERS_SERVICES_HISTORY USH ON USH.USER_ID = U.USER_ID" +
                " JOIN SERVICES S ON S.SERVICE_ID = USH.SERVICE_ID" +
                " WHERE U.USER_ID = USH.USER_ID AND U.USER_ID = ?";

        //Executing Query and Printing Formatted Results
        PreparedStatement pstmt = conn.prepareStatement(sql);
        pstmt.setString(1, id);
        ResultSet rs = pstmt.executeQuery();
        printReservationRS(rs);

    }

    /**
     * Checks if the user has a reservation they are able to cancel. If an applicable
     * reservation is able to be cancelled, then it is cancelled, removed from the database,
     * and the history of the cancelled reservation is recorded in the history table.
     * @param:  conn The database connection
     * @param:  id The user ID
     * @return: None
     */
    public static void cancelReservation(Connection conn, String id) throws SQLException {

        //Prints all Reservations Made For/By The User
        boolean hasReservation = listFutureReservations(conn, id);

        //Checks if user has any reservations
        if (!hasReservation) {
            System.out.println("No Reservations Found to be Cancelled");
            return;
        }

        //Choosing which reservation to be cancelled
        Scanner scan = new Scanner(System.in);
        String input;
        ResultSet rs;
        String sql = "SELECT * FROM USERS_SERVICES_HISTORY WHERE RESERVATION_ID = ?";
        PreparedStatement pstmt = conn.prepareStatement(sql);

        //Looping Until a Valid Reservation to Cancel is Picked
        do {
            System.out.print("Choose a Valid Reservation ID to Cancel or 'EXIT' to go back: ");

            //Checking input
            input = scan.nextLine();
            if(input.compareToIgnoreCase("EXIT") == 0)
                return;

            pstmt.setString(1, input);
            rs = pstmt.executeQuery();
        }
        while(!rs.next());

        //Confirmation to cancel
        System.out.print("Type 'CONFIRM' to Cancel This Reservation: ");
        input = scan.nextLine();

        //Delete the reservation
        if (input.compareToIgnoreCase("CONFIRM") == 0) {
            sql = "UPDATE USERS_SERVICES_HISTORY SET CANCELLED_FLAG = True WHERE RESERVATION_ID = \'" + rs.getString("RESERVATION_ID") + "\'";
            pstmt = conn.prepareStatement(sql);
            pstmt.executeUpdate();
            System.out.println("RESERVATION CANCELLED");
            return;
        }
        System.out.println("Cancellation terminated, returning to application."); // return to main application
    }

}

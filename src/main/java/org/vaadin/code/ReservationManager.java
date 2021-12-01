package org.vaadin.code;

import javax.xml.transform.Result;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.sql.Connection;
import java.sql.*;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Random;
import java.util.Scanner;
import java.util.spi.CalendarDataProvider;


public class ReservationManager {

    public ReservationManager(){
        //Default constructor
    }

    public boolean printReservationRS(ResultSet rs) throws SQLException{

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

    public void printPastReservations(Connection conn) throws SQLException {

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

    public ResultSet printFutureReservations(Connection conn) throws SQLException {

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

        return rs;
    }

    public ResultSet printAllReservations(Connection conn) throws SQLException {

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
//        printReservationRS(rs);

        return rs;
    }



    public boolean listPastReservations(Connection conn, String id) throws SQLException {

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
    public boolean listFutureReservations(Connection conn, String id) throws SQLException {

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

    public void listAllReservations(Connection conn, String id) throws SQLException {

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

    public ResultSet getAllReservations(Connection conn) throws SQLException {

        //Preparing Query
        String sql = "SELECT * FROM USERS_SERVICES_HISTORY";

        //Executing Query and Printing Formatted Results
        PreparedStatement pstmt = conn.prepareStatement(sql);
        ResultSet rs = pstmt.executeQuery();
        return rs;

    }

    /**
     * Checks if the user has a reservation they are able to cancel. If an applicable
     * reservation is able to be cancelled, then it is cancelled, removed from the database,
     * and the history of the cancelled reservation is recorded in the history table.
     * @param:  conn The database connection
     * @param:  id The user ID
     * @return: None
     */
    public void cancelReservation(Connection conn, String id) throws SQLException {

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

//    public boolean checkReservationConflicts(Connection conn, String userId, String serviceId, LocalDateTime dateTime, int duration) throws SQLException, ParseException
//    {
//        ServiceManager srvcManager = new ServiceManager();
//
//        String mineralBath = "001";
//
//        String sql = "SELECT * FROM USERS_SERVICES_HISTORY WHERE SERVICE_ID = ? AND DATE_TIME >= ? AND DATE_TIME <= ?";
//
//        PreparedStatement pstmt = conn.prepareStatement(sql);
//
//        int maxDuration = srvcManager.getMaxDurationOptions(conn, serviceId);
//        if (maxDuration == -1)
//            return false;
//
//
//        pstmt.setString(1, serviceId);
//        pstmt.setString(2,  dateTime.plusMinutes(-1*maxDuration).toString());
//        calendar.add(Calendar.MINUTE,maxDuration);
//        calendar.add(Calendar.MINUTE,duration);
//
//        pstmt.setString(3, outputFormatter.format(calendar.getTimeInMillis()));
//
//        ResultSet rs = pstmt.executeQuery();
//
//        if (rs.next() && serviceId.compareTo(mineralBath) != 0){
//            Calendar tempCalendar = Calendar.getInstance();
//            do {
//
//                if (rs.getInt("DURATION_PICKED") == maxDuration)
//                    return false;
//
//                tempCalendar.setTime(outputFormatter.parse(rs.getString("DATE_TIME")));
//                tempCalendar.add(Calendar.MINUTE,rs.getInt("DURATION_PICKED"));
//                String temp = outputFormatter.format(tempCalendar.getTimeInMillis());
//
//                if (temp.compareTo(aptTime) >= 0)
//                    return false;
//
//            }while (rs.next());
//        }
//
//        String sql2 = "SELECT * FROM USERS_SERVICES_HISTORY WHERE USER_ID = ? AND DATE_TIME >= ? AND DATE_TIME <= ?";
//        PreparedStatement pstmt2 = conn.prepareStatement(sql2);
//
//        pstmt2.setString(1, userId);
//        calendar.set(Calendar.HOUR_OF_DAY, 0);
//        calendar.set(Calendar.MINUTE, 0);
//        calendar.set(Calendar.SECOND, 0);
//        pstmt2.setString(2, outputFormatter.format(calendar.getTimeInMillis()));
//        calendar.set(Calendar.HOUR_OF_DAY, 23);
//        calendar.set(Calendar.MINUTE, 59);
//        calendar.set(Calendar.SECOND, 59);
//        pstmt2.setString(3, outputFormatter.format(calendar.getTimeInMillis()));
//
//        ResultSet rs2 = pstmt2.executeQuery();
//
//        if (rs2.next()){
//            Calendar tempCalendar2 = Calendar.getInstance();
//            do {
//
//                tempCalendar2.setTime(outputFormatter.parse(rs2.getString("DATE_TIME")));
//                tempCalendar2.add(Calendar.MINUTE,rs2.getInt("DURATION_PICKED"));
//                String temp = outputFormatter.format(tempCalendar2.getTimeInMillis());
//
//                if (temp.compareTo(aptTime) >= 0)
//                    return false;
//
//            }while (rs2.next());
//        }
//
//        return true;
//    }

    public String generateRandomId() {

        char[] base62chars =
                "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz"
                        .toCharArray();

        Random _random = new Random();

        StringBuilder sb = new StringBuilder(7);

        for (int i=0; i<7; i++)
            sb.append(base62chars[_random.nextInt(62)]);

        return sb.toString();
    }

    public boolean checkUniqueId (Connection conn, String tableName, String id) throws SQLException
    {
        String sql;

        switch (tableName){
            case "SERVICES":
                sql = "SELECT * FROM SERVICES WHERE SERVICE_ID = ?";
                break;
            case "USERS":
                sql = "SELECT * FROM USERS WHERE USER_ID = ?";
                break;
            case "USERS_SERVICES_HISTORY":
                sql = "SELECT * FROM USERS_SERVICES_HISTORY WHERE RESERVATION_ID = ?";
                break;
            default:
                System.out.println("Invalid Table");
                return false;
        }

        PreparedStatement pstmt = conn.prepareStatement(sql);
        pstmt.setString(1,id);
        ResultSet rs = pstmt.executeQuery();

        if (rs.next()){
            return false;
        }
        return true;

    }

    public Reservations insertReservation(Connection conn, String userID, String serviceID, LocalDateTime dateTime, int durationPicked) throws SQLException, ParseException
    {
        UserManager usrMngr = new UserManager();
        boolean check = usrMngr.userExists(conn, userID);
        if(!check){
            System.out.println("ERROR: User ID \'" + userID + "\' Does Not Exist, Unable To Add Reservation");
            return null;
        }

//        check = checkReservationConflicts(conn, userID, serviceID,dateTime,durationPicked);
//        if(!check){
//            System.out.println("ERROR: Time Conflict Found, Unable To Add Reservation");
//            return false;
//        }

        String sql2 = "SELECT PRICE_PER_MINUTE FROM SERVICES WHERE SERVICE_ID = ?";
        PreparedStatement pstmt2 = conn.prepareStatement(sql2);
        pstmt2.setString(1, serviceID);
        ResultSet rs = pstmt2.executeQuery();
        rs.next();

        float totalCost = rs.getFloat("PRICE_PER_MINUTE") * durationPicked;

        String reservationID;
        do {
            reservationID = generateRandomId();
        }while (! checkUniqueId(conn,"USERS_SERVICES_HISTORY",reservationID));
        DateTimeFormatter outputFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

        String sql =
                "INSERT INTO USERS_SERVICES_HISTORY " +
                        "(RESERVATION_ID, USER_ID, CANCELLED_FLAG, SERVICE_ID, DATE_TIME, DURATION_PICKED, COST)" +
                        "VALUES (?, ?, ?, ?, ?, ?, ?)";
        PreparedStatement pstmt = conn.prepareStatement(sql);
        pstmt.setString(1, reservationID);
        pstmt.setString(2, userID);
        pstmt.setBoolean(3, false);
        pstmt.setString(4, serviceID);
        pstmt.setString(5, dateTime.format(outputFormatter));
        pstmt.setInt(6, durationPicked);
        pstmt.setFloat(7, totalCost);
        pstmt.executeUpdate();
        System.out.println("Added Reservation");
        Reservations c = new Reservations();


        c.setReservationId(reservationID);
        c.setUserId(userID);

        ConnectionManager connMngr = new ConnectionManager();

        // Connection to DataBase
        Connection con = null;
        try {
            con = connMngr.connect();
        } catch (Exception e) {
            System.out.println("\nERROR ON CONNECTING TO SQL DATABASE ON INITIAL POPULATION\n " + e);
        }

        UserManager userMngr = new UserManager();

        assert con != null;
        ResultSet resultSet = userMngr.getUser(con, userID);

        c.setFirstName(resultSet.getString("F_NAME"));
        c.setLastName(resultSet.getString("L_NAME"));
        c.setServiceId(serviceID);

        String date = dateTime.format(outputFormatter);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime formattedDate = LocalDateTime.parse(date, formatter);
        c.setDate(formattedDate.toLocalDate());
        c.setTime(formattedDate.toLocalTime());
        switch (c.getServiceId())
        {
            case "0000001":
                c.setStatus(ReservationsStatus.MineralBath);
                break;
            case "0000002":
                c.setStatus(ReservationsStatus.SwedishMassage);
                break;
            case "0000003":
                c.setStatus(ReservationsStatus.ShiatsuMassage);
                break;
            case "0000004":
                c.setStatus(ReservationsStatus.DeepTissueMassage);
                break;
            case "0000005":
                c.setStatus(ReservationsStatus.NormalFacial);
                break;
            case "0000006":
                c.setStatus(ReservationsStatus.CollagenFacial);
                break;
            case "0000007":
                c.setStatus(ReservationsStatus.HotStone);
                break;
            case "0000008":
                c.setStatus(ReservationsStatus.SugarScrub);
                break;
            case "0000009":
                c.setStatus(ReservationsStatus.HerbalBodyWrap);
                break;
            case "0000010":
                c.setStatus(ReservationsStatus.BotanicalMudWrap);
                break;
        }

        c.setDuration(durationPicked);

        c.setCost(totalCost);


        return c;

    }

}

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

    public void cancelReservation(Connection conn, String id) throws SQLException {
        System.out.println("I'M IN CANCEL RESERVATION");
        String sql = "DELETE FROM USERS_SERVICES_HISTORY WHERE RESERVATION_ID = \'" + id + "\'";
        PreparedStatement pstmt = conn.prepareStatement(sql);
        pstmt = conn.prepareStatement(sql);
        System.out.println(id);pstmt.executeUpdate();
        System.out.println("I'm DONE WITH CANCEL RESERVATION");
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

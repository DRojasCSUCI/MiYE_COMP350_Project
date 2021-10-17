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


public class Test {

    public static Connection connect() {
        Connection conn = null;
        try {

            // db parameters
            String url = "jdbc:sqlite:tempDB";
            // String url = "jdbc:sqlite:/MiYE_Project/db/MiYEDB.db";
            // create a connection to the database
            conn = DriverManager.getConnection(url);

            System.out.println("Connection to SQLite has been established.");

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return conn;
    }

    public static void printUsers(Connection conn) throws SQLException {

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

    public static boolean authentication(Connection conn, String userID, String password) throws SQLException {
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

    public static int getMaxDurationOptions(Connection conn, String serviceId) throws SQLException
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

    public static boolean checkReservationConflicts(Connection conn, String userId, String serviceId, String dateTime, int duration) throws SQLException, ParseException
    {
        String mineralBath = "001";
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat inputFormater = new SimpleDateFormat("yyyy-MM-dd hh:mm aa");
        calendar.setTime(inputFormater.parse(dateTime));

        DateFormat outputFormatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String aptTime = outputFormatter.format(calendar.getTimeInMillis());


        String sql = "SELECT * FROM USERS_SERVICES_HISTORY WHERE SERVICE_ID = ? AND DATE_TIME >= ? AND DATE_TIME <= ?";
        PreparedStatement pstmt = conn.prepareStatement(sql);
        int maxDuration = getMaxDurationOptions(conn, serviceId);
        if (maxDuration == -1)
            return false;

        pstmt.setString(1, serviceId);
        calendar.add(Calendar.MINUTE,-1*maxDuration);
        pstmt.setString(2, outputFormatter.format(calendar.getTimeInMillis()));
        calendar.add(Calendar.MINUTE,maxDuration);
        calendar.add(Calendar.MINUTE,duration);
        pstmt.setString(3, outputFormatter.format(calendar.getTimeInMillis()));
        ResultSet rs = pstmt.executeQuery();
        if (rs.next() && serviceId.compareTo(mineralBath) != 0){
            Calendar tempCalendar = Calendar.getInstance();
            do {
                if (rs.getInt("DURATION_PICKED") == maxDuration)
                    return false;
                tempCalendar.setTime(outputFormatter.parse(rs.getString("DATE_TIME")));
                tempCalendar.add(Calendar.MINUTE,rs.getInt("DURATION_PICKED"));
                String temp = outputFormatter.format(tempCalendar.getTimeInMillis());
                if (temp.compareTo(aptTime) >= 0)
                    return false;
            }while (rs.next());
        }

        String sql2 = "SELECT * FROM USERS_SERVICES_HISTORY WHERE USER_ID = ? AND DATE_TIME >= ? AND DATE_TIME <= ?";
        PreparedStatement pstmt2 = conn.prepareStatement(sql2);
        pstmt2.setString(1, userId);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        pstmt2.setString(2, outputFormatter.format(calendar.getTimeInMillis()));
        calendar.set(Calendar.HOUR_OF_DAY, 23);
        calendar.set(Calendar.MINUTE, 59);
        calendar.set(Calendar.SECOND, 59);
        pstmt2.setString(3, outputFormatter.format(calendar.getTimeInMillis()));
        ResultSet rs2 = pstmt2.executeQuery();
        if (rs2.next()){
            Calendar tempCalendar2 = Calendar.getInstance();
            do {
                tempCalendar2.setTime(outputFormatter.parse(rs2.getString("DATE_TIME")));
                tempCalendar2.add(Calendar.MINUTE,rs2.getInt("DURATION_PICKED"));
                String temp = outputFormatter.format(tempCalendar2.getTimeInMillis());
                if (temp.compareTo(aptTime) >= 0)
                    return false;
            }while (rs2.next());
        }

        return true;
    }

    public static String generateRandomId() {

        char[] base62chars =
                "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz"
                        .toCharArray();

        Random _random = new Random();

        var sb = new StringBuilder(7);

        for (int i=0; i<7; i++)
            sb.append(base62chars[_random.nextInt(62)]);

        return sb.toString();
    }

    public static boolean checkUniqueId (Connection conn, String tableName, String id) throws SQLException
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

    public static void insertReservation(Connection conn, String userID, String serviceID, String dateTime, int durationPicked) throws SQLException, ParseException
    {

        boolean check = checkReservationConflicts(conn, userID, serviceID,dateTime,durationPicked);
        if(!check){
            System.out.println("Time Conflict Found Unable To Add Reservation");
            return;
        }
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

        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat inputFormater = new SimpleDateFormat("yyyy-MM-dd hh:mm aa");
        calendar.setTime(inputFormater.parse(dateTime));

        SimpleDateFormat outputFormatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String aptTime = outputFormatter.format(calendar.getTimeInMillis());


        String sql =
                "INSERT INTO USERS_SERVICES_HISTORY " +
                        "(RESERVATION_ID, USER_ID, CANCELLED_FLAG, SERVICE_ID, DATE_TIME, DURATION_PICKED, COST)" +
                        "VALUES (?, ?, ?, ?, ?, ?, ?)";
        PreparedStatement pstmt = conn.prepareStatement(sql);
        pstmt.setString(1, reservationID);
        pstmt.setString(2, userID);
        pstmt.setBoolean(3, false);
        pstmt.setString(4, serviceID);
        pstmt.setString(5, aptTime);
        pstmt.setInt(6, durationPicked);
        pstmt.setFloat(7, totalCost);
        pstmt.executeUpdate();
        System.out.println("Added Reservation");

    }

    public static void main(String[] args) throws SQLException, ParseException
    {

        //Connection to DataBase
        Connection con;
        try {
            con = connect();
        } catch (Exception e) {
            System.out.println("\nERROR ON CONNECTING TO SQL DATABASE\n " + e);
            return;
        }

        //Terminal Interface
        String userIn;
        String password;
        System.out.println("Type EXIT to exit.");

        do {
            Scanner scan = new Scanner(System.in);
            System.out.print("Please Enter Your User Name: ");
            userIn = scan.nextLine();
            System.out.print("Please Enter Your Password: ");
            password = scan.nextLine();

            if (authentication(con, userIn, password)) {

                while (userIn.compareTo("EXIT") != 0) {

                    //Prompt for User Input
                    System.out.println("\n" +
                            "Enter a Two-Character Command From the Available Actions\n" +
                            "[VAU]: View All Users\n" +
                            "[VAS]: View All Services\n" +
                            "[VPR]: View Past Reservations\n" +
                            "[VFR]: View Future Reservations\n" +
                            "[VAR]: View All Reservations\n" +
                            "[UPR]: List Past User Reservations\n" +
                            "[UFR]: List Future User Reservations\n" +
                            "[UAR]: List All User Reservations\n" +
                            "[CSR]: Cancel Single Reservation\n" +
                            "[CAR]: Create A Reservation\n" +
                            "[EXIT]: Exit\n" +
                            "[...]: ...\r" +  // '\r' Prevents this line from being printed
                            "Enter Option: "
                    );

                    userIn = scan.nextLine();

                    //Checking Input
                    switch (userIn.toUpperCase()) {
                        case "VAU":
                            printUsers(con);
                            break;
                        case "VAS":
                            printServices(con);
                            break;
                        case "VPR":
                            printPastReservations(con);
                            break;
                        case "VFR":
                            printFutureReservations(con);
                            break;
                        case "VAR":
                            printAllReservations(con);
                            break;
                        case "UPR":
                            System.out.println("Please input User ID: ");
                            userIn = scan.nextLine();
                            listPastReservations(con, userIn);
                            break;
                        case "UFR":
                            System.out.println("Please input User ID: ");
                            userIn = scan.nextLine();
                            listFutureReservations(con, userIn);
                            break;
                        case "UAR":
                            System.out.println("Please input User ID: ");
                            userIn = scan.nextLine();
                            listAllReservations(con, userIn);
                            break;
                        case "CSR":
                            System.out.println("Please input User ID: ");
                            userIn = scan.nextLine();
                            cancelReservation(con, userIn);
                            break;
                        case "CAR":
                            System.out.println("Please input User ID: ");
                            String userID = scan.nextLine();
                            System.out.println("Please input Service ID: ");
                            String serviceID = scan.nextLine();
                            System.out.println("Please input Date Time : yyyy-MM-dd hh:mm (am/pm) ");
                            String dateTime = scan.nextLine();
                            System.out.println("Please input Duration ");
                            int durationPicked = scan.nextInt();
                            insertReservation(con, userID, serviceID, dateTime, durationPicked);
                            userIn = scan.nextLine();
                            break;
                        case "EXIT":
                            System.out.println("Exiting...");
                            break;
                        default:
                            System.out.println("Invalid Command\n");
                    }
                }
            } else {
                System.out.println("Invalid Login - Please Try Again!");
            }
        } while (userIn.compareTo("EXIT") != 0);

        //TODO: ClOSE DB and END CONNECTION PROPERLY ?
    }

}
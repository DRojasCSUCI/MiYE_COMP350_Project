import java.sql.Connection;
import java.sql.*;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Scanner;


public class Test {

    public static Connection connect() {
        Connection conn = null;
        try {

            // db parameters
            String url = "jdbc:sqlite:tempDB";
            // create a connection to the database
            conn = DriverManager.getConnection(url);

            System.out.println("Connection to SQLite has been established.");

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return conn;
    }

    //TODO: MAKE THIS METHOD NAME MORE DESCRIPTIVE
    public static void checkCurrentServices(Connection conn, String id) throws SQLException {

        String sql = "SELECT USERS.USER_ID as \"ID\", USERS.F_NAME AS \"First Name\", USERS.L_NAME AS \"Last Name\", USERS.GENDER AS \"Gender\",USERS_ACTIVE_SERVICES.TIME_STARTED AS \"Start Time\",  SERVICES.SERVICE_NAME AS \"Service\", USERS_ACTIVE_SERVICES.TIME_STARTED AS \"Start Time\", USERS_ACTIVE_SERVICES.DURATION_PICKED AS \"Duration\" FROM USERS \n" +
                "JOIN USERS_ACTIVE_SERVICES ON USERS_ACTIVE_SERVICES.USER_ID = USERS.USER_ID\n" +
                "JOIN SERVICES ON SERVICES.SERVICE_ID = USERS_ACTIVE_SERVICES.SERVICE_ID\n" +
                "WHERE USERS.USER_ID = USERS_ACTIVE_SERVICES.USER_ID AND USERS.USER_ID = ?";


        PreparedStatement pstmt = conn.prepareStatement(sql);

        pstmt.setString(1, id);

        ResultSet rs = pstmt.executeQuery();

        // loop through the result set
        if (!rs.next()) {
            System.out.println("No Reservations Currently Made Under That User");
        } else {
            do {
                System.out.println("\n     ACTIVE RESERVATIONS     ");
                System.out.println("===============================");
                System.out.println(
                        "\n" + rs.getString("ID") + ": " + rs.getString("Last Name") + ", " +
                                rs.getString("First Name") + "\n" +
                                rs.getString("Service") + "\n" + "Date-Time: " + rs.getString("Start Time") +
                                "\t" + "Duration: " + rs.getInt("Duration") + "min\n");
            } while (rs.next());
        }

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

    public static void printReservations(Connection conn) throws SQLException {

        //Preparing the Query
        String sql = "" +
                "SELECT " +
                "U.USER_ID, U.L_NAME, U.F_NAME, S.SERVICE_ID, S.SERVICE_NAME, H.DATE_TIME, H.DURATION_PICKED, H.COST " +
                "FROM SERVICES S " +
                "JOIN USERS_SERVICES_HISTORY H ON S.SERVICE_ID = H.SERVICE_ID " +
                "JOIN USERS U ON H.USER_ID = U.USER_ID " +
                "WHERE H.COMPLETED_FLAG = 0 AND H.CANCELLED_FLAG = 0;";
        PreparedStatement pstmt = conn.prepareStatement(sql);

        //Executing Query
        ResultSet rs = pstmt.executeQuery();

        //Iterating Over Query Results
        if (!rs.next()) {
            System.out.println("No Reservations Currently Made");
        } else {
            System.out.println("\n     RESERVATIONS    ");
            System.out.println("=======================");
            do {
                System.out.println("\n" + rs.getString("USER_ID") + ": " + rs.getString("L_NAME") + ", " +
                        rs.getString("F_NAME") + "\n" + rs.getString("SERVICE_ID") + ": " +
                        rs.getString("SERVICE_NAME") + "\n" + "Date-Time: " + rs.getString("DATE_TIME") +
                        "\t" + "Duration: " + rs.getInt("DURATION_PICKED") + "min\n" + "Total Cost: $" +
                        rs.getFloat("COST")
                );
            } while (rs.next());
        }

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

    public static void cancelReservation(Connection conn, String id) throws SQLException {
        /**
         * Checks if the user has a reservation they are able to cancel. If an applicable
         * reservation is able to be cancelled, then it is cancelled, removed from the database,
         * and the history of the cancelled reservation is recorded in the history table.
         * @param:  conn The database connection
         * @param:  id The user ID
         * @return: None
         */

        // Preparing the Query
        String query = "SELECT * FROM USERS_ACTIVE_SERVICES WHERE USER_ID=" + id + " AND IN_SERVICE_FLAG=1";
        PreparedStatement pstmt = conn.prepareStatement(query);

        // execute the query
        ResultSet rs = pstmt.executeQuery();

        // check if reservation exists and isn't active
        if (!rs.next()) {
            System.out.println("No Reservations Found to be Cancelled");
            return;
        }

        Scanner scan = new Scanner(System.in);
        System.out.println("Type 'DELETE' to delete this reservation. ");
        String input = scan.nextLine();
        if (input.equals("DELETE")) {

            // add deleted reservation to USERS_SERVICES_HISTORY
            query = "INSERT INTO USERS_SERVICES_HISTORY (USER_ID, COMPLETED_FLAG, CANCELLED_FLAG, SERVICE_ID, DATE_TIME, DURATION_PICKED, COST)\n" +
                    "VALUES ('" + rs.getString("USER_ID") + "', '0', '1', '" + rs.getString("SERVICE_ID") + "', '" +
                    rs.getString("TIME_STARTED") + "', '" + rs.getString("DURATION_PICKED") + "', '225')"; //TODO the cost needs to be changed
            pstmt = conn.prepareStatement(query);
            pstmt.executeUpdate();

            // delete the reservation
            System.out.println("RESERVATION DELETED.");
            query = "DELETE FROM USERS_ACTIVE_SERVICES WHERE USER_ID=" + id;
            pstmt = conn.prepareStatement(query);
            pstmt.executeUpdate();
            return;
        }
        System.out.println("Cancellation terminated, returning to application."); // return to main application
    }

    public static boolean authentication(Connection conn, String userID, String password) throws SQLException {
        boolean check = true;

        //Preparing the Query
        String sql = "SELECT ADMIN_FLAG FROM USERS WHERE USERS.user_id = ?";
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

    public static void main(String[] args) throws SQLException {

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
                    System.out.println("\n\n" +
                            "Enter a Two-Character Command From the Available Actions\n" +
                            "[VU]: View Users\n" +
                            "[VS]: View Services\n" +
                            "[VR]: View Reservations\n" +
                            "[UA]: User Active Reservations\n" +
                            "[CR]: Cancel Reservation\n" +
                            "[EXIT]: Exit\n" +
                            "[..]: ...\r" +
                            "Enter Option: "
                    );

                    userIn = scan.nextLine();

                    //Checking Input
                    switch (userIn.toUpperCase()) {
                        case "VU":
                            printUsers(con);
                            break;
                        case "VS":
                            printServices(con);
                            break;
                        case "VR":
                            printReservations(con);
                            break;
                        case "UA":
                            System.out.println("Please input User ID: ");
                            userIn = scan.nextLine();
                            checkCurrentServices(con, userIn);
                            break;
                        case "CR":
                            System.out.println("Please input User ID: ");
                            userIn = scan.nextLine();
                            cancelReservation(con, userIn);
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
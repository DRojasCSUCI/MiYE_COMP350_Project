import java.sql.Connection;
import java.sql.*;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Scanner;

/**
 *
 * @author sqlitetutorial.net
 */
public class Test
{

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
    public static void selectAll(Connection conn, String id) throws SQLException
    {

        String sql = "SELECT USERS.USER_ID as \"ID\", USERS.F_NAME AS \"First Name\", USERS.L_NAME AS \"Last Name\", USERS.GENDER AS \"Gender\", SERVICES.SERVICE_NAME AS \"Service\", USERS_ACTIVE_SERVICES.TIME_STARTED AS \"Start Time\", USERS_ACTIVE_SERVICES.DURATION_PICKED AS \"Duration\" FROM USERS \n" +
                "JOIN USERS_ACTIVE_SERVICES ON USERS_ACTIVE_SERVICES.USER_ID = USERS.USER_ID\n" +
                "JOIN SERVICES ON SERVICES.SERVICE_ID = USERS_ACTIVE_SERVICES.SERVICE_ID\n" +
                "WHERE USERS.USER_ID = USERS_ACTIVE_SERVICES.USER_ID AND USERS.USER_ID = ?";


        PreparedStatement pstmt  = conn.prepareStatement(sql);

        pstmt.setString(1, id);

        ResultSet rs    = pstmt.executeQuery();

        // loop through the result set
        if (!rs.next()){
            System.out.println("FUCK YOU LOSER");
        } else
        {
            do
            {
                System.out.println(
                        rs.getString("ID") + "\t" + rs.getString("First Name") + "\t" + rs.getString("Last Name") + "\t" + rs.getString("Gender")
                                + "\t" + rs.getString("Service"));
            }  while (rs.next());
        }

    }

    public static void printReservations(Connection conn) throws SQLException{

        //Preparing the Query
        String sql = "" +
                "SELECT " +
                "U.USER_ID, U.L_NAME, U.F_NAME, S.SERVICE_ID, S.SERVICE_NAME, H.DATE_TIME, H.DURATION_PICKED, H.COST " +
                "FROM SERVICES S " +
                "JOIN USERS_SERVICES_HISTORY H ON S.SERVICE_ID = H.SERVICE_ID " +
                "JOIN USERS U ON H.USER_ID = U.USER_ID " +
                "WHERE H.COMPLETED_FLAG = 0 AND H.CANCELLED_FLAG = 0;";
        PreparedStatement pstmt  = conn.prepareStatement(sql);

        //Executing Query
        ResultSet rs = pstmt.executeQuery();

        //Iterating Over Query Results
        if (!rs.next()){
            System.out.println("No Reservations Currently Made");
        } else
        {
            System.out.println("\n     RESERVATIONS    ");
            System.out.println("======================");
            do
            {
                System.out.println( "\n" + rs.getString("USER_ID") + ": " + rs.getString("L_NAME") + ", " +
                        rs.getString("F_NAME") + "\n" + rs.getString("SERVICE_ID") + ": " +
                        rs.getString("SERVICE_NAME") + "\n" + "Date-Time: " + rs.getString("DATE_TIME") +
                        "\t" + "Duration: " + rs.getInt("DURATION_PICKED") + "min\n" + "Total Cost: $" +
                        rs.getFloat("COST")
                );
            }  while (rs.next());
        }

    }

    public static void printServices(Connection conn) throws SQLException{

        //Preparing the Query
        String sql = "SELECT * FROM SERVICES;";
        PreparedStatement pstmt  = conn.prepareStatement(sql);

        //Executing Query
        ResultSet rs = pstmt.executeQuery();

        //Iterating Over Query Results
        if (!rs.next()){
            System.out.println("No Services Have Been Added");
        } else
        {
            System.out.println("\n     SERVICES    ");
            System.out.println("==================");
            do
            {
                System.out.println( "\n" + rs.getString("SERVICE_ID") + ": " + rs.getString("SERVICE_NAME") +
                        "\nDescription: " + rs.getString("SERVICE_DESC") + "\nPrice Per Minute: $" +
                        rs.getFloat("PRICE_PER_MINUTE") + "\nDuration Options: " +
                        rs.getString("DURATION_OPTIONS")
                );
            }  while (rs.next());
        }

    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws SQLException
    {

        //Connection to DataBase
        Connection con;
        try {
            con = connect();
        } catch (Exception e){
            System.out.println("\nERROR ON CONNECTING TO SQL DATABASE\n " + e);
            return;
        }

        //Terminal Interface
        String userIn = "";
        Scanner scan = new Scanner(System.in);

        while(userIn.compareTo("EXIT") != 0){

            //Prompt for User Input
            System.out.println( "\n\n" +
                    "Enter a Two-Character Command From the Available Actions\n" +
                    "[VU]: View Users\n" +
                    "[VS]: View Services\n" +
                    "[VR]: View Reservations\n" +
                    "[EXIT]: Exit\n" +
                    "[..]: ..."
            );

            userIn = scan.nextLine();

            //Checking Input
            switch (userIn.toUpperCase()){
                case "VU":
                    //printUsers(con);
                    break;
                case "VS":
                    printServices(con);
                    break;
                case "VR":
                    printReservations(con);
                    break;
                case "...":
                    //...
                    break;
                case "EXIT":
                    System.out.println("Exiting...");
                    break;
                default: System.out.println("Invalid Command\n");
            }

        }

        //TODO: ClOSE DB and END CONNECTION PROPERLY ?

    }


}
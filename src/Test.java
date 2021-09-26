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
    /**
     * Connect to a sample database
     * @return
     */
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

    public static void printCurrentReservations(Connection conn) throws SQLException{

        //Preparing the Query
        String sql = "SELECT * FROM USERS_SERVICES_HISTORY WHERE COMPLETED-FLAG = 0 AND CANCELLED_FLAG = 0";
        PreparedStatement pstmt  = conn.prepareStatement(sql);

        //Executing Query
        ResultSet rs = pstmt.executeQuery();

        //Iterating Over Query Results
        if (!rs.next()){
            System.out.println("No Reservations Currently Made");
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

    public static void printServices(Connection conn) throws SQLException{

        //Preparing the Query
        String sql = "SELECT * FROM SERVICES";
        PreparedStatement pstmt  = conn.prepareStatement(sql);

        //Executing Query
        ResultSet rs = pstmt.executeQuery();

        //Iterating Over Query Results
        if (!rs.next()){
            System.out.println("No Services Have Been Added");
        } else
        {
            do
            {
                System.out.println( rs.getString(0) + "\t" + rs.getString(1) + "\t" + rs.getString(2)
                        + "\t" + rs.getString(3) + "\t" + rs.getString(4) + "\t" + rs.getString(5)
                        + "\t" + rs.getString(6) );
            }  while (rs.next());
        }

    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws SQLException
    {
        Scanner scan = new Scanner(System.in);
        String userIn;

        try{
            Connection con = connect();
            System.out.println("Please enter your fucking id: ");
            userIn = scan.next();
            selectAll(con,userIn);

        }
        catch (SQLException e){

        }


    }
}
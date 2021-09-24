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
                String url = "jdbc:sqlite:C:/Users/Biggy/Desktop/Simple SQLlite Program/Connect/net/Database/DB";
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
                }
                while (rs.next()) {
                    System.out.println(
                            rs.getString("ID") + "\t" + rs.getString("First Name") + "\t" + rs.getString("Last Name")  + "\t" + rs.getString("Gender")
                                    + "\t" + rs.getString("Service"));
                }

        }

        /**
         * @param args the command line arguments
         */
        public static void main(String[] args) throws SQLException
        {
            Scanner scan = new Scanner(System.in);
            System.out.println("Please enter your fuckikng id: ");
            String userIn = scan.next();

            try{
                Connection con = connect();

                selectAll(con,userIn);

            }
                catch (SQLException e){

                }


        }
    }

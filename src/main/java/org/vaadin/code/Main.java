package org.vaadin.code;

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


public class Main {

    public static void main(String[] args) throws SQLException, ParseException {

        //Instantiating Objects Needed for Database Manipulation
        ConnectionManager connMngr = new ConnectionManager();
        UserManager userMngr = new UserManager();
        ServiceManager srvcMngr = new ServiceManager();
        ReservationManager rsrvMngr = new ReservationManager();

        //Connection to DataBase
        Connection con;
        try {
            con = connMngr.connect();
        } catch (Exception e) {
            System.out.println("\nERROR ON CONNECTING TO SQL DATABASE\n " + e);
            return;
        }

        //Terminal Interface
        String userIn;
        String password;

        do {
            Scanner scan = new Scanner(System.in);

            System.out.print("Please Enter Your User Name or \'EXIT\': ");
            userIn = scan.nextLine();

            if(userIn.compareToIgnoreCase("EXIT") == 0)
                return;

            System.out.print("Please Enter Your Password: ");
            password = scan.nextLine();

            if (connMngr.authentication(con, userIn, password)) {

                while (userIn.compareTo("EXIT") != 0) {

                    //Prompt for User Input
                    System.out.println("\n" +
                            "Enter a Multiple-Character Command From the Available Actions\n" +
                            "[VAU]: View All Users\n" +
                            "[VAS]: View All Services\n" +
                            "[VPR]: View Past Reservations\n" +
                            "[VFR]: View Future Reservations\n" +
                            "[VAR]: View All Reservations\n" +
                            "[UPR]: List Past User Reservations\n" +
                            "[UFR]: List Future User Reservations\n" +
                            "[UAR]: List All User Reservations\n" +
                            "[CAR]: Create A Reservation\n" +
                            "[CSR]: Cancel Single Reservation\n" +
                            "[EXIT]: Exit\n" +
                            "[...]: ...\r" +  // '\r' Prevents this line from being printed
                            "Enter Option: "
                    );

                    userIn = scan.nextLine();

                    //Checking Input
                    switch (userIn.toUpperCase()) {
                        case "VAU":
                            userMngr.printUsers(con);
                            break;
                        case "VAS":
                            srvcMngr.printServices(con);
                            break;
                        case "VPR":
                            rsrvMngr.printPastReservations(con);
                            break;
                        case "VFR":
                            rsrvMngr.printFutureReservations(con);
                            break;
                        case "VAR":
                            rsrvMngr.printAllReservations(con);
                            break;
                        case "UPR":
                            System.out.println("Please input User ID: ");
                            userIn = scan.nextLine();
                            rsrvMngr.listPastReservations(con, userIn);
                            break;
                        case "UFR":
                            System.out.println("Please input User ID: ");
                            userIn = scan.nextLine();
                            rsrvMngr.listFutureReservations(con, userIn);
                            break;
                        case "UAR":
                            System.out.println("Please input User ID: ");
                            userIn = scan.nextLine();
                            rsrvMngr.listAllReservations(con, userIn);
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
//                            rsrvMngr.insertReservation(con, userID, serviceID, dateTime, durationPicked);
                            userIn = scan.nextLine();
                            break;
                        case "CSR":
                            System.out.println("Please input User ID: ");
                            userIn = scan.nextLine();
                            rsrvMngr.cancelReservation(con, userIn);
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
        } while (userIn.compareToIgnoreCase("EXIT") != 0);

        //TODO: ClOSE DB and END CONNECTION PROPERLY ?
    }

}

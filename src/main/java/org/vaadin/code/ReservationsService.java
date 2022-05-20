package org.vaadin.code;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;


public class ReservationsService {

    private static ReservationsService instance;
    private static final Logger LOGGER = Logger.getLogger(ReservationsService.class.getName());

    private final HashMap<Long, Reservations> contacts = new HashMap<>();
    private long nextId = 0;

    private ReservationsService() {
    }

    /**
     * @return a reference to an example facade for Reservations objects.
     */
    public static ReservationsService getInstance() throws SQLException {
        if (instance == null) {
            instance = new ReservationsService();
            instance.ensureTestData();
        }
        return instance;
    }

    /**
     * @return all available Reservations objects.
     */
    public synchronized List<Reservations> findAll() {
        return findAll(null);
    }

    /**
     * Finds all Reservations's that match given filter.
     *
     * @param stringFilter
     *            filter that returned objects should match or null/empty string
     *            if all objects should be returned.
     * @return list a Reservations objects
     */
    public synchronized List<Reservations> findAll(String stringFilter) {
        ArrayList<Reservations> arrayList = new ArrayList<>();
        for (Reservations contact : contacts.values()) {
            try {
                boolean passesFilter = (stringFilter == null || stringFilter.isEmpty())
                        || contact.toString().toLowerCase().contains(stringFilter.toLowerCase());
                if (passesFilter) {
                    arrayList.add(contact.clone());
                }
            } catch (CloneNotSupportedException ex) {
                Logger.getLogger(ReservationsService.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        Collections.sort(arrayList, new Comparator<Reservations>() {

            @Override
            public int compare(Reservations o1, Reservations o2) {
                return (int) (o2.getId() - o1.getId());
            }
        });
        return arrayList;
    }

    /**
     * Finds all Reservations's that match given filter and limits the resultset.
     *
     * @param stringFilter
     *            filter that returned objects should match or null/empty string
     *            if all objects should be returned.
     * @param start
     *            the index of first result
     * @param maxresults
     *            maximum result count
     * @return list a Reservations objects
     */
    public synchronized List<Reservations> findAll(String stringFilter, int start, int maxresults) {
        ArrayList<Reservations> arrayList = new ArrayList<>();
        for (Reservations contact : contacts.values()) {
            try {
                boolean passesFilter = (stringFilter == null || stringFilter.isEmpty())
                        || contact.toString().toLowerCase().contains(stringFilter.toLowerCase());
                if (passesFilter) {
                    arrayList.add(contact.clone());
                }
            } catch (CloneNotSupportedException ex) {
                Logger.getLogger(ReservationsService.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        Collections.sort(arrayList, new Comparator<Reservations>() {

            @Override
            public int compare(Reservations o1, Reservations o2) {
                return (int) (o2.getId() - o1.getId());
            }
        });
        int end = start + maxresults;
        if (end > arrayList.size()) {
            end = arrayList.size();
        }
        return arrayList.subList(start, end);
    }

    /**
     * @return the amount of all Reservations in the system
     */
    public synchronized long count() {
        return contacts.size();
    }

    /**
     * Deletes a Reservations from a system
     *
     * @param reservationId
     *            the Reservations to be deleted
     */
    public synchronized void delete(String reservationId) throws SQLException {

        ReservationManager resvMngr = new ReservationManager();

        List<Reservations> list = findAll(reservationId);

        contacts.remove(list.get(0).getId());

        //Todo
        // update database

        // Connection to DataBase
        ConnectionManager connMngr = new ConnectionManager();
        Connection con = null;
        try {
            con = connMngr.connect();
        } catch (Exception e) {
            System.out.println("\nERROR ON CONNECTING TO SQL DATABASE ON DELETE\n " + e);
        }

        System.out.println("Reservation Id: " + reservationId);
        resvMngr.cancelReservation(con, reservationId);

    }

    /**
     * Persists or updates Reservations in the system. Also assigns an identifier
     * for new Reservations instances.
     *
     * @param entry
     */
    public synchronized void save(Reservations entry) {
        if (entry == null) {
            LOGGER.log(Level.SEVERE,
                    "Reservations is null.");
            return;
        }
        if (entry.getId() == null) {
            entry.setId(nextId++);
        }
        try {
            entry = entry.clone();
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }

        contacts.put(entry.getId(), entry);

    }

    /**
     * Populate UI with DataBase entries
     */
    public void ensureTestData() throws SQLException {

        ConnectionManager connMngr = new ConnectionManager();

        // Connection to DataBase
        Connection con = null;
        try {
            con = connMngr.connect();
        } catch (Exception e) {
            System.out.println("\nERROR ON CONNECTING TO SQL DATABASE ON INITIAL POPULATION\n " + e);
        }

        if(con == null)
        {
            System.out.println("------------------------------------------------------------\n" +
                    "------------------------------------------------------------");
        }
        else
        {
            ReservationManager resvMngr = new ReservationManager();

            ResultSet rs = resvMngr.printAllReservations(con);

            while(rs.next())
            {
                // Creating new Reservations
                Reservations c = new Reservations();

                c.setReservationId(rs.getString("RESERVATION_ID"));
                c.setUserId(rs.getString("USER_ID"));
                c.setFirstName(rs.getString("F_NAME"));
                c.setLastName(rs.getString("L_NAME"));
                c.setServiceId(rs.getString("SERVICE_ID"));

                String date = rs.getString("DATE_TIME");
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
                LocalDateTime formattedDate = LocalDateTime.parse(date, formatter);
                c.setDate(formattedDate.toLocalDate());
                c.setTime(formattedDate.toLocalTime());
                switch (c.getServiceId()){
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

                c.setDuration(rs.getInt("DURATION_PICKED"));

                c.setCost(rs.getFloat("COST"));
                System.out.println(c);
                save(c);
            }
            // close the connection
            con.close();

        }

    }
}
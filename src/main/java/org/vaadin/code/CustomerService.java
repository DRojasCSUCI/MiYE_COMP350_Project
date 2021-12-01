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


public class CustomerService {

	private static CustomerService instance;
	private static final Logger LOGGER = Logger.getLogger(CustomerService.class.getName());

	private final HashMap<Long, Customer> contacts = new HashMap<>();
	private long nextId = 0;

	private CustomerService() {
	}

	/**
	 * @return a reference to an example facade for Customer objects.
	 */
	public static CustomerService getInstance() throws SQLException {
		if (instance == null) {
			instance = new CustomerService();
			instance.ensureTestData();
		}
		return instance;
	}

	/**
	 * @return all available Customer objects.
	 */
	public synchronized List<Customer> findAll() {
		return findAll(null);
	}

	/**
	 * Finds all Customer's that match given filter.
	 *
	 * @param stringFilter
	 *            filter that returned objects should match or null/empty string
	 *            if all objects should be returned.
	 * @return list a Customer objects
	 */
	public synchronized List<Customer> findAll(String stringFilter) {
		ArrayList<Customer> arrayList = new ArrayList<>();
		for (Customer contact : contacts.values()) {
			try {
				boolean passesFilter = (stringFilter == null || stringFilter.isEmpty())
						|| contact.toString().toLowerCase().contains(stringFilter.toLowerCase());
				if (passesFilter) {
					arrayList.add(contact.clone());
				}
			} catch (CloneNotSupportedException ex) {
				Logger.getLogger(CustomerService.class.getName()).log(Level.SEVERE, null, ex);
			}
		}
		Collections.sort(arrayList, new Comparator<Customer>() {

			@Override
			public int compare(Customer o1, Customer o2) {
				return (int) (o2.getId() - o1.getId());
			}
		});
		return arrayList;
	}

	/**
	 * Finds all Customer's that match given filter and limits the resultset.
	 *
	 * @param stringFilter
	 *            filter that returned objects should match or null/empty string
	 *            if all objects should be returned.
	 * @param start
	 *            the index of first result
	 * @param maxresults
	 *            maximum result count
	 * @return list a Customer objects
	 */
	public synchronized List<Customer> findAll(String stringFilter, int start, int maxresults) {
		ArrayList<Customer> arrayList = new ArrayList<>();
		for (Customer contact : contacts.values()) {
			try {
				boolean passesFilter = (stringFilter == null || stringFilter.isEmpty())
						|| contact.toString().toLowerCase().contains(stringFilter.toLowerCase());
				if (passesFilter) {
					arrayList.add(contact.clone());
				}
			} catch (CloneNotSupportedException ex) {
				Logger.getLogger(CustomerService.class.getName()).log(Level.SEVERE, null, ex);
			}
		}
		Collections.sort(arrayList, new Comparator<Customer>() {

			@Override
			public int compare(Customer o1, Customer o2) {
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
	 * @return the amount of all customers in the system
	 */
	public synchronized long count() {
		return contacts.size();
	}

	/**
	 * Deletes a customer from a system
	 *
	 * @param value
	 *            the Customer to be deleted
	 */
	public synchronized void delete(Customer value) {

		contacts.remove(value.getId());

		// update database

		// Connection to DataBase
		ConnectionManager connMngr = new ConnectionManager();
		Connection con = null;
		try {
			con = connMngr.connect();
		} catch (Exception e) {
			System.out.println("\nERROR ON CONNECTING TO SQL DATABASE ON DELETE\n " + e);
		}

	}

	/**
	 * Persists or updates customer in the system. Also assigns an identifier
	 * for new Customer instances.
	 *
	 * @param entry
	 */
	public synchronized void save(Customer entry) {
		if (entry == null) {
			LOGGER.log(Level.SEVERE,
					"Customer is null.");
			return;
		}
		if (entry.getId() == null) {
			entry.setId(nextId++);
		}
		try {
			entry = (Customer) entry.clone();
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
			UserManager userMngr = new UserManager();

			ResultSet rs = userMngr.getCustomer(con);

			Random r = new Random(0);

			while(rs.next())
			{
				// Creating new customer
				Customer c = new Customer();

				c.setDatabaseId(rs.getString("USER_ID"));

				c.setFirstName(rs.getString("F_NAME"));
				c.setLastName(rs.getString("L_NAME"));

				String start = rs.getString("DATE_START_OF_STAY");
				String end = rs.getString("DATE_END_OF_STAY");

				DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

				LocalDateTime startTime = LocalDateTime.parse(start, formatter);
				LocalDateTime endTime = LocalDateTime.parse(end, formatter);

				c.setStatus(CustomerStatus.values()[r.nextInt(CustomerStatus.values().length)]);
				c.setStartDate(startTime.toLocalDate());
				c.setStartTime(startTime.toLocalTime());
				c.setEndDate(endTime.toLocalDate());
				c.setEndTime(endTime.toLocalTime());

				save(c);
			}
			// close the connection
			con.close();

		}

	}
}
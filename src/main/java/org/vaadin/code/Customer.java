package org.vaadin.code;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalTime;

/**
 * A entity object, like in any other Java application. In a typical real world
 * application this could for example be a JPA entity.
 */
@SuppressWarnings("serial")
public class Customer implements Serializable, Cloneable {

    private Long id;

    private String userId;

    private String firstName = "";

    private String lastName = "";

    private CustomerStatus status;

    private LocalDate startDate;

    private LocalDate endDate;

    private LocalTime startTime;

    private LocalTime endTime;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUserId() {return userId;}

    public void setUserId(String userId) { this.userId = userId; }

    /**
     * Get the value of status
     *
     * @return the value of status
     */
    public CustomerStatus getStatus() {
        return status;
    }

    /**
     * Set the value of status
     *
     * @param status new value of status
     */
    public void setStatus(CustomerStatus status) {
        this.status = status;
    }

    /**
     * Get the value of startDate
     *
     * @return the value of startDate
     */
    public LocalDate getStartDate() {
        return startDate;
    }


    /**
     * Set the value of startDate
     *
     * @param startDate new value of startDate
     */
    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    /**
     * Get the value of startTime
     *
     * @return the value of startTime
     */
    public LocalTime getStartTime() {return startTime;}

    /**
     * Set the value of endDate
     *
     * @param endDate new value of endDate
     */
    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    /**
     * Get the value of endDate
     *
     * @return the value of endDate
     */
    public LocalDate getEndDate() {
        return endDate;
    }

    /**
     * Set the value of startTime
     *
     * @param startTime new value of startTime
     */
    public void setStartTime(LocalTime startTime) {
        this.startTime = startTime;
    }

    /**
     * Get the value of endTime
     *
     * @return the value of endTime
     */
    public LocalTime getEndTime() {return endTime;}

    /**
     * Set the value of endTime
     *
     * @param endTime new value of endTime
     */
    public void setEndTime(LocalTime endTime) {
        this.endTime = endTime;
    }


    /**
     * Get the value of lastName
     *
     * @return the value of lastName
     */
    public String getLastName() {
        return lastName;
    }

    /**
     * Set the value of lastName
     *
     * @param lastName new value of lastName
     */
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    /**
     * Get the value of firstName
     *
     * @return the value of firstName
     */
    public String getFirstName() {
        return firstName;
    }

    /**
     * Set the value of firstName
     *
     * @param firstName new value of firstName
     */
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public boolean isPersisted() {
        return id != null;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (this.id == null) {
            return false;
        }

        if (obj instanceof Customer && obj.getClass().equals(getClass())) {
            return this.id.equals(((Customer) obj).id);
        }

        return false;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 43 * hash + (id == null ? 0 : id.hashCode());
        return hash;
    }

    @Override
    public Customer clone() throws CloneNotSupportedException {
        return (Customer) super.clone();
    }

    @Override
    public String toString() {
        return firstName + " " + lastName;
    }
}
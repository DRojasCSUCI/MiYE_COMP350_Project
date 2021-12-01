package org.vaadin.code;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalTime;

/**
 * A entity object, like in any other Java application. In a typical real world
 * application this could for example be a JPA entity.
 */
@SuppressWarnings("serial")
public class Reservations implements Serializable, Cloneable {

    private Long id;

    private String reservationId;

    //Todo: Implement ReservationStatus above.
    private ReservationsStatus status;

    private String serviceId;

    private String userId;

    private LocalDate date;

    private String firstName;

    private String lastName;

    private LocalTime time;

    private int duration;

    private float cost;

    private boolean cancelledFlag;

    public int getDuration(){return duration;}

    public void setDuration(int duration){this.duration = duration;}

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getReservationId() {return reservationId;}

    public void setReservationId(String id) { this.reservationId = id; }

    public String getServiceId(){return  serviceId;}

    public void setServiceId(String id){this.serviceId = id;}

    public float getCost(){return cost;}

    public void setCost(float cost){this.cost = cost;}

    public String getUserId(){return userId;}

    public void setUserId(String id){this.userId = id;}

    public boolean getCancelledFlag(){return cancelledFlag;}

    public void setCancelledFlag(boolean flag){this.cancelledFlag = flag;}


    /**
     * Get the value of status
     *
     * @return the value of status
     */
    public ReservationsStatus getStatus() {
        return status;
    }

    /**
     * Set the value of status
     *
     * @param status new value of status
     */
    public void setStatus(ReservationsStatus status) {
        this.status = status;
    }

    /**
     * Get the value of startDate
     *
     * @return the value of startDate
     */
    public LocalDate getDate() {
        return date;
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

    /**
     * Set the value of startDate
     *
     * @param date new value of date
     */
    public void setDate(LocalDate date) {
        this.date = date;
    }

    /**
     * Get the value of startTime
     *
     * @return the value of startTime
     */
    public LocalTime getTime() {return time;}

    /**
     * Set the value of startTime
     *
     * @param time new value of startTime
     */
    public void setTime(LocalTime time) {
        this.time = time;
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

        if (obj instanceof Reservations && obj.getClass().equals(getClass())) {
            return this.id.equals(((Reservations) obj).id);
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
    public Reservations clone() throws CloneNotSupportedException {
        return (Reservations) super.clone();
    }

    @Override
    public String toString() {
        return reservationId + " " +  userId + " " + firstName + " " + lastName;
    }


}
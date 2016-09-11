package uts.edu.aip.dto;

import java.io.Serializable;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

/**
 * @author NQ
 * @version 1.0
 * 
 * This ride class is used for both Driver and Passenger
 * this also has a constraint feature for some fields
 * min/max of the characters and validation messages
 * 
 */
public class Ride implements Serializable{
    private int id;
    private int userId;
    private int vehicleId;
    private String publishDate;
    private String pickupLocation;
    private int availableSeats;
    private boolean status;
    private String pickupTime;
    private String finalDestination;
    private Vehicle vehicle;
    private int bookedBy;

    public Ride() {
    }

    public Ride(int id, int userId, int vehicleId, String publishDate, String pickupLocation, int availableSeats, boolean status, String pickupTime, String finalDestination, Vehicle vehicle) {
        this.id = id;
        this.userId = userId;
        this.vehicleId = vehicleId;
        this.publishDate = publishDate;
        this.pickupLocation = pickupLocation;
        this.availableSeats = availableSeats;
        this.status = status;
        this.pickupTime = pickupTime;
        this.finalDestination = finalDestination;
        this.vehicle = vehicle;
    }
    
     /**
     * The pickup time of the ride which could be used both driver or passenger
     * A pattern constraints user to enter the correct format
     * @return non-null, a pickup time string
     */
    @Pattern (regexp = "([01]?[0-9]|2[0-3]):[0-5][0-9]", message = "The format of the time is HH:mm")
    public String getPickupTime() {
        return pickupTime;
    }

    public void setPickupTime(String pickupTime) {
        this.pickupTime = pickupTime;
    }

    /**
     * The destination of the ride (Driver/Passenger)
     * Driver can enter the final destination which is allowed passenger to know the end point of the ride
     * @return non-null, a final destination string
     */
    @Size(min = 3, max = 20, message="Please enter  at least 3 characters for the final destination address")
    public String getFinalDestination() {
        return finalDestination;
    }

    public void setFinalDestination(String finalDestination) {
        this.finalDestination = finalDestination;
    }

    /**
     * The id of the ride which is automatically generated
     * @return non-null, an id number
     */
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
 
    /**
     * The user id of the ride which is used as the driver id
     * @return non-null, an user id number
     */
    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    /**
     * The vehicle id of the ride which is linked to the id of the vehicle in a ride
     * @return non-null, a vehicle id number
     */
    public int getVehicleId() {
        return vehicleId;
    }

    public void setVehicleId(int vehicleId) {
        this.vehicleId = vehicleId;
    }

    /**
     * The publish date is the date of ride published 
     * @return non-null, a publish date string
     */
    public String getPublishDate() {
        return publishDate;
    }

    public void setPublishDate(String publishDate) {
        this.publishDate = publishDate;
    }

    /**
     * The pickup location where the place is for driver to pick up
     * @return non-null, a pickup location string
     */
    @Size(min = 3, max = 20,message="Please enter at least 3 characters for the pick up location")
    public String getPickupLocation() {
        return pickupLocation;
    }

    public void setPickupLocation(String pickupLocation) {
        this.pickupLocation = pickupLocation;
    }

    /**
     * The number of available seats for passenger
     * @return non-null, a available seat number
     */
    @DecimalMin (value = "1",message = "Please enter at least 1 available seat")
    public int getAvailableSeats() {
        return availableSeats;
    }
    
    public void setAvailableSeats(int availableSeats) {
        this.availableSeats = availableSeats;
    }

     /**
     * The status of the ride
     * @return non-null, a true/false value
     */
    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

     /**
     * The Vehicle object of the vehicle which is 
     * @return non-null, a vehicle object
     */
    public Vehicle getVehicle() {
        return vehicle;
    }

    public void setVehicle(Vehicle vehicle) {
        this.vehicle = vehicle;
    }

    /**
     * A user id which is passenger who is booked for the ride
     * @return a user id
     */
    public int getBookedBy() {
        return bookedBy;
    }

    public void setBookedBy(int bookedBy) {
        this.bookedBy = bookedBy;
    }
}

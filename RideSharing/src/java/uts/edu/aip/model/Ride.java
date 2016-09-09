/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package uts.edu.aip.model;

import java.io.Serializable;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

/**
 *
 * @author NQ
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
    @Pattern (regexp = "([01]?[0-9]|2[0-3]):[0-5][0-9]", message = "The format of the time is HH:mm")
    public String getPickupTime() {
        return pickupTime;
    }

    public void setPickupTime(String pickupTime) {
        this.pickupTime = pickupTime;
    }

    @Size(min = 3, max = 20, message="Please enter  at least 3 characters for the final destination address")
    public String getFinalDestination() {
        return finalDestination;
    }

    public void setFinalDestination(String finalDestination) {
        this.finalDestination = finalDestination;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getVehicleId() {
        return vehicleId;
    }

    public void setVehicleId(int vehicleId) {
        this.vehicleId = vehicleId;
    }

    public String getPublishDate() {
        return publishDate;
    }

    public void setPublishDate(String publishDate) {
        this.publishDate = publishDate;
    }

    @Size(min = 3, max = 20,message="Please enter at least 3 characters for the pick up location")
    public String getPickupLocation() {
        return pickupLocation;
    }

    public void setPickupLocation(String pickupLocation) {
        this.pickupLocation = pickupLocation;
    }

    @DecimalMin (value = "1",message = "Please enter at least 1 available seat")
    public int getAvailableSeats() {
        return availableSeats;
    }
    
    public void setAvailableSeats(int availableSeats) {
        this.availableSeats = availableSeats;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public Vehicle getVehicle() {
        return vehicle;
    }

    public void setVehicle(Vehicle vehicle) {
        this.vehicle = vehicle;
    }

    public int getBookedBy() {
        return bookedBy;
    }

    public void setBookedBy(int bookedBy) {
        this.bookedBy = bookedBy;
    }
}

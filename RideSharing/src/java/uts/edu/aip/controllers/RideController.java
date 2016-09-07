/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package uts.edu.aip.controllers;

import java.io.File;
import java.io.InputStream;
import java.io.Serializable;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.Part;
import uts.edu.aip.dao.RideDAO;
import uts.edu.aip.dao.RideDAOImpl;
import uts.edu.aip.dao.UserDAO;
import uts.edu.aip.dao.UserDAOImpl;
import uts.edu.aip.dao.VehicleDAO;
import uts.edu.aip.dao.VehicleDAOImpl;
import uts.edu.aip.db.SQLUtil;
import uts.edu.aip.model.Ride;
import uts.edu.aip.model.User;
import uts.edu.aip.model.Vehicle;
import uts.edu.aip.utilities.Constant;

/**
 *
 * @author NQ
 */
@Named
@SessionScoped
public class RideController implements Serializable {

    private List<Ride> rides = new ArrayList<Ride>();
    private Ride myRide = new Ride();
    private Vehicle vehicle = new Vehicle();
    private Ride ride = new Ride();
    
    private User user = new User();
    private User passengerInfo = new User();
    private User driverInfo = new User();
    private Part file;
    private String tempFileName;
    
    
    public synchronized void saveImage() {
        
        FacesContext context = FacesContext.getCurrentInstance();
        HttpServletRequest request = (HttpServletRequest)context.getExternalContext().getRequest();
        String uploadDirPath = request.getServletContext().getRealPath("/")+Constant.UPLOAD_DIRECTORY;
        //Create the uploads dir if it does not exist
        this.createUploadDir(uploadDirPath);
        
        //format the file name with initial date format 
        tempFileName = SQLUtil.getInstance().getStringDateByFormat(Constant.INITIAL_DATE_FORMAT_FOR_IMAGE)
                +file.getSubmittedFileName();
        
        try (InputStream input = file.getInputStream()) {
           Files.copy(input, new File(uploadDirPath+"/"+tempFileName).toPath());
        } catch (Exception e) {
            context.addMessage(null, new FacesMessage(e.getMessage()));
        }
    }
    
    private void createUploadDir(String uploadDirPath){
        File path = new File(uploadDirPath);

        if (!path.exists()) {
            path.mkdirs();
        }
    }

    public synchronized String addRide() {
        //add vehicle
        VehicleDAO vehicleDAO = new VehicleDAOImpl();
        
        vehicle.setImage(tempFileName);
        int vehicleId = vehicleDAO.addVehicle(vehicle);

        //add a ride
        RideDAO rideDAO = new RideDAOImpl();
        ride.setStatus(true);
        ride.setUserId(user.getId());
        ride.setVehicleId(vehicleId);
        
        boolean isAddedRide = rideDAO.addRide(ride);

        if (vehicleId!=0 && isAddedRide) {
            return "success";
        } else {
            return "fail";
        }
    }
    
    public String bookRide(Ride ride){
        RideDAO rideDAO = new RideDAOImpl();
        int passengerID = this.getUser().getId();
        rideDAO.bookRide(ride,passengerID);
        return "booked";
    }

    public boolean isAddRide() {
        return this.getUser().getUserType().equals(User.DRIVER) && this.getMyRide().getId()>0;
    }
    
    public boolean isEditable() {
        return this.getMyRide().getId()>0 && this.getPassengerInfo().getId()==0;
    }
    
    public String deleteRide(){
        Ride r = this.getMyRide();
        
        VehicleDAO vehicleDAO = new VehicleDAOImpl();
        vehicleDAO.deleteVehicle(r.getVehicle());
        
        RideDAO rdao = new RideDAOImpl();
        rdao.deleteRide(r);
        
        return "deleted";
    }

    public Vehicle getVehicle() {
        return vehicle;
    }

    public void setVehicle(Vehicle vehicle) {
        this.vehicle = vehicle;
    }

    public Ride getMyRide() {
        RideDAO rideDAO = new RideDAOImpl();
        return rideDAO.getRideIDFromUserID(user.getId());
    }

    public void setMyRide(Ride myRide) {
        this.myRide = myRide;
    }
    
    
     public Ride getRide() {
        return ride;
    }

    public void setRide(Ride ride) {
        this.ride = ride;
    }

    public User getUser() {
        FacesContext context = FacesContext.getCurrentInstance();
        HttpServletRequest request = (HttpServletRequest) context.getExternalContext().getRequest();
        return user = (User) request.getSession().getAttribute("user");
    }

    public List<Ride> getRides() {
        RideDAO rideDAO = new RideDAOImpl();
        return rideDAO.getRides();
    }

    public void setRides(List<Ride> rides) {
        this.rides = rides;
    }

    public String getTempFileName() {
        return tempFileName;
    }

    public void setTempFileName(String tempFileName) {
        this.tempFileName = tempFileName;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Part getFile() {
        return file;
    }

    public void setFile(Part file) {
        this.file = file;
    }

    public User getPassengerInfo() {
        UserDAO userDAO = new UserDAOImpl();
        return userDAO.findUserByID(this.getMyRide().getBookedBy());
    }

    public void setPassengerInfo(User passengerInfo) {
        this.passengerInfo = passengerInfo;
    }
    
    

    public User getDriverInfo() {   
        for (Ride bookedRide : this.getRides()) {
            int passengerBookingID = bookedRide.getBookedBy();
            if(passengerBookingID == this.getUser().getId()){
                UserDAO userDAO = new UserDAOImpl();
                return userDAO.findUserByID(bookedRide.getId());
            }
        }
        return driverInfo;
    }

    public void setDriverInfo(User driverInfo) {
        this.driverInfo = driverInfo;
    }
    
    
}

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
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
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
import uts.edu.aip.utilities.AppUtil;
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
        tempFileName = AppUtil.getInstance().getStringDateByFormat(Constant.INITIAL_DATE_FORMAT_FOR_IMAGE)
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
    
    private boolean validation(){
        if(tempFileName == null){
            AppUtil.getInstance().showError("Please select an image of your vehicle");
            return false;
        }
        if(!AppUtil.getInstance().isValidTime(this.getRide().getPickupTime())){
            AppUtil.getInstance().showError("The pickup time must be later than current time");
            return false;
        }
        return true;
    }

    public synchronized String addRide() {
        // validate fields
        if(!validation())
            return "";
        
        //add vehicle
        vehicle.setImage(tempFileName);
        
        int vehicleId = this.getVehicleId();
        //add a ride
        RideDAO rideDAO = new RideDAOImpl();
        ride.setStatus(true);
        ride.setUserId(user.getId());
        ride.setVehicleId(vehicleId);
        
        try {
            rideDAO.addRide(ride);
            return "success";
        } catch (SQLException ex) {
            AppUtil.getInstance().showError(Constant.SQL_ERROR_MESSAGE);
            Logger.getLogger(RideController.class.getName()).log(Level.SEVERE, null, ex);
            return "";
        }
//        if (vehicleId!=0 && isAddedRide) {
//            return "success";
//        } else {
//            return "";
//        }
    }
    
    public String bookRide(Ride ride){
        RideDAO rideDAO = new RideDAOImpl();
        int passengerID = this.getUser().getId();
        try {
            rideDAO.bookRide(ride,passengerID);
        } catch (SQLException ex) {
            AppUtil.getInstance().showError(Constant.SQL_ERROR_MESSAGE);
            Logger.getLogger(RideController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return "booked";
    }
  
    public String deleteRide(){
        Ride r = this.getMyRide();
        
        VehicleDAO vehicleDAO = new VehicleDAOImpl();
        try {
            vehicleDAO.deleteVehicle(r.getVehicle());
        } catch (SQLException ex) {
            AppUtil.getInstance().showError(Constant.SQL_ERROR_MESSAGE);
            Logger.getLogger(RideController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        RideDAO rdao = new RideDAOImpl();
        try {
            rdao.deleteRide(r);
        } catch (SQLException ex) {
            AppUtil.getInstance().showError(Constant.SQL_ERROR_MESSAGE);
            Logger.getLogger(RideController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return "deleted";
    }
    
    private int getVehicleId(){
        int vehicleId = 0;
        VehicleDAO vehicleDAO = new VehicleDAOImpl();
        try {
            vehicleId =  vehicleDAO.addVehicle(vehicle);
        } catch (SQLException ex) {
            AppUtil.getInstance().showError(Constant.SQL_ERROR_MESSAGE);
            Logger.getLogger(RideController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return vehicleId;
    }

    public Vehicle getVehicle() {
        return vehicle;
    }

    public void setVehicle(Vehicle vehicle) {
        this.vehicle = vehicle;
    }

    public Ride getMyRide() {
        RideDAO rideDAO = new RideDAOImpl();
        try {
            return rideDAO.getRideIDFromUserID(user.getId());
        } catch (SQLException ex) {
            AppUtil.getInstance().showError(Constant.SQL_ERROR_MESSAGE);
            Logger.getLogger(RideController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return new Ride();
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
        try {
            return rideDAO.getRides();
        } catch (SQLException ex) {
            AppUtil.getInstance().showError(Constant.SQL_ERROR_MESSAGE);
            Logger.getLogger(RideController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return new ArrayList<Ride>();
    }
    
    public void checkRedirectPage(){
        FacesContext context = FacesContext.getCurrentInstance();
        
        String outcome = "";
        if (this.getUser().getUserType().equals(Constant.DRIVER_TYPE))
            outcome = "driver";
        else 
            outcome = "passenger";
        context.getApplication().getNavigationHandler().handleNavigation(context, null, outcome);
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
        try {
            return userDAO.findUserByID(this.getMyRide().getBookedBy());
        } catch (SQLException ex) {
            AppUtil.getInstance().showError(Constant.SQL_ERROR_MESSAGE);
            Logger.getLogger(RideController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return new User();
    }

    public void setPassengerInfo(User passengerInfo) {
        this.passengerInfo = passengerInfo;
    }
   
    public User getDriverInfo() {   
        for (Ride bookedRide : this.getRides()) {
            int passengerBookingID = bookedRide.getBookedBy();
            if(passengerBookingID == this.getUser().getId()){
                UserDAO userDAO = new UserDAOImpl();
                try {
                    return userDAO.findUserByID(bookedRide.getId());
                } catch (SQLException ex) {
                    AppUtil.getInstance().showError(Constant.SQL_ERROR_MESSAGE);
                    Logger.getLogger(RideController.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
        return driverInfo;
    }

    public void setDriverInfo(User driverInfo) {
        this.driverInfo = driverInfo;
    }
    
    public boolean isAddRide() {
        return this.getUser().getUserType().equals(User.DRIVER) && this.getMyRide().getId()>0;
    }
    
    public boolean isEditable() {
        return this.getMyRide().getId()>0 && this.getPassengerInfo().getId()==0;
    }
}

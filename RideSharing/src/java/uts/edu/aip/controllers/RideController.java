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
import uts.edu.aip.dto.Ride;
import uts.edu.aip.dto.User;
import uts.edu.aip.dto.Vehicle;
import uts.edu.aip.utilities.Constant;

/**
 *
 * @author NQ
 * @version 1.0
 * A backing bean for the addRide.xhtml and viewRide (Driver and Passenger) Facelet.
 * This backing bean assists with viewing and adding Ride
 * 
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
    //image or photo path
    private Part file;
    private String tempFileName;
    
    /**
     * This saveImage method is used to store the image to the defined folder 
     * and generate the file name with date time format
     */
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
    /**
     * This createUploadDir method is used to create a directory for image 
     * which is uploaded on the server by user
     */
    private void createUploadDir(String uploadDirPath){
        File path = new File(uploadDirPath);

        if (!path.exists()) {
            path.mkdirs();
        }
    }
    
    /**
     * This validation method is used to validate whether the user choose the image or 
     * not, and the pickup time should after the current time
     * @return a status of the validation
     */
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
    
    /**
     * This addRide method is used to add a new ride information by driver which provided by user
     * @return a string action which is used for page navigation purposes
     * 
     */
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
    }
    
    /**
     * This bookRide method is used to assign passenger id into booked by field
     * @return a string action which is used for page navigation purposes
     * 
     */
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
     /** 
     * This deleteRide method is used to remove the vehicle and the ride information
     * @return a string action which is used for page navigation purposes
     */
    public String deleteRide(){
        //get current ride information
        Ride r = this.getMyRide();
        //remove the vehicle information in database
        VehicleDAO vehicleDAO = new VehicleDAOImpl();
        try {
            vehicleDAO.deleteVehicle(r.getVehicle());
        } catch (SQLException ex) {
            AppUtil.getInstance().showError(Constant.SQL_ERROR_MESSAGE);
            Logger.getLogger(RideController.class.getName()).log(Level.SEVERE, null, ex);
        }
        //remove the ride information in database
        RideDAO rdao = new RideDAOImpl();
        try {
            rdao.deleteRide(r);
        } catch (SQLException ex) {
            AppUtil.getInstance().showError(Constant.SQL_ERROR_MESSAGE);
            Logger.getLogger(RideController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return "deleted";
    }
    
    /** 
     * This getVehicleId method is used to add a vehicle information
     * @return a id number of the vehicle
     */
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

    /** 
     * This getMyRide method is used to retrieve current ride of the user
     * @return a ride object
     */
    public Ride getMyRide() {
        RideDAO rideDAO = new RideDAOImpl();
        try {
            return rideDAO.getRideIDFromUserID(user.getId());
        } catch (SQLException ex) {
            AppUtil.getInstance().showError(Constant.SQL_ERROR_MESSAGE);
            Logger.getLogger(RideController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return myRide;
    }

    /** 
     * This getUser method is used to retrieve user info from session
     * @return a user object
     */
    public User getUser() {
        FacesContext context = FacesContext.getCurrentInstance();
        HttpServletRequest request = (HttpServletRequest) context.getExternalContext().getRequest();
        return user = (User) request.getSession().getAttribute("user");
    }

     /** 
     * This getRides method is used to retrieve all available rides
     * @return a list of ride
     */
    public List<Ride> getRides() {
        RideDAO rideDAO = new RideDAOImpl();
        try {
            return rideDAO.getRides();
        } catch (SQLException ex) {
            AppUtil.getInstance().showError(Constant.SQL_ERROR_MESSAGE);
            Logger.getLogger(RideController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return rides;
    }
    
    /** 
     * This checkRedirectPage method is used to 
     * navigate to the correct page based on user type
     * 
     */
    public void checkRedirectPage(){
        FacesContext context = FacesContext.getCurrentInstance();
        
        String outcome = "";
        if (this.getUser().getUserType().equals(Constant.DRIVER_TYPE))
            outcome = "driver";
        else 
            outcome = "passenger";
        context.getApplication().getNavigationHandler().handleNavigation(context, null, outcome);
    }
    
    /** 
     * This getPassengerInfo method is used to 
     * retrieve passenger info
     * @return an user object
     */
    public User getPassengerInfo() {
        UserDAO userDAO = new UserDAOImpl();
        try {
            return userDAO.findUserByID(this.getMyRide().getBookedBy());
        } catch (SQLException ex) {
            AppUtil.getInstance().showError(Constant.SQL_ERROR_MESSAGE);
            Logger.getLogger(RideController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return passengerInfo;
    }
    
    /** 
     * This getDriverInfo method is used to 
     * retrieve driver info
     * @return an user object
     */
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

    public void setPassengerInfo(User passengerInfo) {
        this.passengerInfo = passengerInfo;
    }

    public void setDriverInfo(User driverInfo) {
        this.driverInfo = driverInfo;
    }
    
    public boolean isAddRide() {
        return this.getUser().getUserType().equals(Constant.DRIVER_TYPE) && this.getMyRide().getId()>0;
    }
    
    public boolean isEditable() {
        return this.getMyRide().getId()>0 && this.getPassengerInfo().getId()==0;
    }
    
    public Vehicle getVehicle() {
        return vehicle;
    }

    public void setVehicle(Vehicle vehicle) {
        this.vehicle = vehicle;
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
}

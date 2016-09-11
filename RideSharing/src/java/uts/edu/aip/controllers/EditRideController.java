package uts.edu.aip.controllers;

import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.enterprise.context.RequestScoped;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import javax.servlet.http.HttpServletRequest;
import uts.edu.aip.dao.RideDAO;
import uts.edu.aip.dao.RideDAOImpl;
import uts.edu.aip.dto.Ride;
import uts.edu.aip.dto.User;
import uts.edu.aip.utilities.AppUtil;
import uts.edu.aip.utilities.Constant;

/**
 *
 * @author NQ
 * @version 1.0
 * A backing bean for the editRide.xhtml Facelet. This backing bean assists
 * with editing the ride of the driver
 * 
 */

@Named
@RequestScoped
public class EditRideController {
    private Ride editRide = new Ride();

    //EditRideController constructor which is used to load user object from
    //session and find the ride from user id
    public EditRideController() {
        FacesContext context = FacesContext.getCurrentInstance();
        HttpServletRequest request = (HttpServletRequest) context.getExternalContext().getRequest();
        //retrieve user object from session
        User user = (User) request.getSession().getAttribute("user");
        // find the ride information with the user id
        RideDAO rideDAO = new RideDAOImpl();
         try {
             editRide = rideDAO.getRideIDFromUserID(user.getId());
         } catch (SQLException ex) {
             AppUtil.getInstance().showError(Constant.SQL_ERROR_MESSAGE);
             Logger.getLogger(EditRideController.class.getName()).log(Level.SEVERE, null, ex);
         }
    }
     
    /**
     * This saveRide method is used to save the ride information after editing from the user
     * 
     * @return a string as action to navigate back to view ride page 
     */
      public String saveRide(){
          // validate fields
        if(!validation())
            return "";
        
        //update ride information into database
        RideDAO rdao = new RideDAOImpl();
         try {
             rdao.updateRide(editRide);
             return "success";
         } catch (SQLException ex) {
             AppUtil.getInstance().showError(Constant.SQL_ERROR_MESSAGE);
             Logger.getLogger(EditRideController.class.getName()).log(Level.SEVERE, null, ex);
             return "";
         }
    }
      
     /**
     * This validation method is used to check whether the pickup time 
     * after or before the current time
     * 
     * @return a status of the validation 
     */
      private boolean validation(){
        if(!AppUtil.getInstance().isValidTime(this.getEditRide().getPickupTime())){
            AppUtil.getInstance().showError("The pickup time must be later than current time");
            return false;
        }
        return true;
    }
      
    /**
     * The ride object which is used for editing
     * @return a Ride object
     */
    public Ride getEditRide() {
        return editRide;
    }

    public void setEditRide(Ride editRide) {
        this.editRide = editRide;
    }  
}

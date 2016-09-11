/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
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
import uts.edu.aip.model.Ride;
import uts.edu.aip.model.User;
import uts.edu.aip.utilities.AppUtil;
import uts.edu.aip.utilities.Constant;

/**
 *
 * @author NQ
 */
@Named
@RequestScoped
public class EditRideController {
     private Ride editRide = new Ride();

    public EditRideController() {
        FacesContext context = FacesContext.getCurrentInstance();
        HttpServletRequest request = (HttpServletRequest) context.getExternalContext().getRequest();
        User user = (User) request.getSession().getAttribute("user");
        
        RideDAO rideDAO = new RideDAOImpl();
         try {
             editRide = rideDAO.getRideIDFromUserID(user.getId());
         } catch (SQLException ex) {
             AppUtil.getInstance().showError(Constant.SQL_ERROR_MESSAGE);
             Logger.getLogger(EditRideController.class.getName()).log(Level.SEVERE, null, ex);
         }
    }
     
      public String saveRide(){
          // validate fields
        if(!validation())
            return "";
        
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
      
      private boolean validation(){
        if(!AppUtil.getInstance().isValidTime(this.getEditRide().getPickupTime())){
            AppUtil.getInstance().showError("The pickup time must be later than current time");
            return false;
        }
        return true;
    }
      
    public Ride getEditRide() {
        return editRide;
    }

    public void setEditRide(Ride editRide) {
        this.editRide = editRide;
    }
    
    
    
}

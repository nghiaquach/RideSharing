/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package uts.edu.aip.controllers;

import javax.enterprise.context.RequestScoped;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import javax.servlet.http.HttpServletRequest;
import uts.edu.aip.dao.RideDAO;
import uts.edu.aip.dao.RideDAOImpl;
import uts.edu.aip.dao.VehicleDAO;
import uts.edu.aip.dao.VehicleDAOImpl;
import uts.edu.aip.model.Ride;
import uts.edu.aip.model.User;
import uts.edu.aip.model.Vehicle;

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
        editRide = rideDAO.getRideIDFromUserID(user.getId());
    }
     
      public String saveRide(){
        
        System.out.println("uts.edu.aip.controllers.RideController.saveRide()" + editRide.getFinalDestination());
//        
//        Vehicle v = editRide.getVehicle();
//        VehicleDAO vehicleDAO = new VehicleDAOImpl();
//        vehicleDAO.updateVehicle(v);
//         
        RideDAO rdao = new RideDAOImpl();
        rdao.updateRide(editRide);
         
        return "success";
    }
      
    public Ride getEditRide() {
        return editRide;
    }

    public void setEditRide(Ride editRide) {
        this.editRide = editRide;
    }
    
    
    
}

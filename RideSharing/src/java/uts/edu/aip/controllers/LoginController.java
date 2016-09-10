/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package uts.edu.aip.controllers;

import java.io.Serializable;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.enterprise.context.SessionScoped;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.validation.constraints.Size;
import uts.edu.aip.dao.UserDAO;
import uts.edu.aip.dao.UserDAOImpl;
import uts.edu.aip.model.User;
import uts.edu.aip.utilities.AppUtil;
import uts.edu.aip.utilities.Constant;

/**
 *
 * @author NQ
 */
@Named
@SessionScoped
public class LoginController implements Serializable{
    
    private String username = "";
    private String password = "";
    private User user = new User();
    private boolean isLoggedIn = false;
    private boolean isDriver = false;
    
    public String login(){
        FacesContext context = FacesContext.getCurrentInstance();
        HttpServletRequest request = (HttpServletRequest)context.getExternalContext().getRequest();
        
        try {
            request.login(username, password);
            isLoggedIn = true;
        } catch (ServletException e) {
            Logger.getLogger(LoginController.class.getName()).log(Level.INFO, null, e);
            AppUtil.getInstance().showError(e.getMessage());
            return "";
        }
        
        UserDAO userDAO = new UserDAOImpl();
        user = userDAO.findUser(username);
        request.getSession().setAttribute("user", user);
        
        if (user.getUserType().equals(Constant.DRIVER_TYPE))
                return "driverLoggedIn";
        
        else
                return "passengerLoggedIn";
    }
    
     public String logout(){
        FacesContext context = FacesContext.getCurrentInstance();
        HttpServletRequest request = (HttpServletRequest)context.getExternalContext().getRequest();
        try {
            request.logout();
            //clear session
            context.getExternalContext().invalidateSession();
            user = new User();
            isLoggedIn = false;
            
        } catch (ServletException e) {
            Logger.getLogger(LoginController.class.getName()).log(Level.INFO, null, e);
            AppUtil.getInstance().showError(e.getMessage());
        }
        return "logout";
    }
     
    public String returnMainPage(){
        HttpServletRequest origRequest = (HttpServletRequest)FacesContext.getCurrentInstance().getExternalContext().getRequest();
        
        System.out.println("uts.edu.aip.controllers.LoginController.getRequestURL()" + origRequest.getRequestURL());
        System.out.println("uts.edu.aip.controllers.LoginController.getContextPath()" + origRequest.getContextPath());
        System.out.println("uts.edu.aip.controllers.LoginController.getPathInfo()" + origRequest.getPathInfo());
        return "index";
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public boolean isIsLoggedIn() {
        return isLoggedIn;
    }

    public void setIsLoggedIn(boolean isLoggedIn) {
        this.isLoggedIn = isLoggedIn;
    }

    @Size(min = 3, max = 20, message="The username must have at least 3 characters")
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
    
    @Size(min = 4, max = 20, message="The password must have at least 4 characters")
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isIsDriver() {
        if(this.getUser().getUserType()!=null){
            return this.getUser().getUserType().equals(Constant.DRIVER_TYPE);
        }
        return isDriver;
    }

    public void setIsDriver(boolean isDriver) {
        this.isDriver = isDriver;
    }
}

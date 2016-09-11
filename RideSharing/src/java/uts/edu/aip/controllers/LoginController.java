package uts.edu.aip.controllers;

import java.io.Serializable;
import java.sql.SQLException;
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
import uts.edu.aip.dto.User;
import uts.edu.aip.utilities.AppUtil;
import uts.edu.aip.utilities.Constant;

/**
 *
 * @author NQ
 * @version 1.0
 * A backing bean for the login.xhtml Facelet. This backing bean assists with the 
 * login process
 * 
 */

@Named
@SessionScoped
public class LoginController implements Serializable{
    
    private String username = "";
    private String password = "";
    private User user = new User();
    private boolean isLoggedIn = false;
    private boolean isDriver = false;
    
    /**
     * This login method using Realms to identify user with their username and password
     * Then, username is used to lookup user information and its information is set to session named "user"
     * 
     * @return a string as action to navigate to view ride for passenger or driver 
     */
    public String login(){
        FacesContext context = FacesContext.getCurrentInstance();
        HttpServletRequest request = (HttpServletRequest)context.getExternalContext().getRequest();
        //login using Realms
        try {
            request.login(username, password);
            isLoggedIn = true;
        } catch (ServletException e) {
            //display message if username and password do not match
            Logger.getLogger(LoginController.class.getName()).log(Level.INFO, null, e);
            AppUtil.getInstance().showError("The username and password you entered don't match");
            return "";
        }
        //initialise UserDAO to find User object based on username
        UserDAO userDAO = new UserDAOImpl();
        try {
            user = userDAO.findUser(username);
        } catch (SQLException ex) {
            AppUtil.getInstance().showError(Constant.SQL_ERROR_MESSAGE);
            Logger.getLogger(LoginController.class.getName()).log(Level.SEVERE, null, ex);
        }
        //Assign User object to "user" session 
        request.getSession().setAttribute("user", user);
        //Return a string to navigate to the correct page for user/passenger
        if (user.getUserType()!=null && user.getUserType().equals(Constant.DRIVER_TYPE))
            return "driverLoggedIn";
        
        else
            return "passengerLoggedIn";
    }
    
    
    /**
     * This logout method using Realms to sign out the user account 
     * and remove its session
     * 
     * @return a string as action to navigate to the index page
     */
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

     
    /**
     * Retrieve user information
     * @return a user object
     */
    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

     /**
     * isLoggedIn is used to track the user logged in success or not
     * @return the login status of the user
     */
    public boolean isIsLoggedIn() {
        return isLoggedIn;
    }

    public void setIsLoggedIn(boolean isLoggedIn) {
        this.isLoggedIn = isLoggedIn;
    }

    /**
     * The username which is unique name of the user
     * @return a username string
     */
    @Size(min = 3, max = 20, message="The username must have at least 3 characters")
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
    
    
    /**
     * The password of the user
     * @return a password string
     */
    @Size(min = 4, max = 20, message="The password must have at least 4 characters")
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * isIsDriver is used to check whether the user is driver or not
     * @return a boolean of driver user
     */
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

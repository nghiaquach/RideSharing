package uts.edu.aip.controllers;

import java.io.Serializable;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.enterprise.context.RequestScoped;
import javax.inject.Named;
import uts.edu.aip.dao.UserDAO;
import uts.edu.aip.dao.UserDAOImpl;
import uts.edu.aip.dto.User;
import uts.edu.aip.utilities.AppUtil;
import uts.edu.aip.utilities.Constant;

/**
 *
 * @author NQ
 * @version 1.0
 * A backing bean for the register.xhtml Facelet. This backing bean assists
 * with register a new user
 * 
 */

@Named
@RequestScoped
public class UserController implements Serializable{
    private User user = new User();

    /**
     * 
     * This register method to insert user into database,
     * it also checks if the username existing in database
     * @return a string as action to navigate to the main page 
     * 
     */
    public String register(){
        //check the username if it is existing in current database
        UserDAO userDAO = new UserDAOImpl();
        boolean isExistingUser = this.isExistingUser(userDAO);
        
        //insert user information to database
        if(!isExistingUser){
            try {
                userDAO.addUser(user);
                user = new User();
                return "success";
            } catch (SQLException ex) {
                AppUtil.getInstance().showError(Constant.SQL_ERROR_MESSAGE);
                Logger.getLogger(UserController.class.getName()).log(Level.SEVERE, null, ex);
                return "";
            }
        }
        else{
            AppUtil.getInstance().showError("This username is existing! Please choose another one");
            return "";
        }
    }
    
    /**
     * 
     * This isExistingUser method is used to check the username is existing in database or note
     * @return a boolean value of the validation
     */
    
    private boolean isExistingUser(UserDAO userDAO){
        User searchUser = null;
        try {
            searchUser = userDAO.findUser(user.getUsername());
        } catch (SQLException ex) {
            AppUtil.getInstance().showError(Constant.SQL_ERROR_MESSAGE);
            Logger.getLogger(UserController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return searchUser.getUsername()!=null && searchUser.getUsername().equalsIgnoreCase(user.getUsername());
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    } 
}

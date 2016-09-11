/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package uts.edu.aip.controllers;

import java.io.Serializable;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.enterprise.context.RequestScoped;
import javax.inject.Named;
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
@RequestScoped
public class UserController implements Serializable{
    private User user = new User();

    public String register(){
   
        UserDAO userDAO = new UserDAOImpl();
        boolean isExistingUser = this.isExistingUser(userDAO);
        
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

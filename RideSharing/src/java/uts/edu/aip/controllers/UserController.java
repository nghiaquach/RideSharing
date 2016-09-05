/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package uts.edu.aip.controllers;

import java.io.Serializable;
import javax.enterprise.context.RequestScoped;
import javax.inject.Named;
import uts.edu.aip.dao.UserDAO;
import uts.edu.aip.dao.UserDAOImpl;
import uts.edu.aip.model.User;

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
        boolean status = userDAO.addUser(user);
        if (status){
            user = new User();
            return "success";
        }
        else{
            return "";
        }
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    } 
}

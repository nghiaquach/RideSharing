/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package uts.edu.aip.dao;

import java.util.List;
import uts.edu.aip.model.User;

/**
 *
 * @author NQ
 */
public interface UserDAO {
   public List<User> getUsers();
   public User findUser(String username);
   public User findUserByID(int userID);
   public boolean addUser(User user);
   public boolean updateUser(User user);
}

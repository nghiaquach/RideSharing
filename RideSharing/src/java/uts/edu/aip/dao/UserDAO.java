/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package uts.edu.aip.dao;

import java.sql.SQLException;
import java.util.List;
import uts.edu.aip.model.User;

/**
 *
 * @author NQ
 */
public interface UserDAO {
   public List<User> getUsers() throws SQLException;
   public User findUser(String username) throws SQLException;
   public User findUserByID(int userID) throws SQLException;
   public void addUser(User user) throws SQLException;
   public void updateUser(User user) throws SQLException;
}

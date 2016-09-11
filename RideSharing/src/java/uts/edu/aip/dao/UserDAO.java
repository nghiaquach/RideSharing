package uts.edu.aip.dao;

import java.sql.SQLException;
import java.util.List;
import uts.edu.aip.dto.User;

/**
 *
 * @author NQ
 * @version 1.0
 * 
 * the list of interface methods for UserDAO
 * 
 */
public interface UserDAO {
   public List<User> getUsers() throws SQLException;
   public User findUser(String username) throws SQLException;
   public User findUserByID(int userID) throws SQLException;
   public void addUser(User user) throws SQLException;
   public void updateUser(User user) throws SQLException;
}

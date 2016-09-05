/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package uts.edu.aip.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import uts.edu.aip.db.SQLUtil;
import uts.edu.aip.model.User;

/**
 *
 * @author NQ
 */
public class UserDAOImpl implements UserDAO{

    @Override
    public List<User> getUsers() {
        List<User> users = new ArrayList<>();
        try {
            Connection conn = SQLUtil.getInstance().getConnection();
            Statement stmt = conn.createStatement();
            ResultSet rs;
 
            rs = stmt.executeQuery("SELECT * FROM USERS");
            while ( rs.next() ) {
                User user = new User();
                user.setId(rs.getInt(SQLUtil.ID_FIELD));
                user.setUsername(rs.getString(SQLUtil.USER_NAME_FIELD));
                user.setPassword(rs.getString(SQLUtil.PASSWORD_FIELD));
                user.setFirstName(rs.getString(SQLUtil.FIRST_NAME_FIELD));
                user.setLastName(rs.getString(SQLUtil.LAST_NAME_FIELD));
                user.setPhoneNo(rs.getString(SQLUtil.PHONE_NO_FIELD));
                user.setRegistrationDate(rs.getString(SQLUtil.REGISTRATION_DATE_FIELD));
                users.add(user);
            }
            conn.close();
        } catch (SQLException ex) {
            Logger.getLogger(UserDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return users;
    }

    @Override
    public User findUser(String username) {
        User user = new User();
        try {
            Connection  conn = SQLUtil.getInstance().getConnection();
            Statement stmt = conn.createStatement();
            
            ResultSet rs;
 
            rs = stmt.executeQuery("SELECT * FROM USERS WHERE username='"+username+"'");

            if ( rs.next() ) {
                user.setId(rs.getInt(SQLUtil.ID_FIELD));
                user.setUsername(rs.getString(SQLUtil.USER_NAME_FIELD));
                user.setPassword(rs.getString(SQLUtil.PASSWORD_FIELD));
                user.setFirstName(rs.getString(SQLUtil.FIRST_NAME_FIELD));
                user.setLastName(rs.getString(SQLUtil.LAST_NAME_FIELD));
                user.setPhoneNo(rs.getString(SQLUtil.PHONE_NO_FIELD));
                user.setUserType(rs.getString(SQLUtil.USER_TYPE_FIELD));
                user.setRegistrationDate(rs.getString(SQLUtil.REGISTRATION_DATE_FIELD));
            }
            conn.close();
        } catch (Exception ex) {
            Logger.getLogger(UserDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return user;
    }

    @Override
    public boolean updateUser(User user) {
         try {
            Connection conn = SQLUtil.getInstance().getConnection();
            PreparedStatement ps = 
            conn.prepareStatement( "UPDATE USERS SET USER_NAME = ?, PASSWORD = ?,"
                    + "REGISTRATION_DATE=?, USER_TYPE = ?, FIRS_TNAME = ?, LAST_NAME=?, PHONE_NO = ? "
                    + "WHERE ID='"+ user.getId() +"'");
            ps.setString( 1, user.getUsername());
            ps.setString( 2, user.getPassword());
            ps.setString( 3, user.getRegistrationDate());
            ps.setString( 4, user.getUserType());
            ps.setString( 5, user.getFirstName());
            ps.setString( 6, user.getLastName());
            ps.setString( 7, user.getPhoneNo());
            ps.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(UserDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
        return true;
    }

    @Override
    public boolean addUser(User user) {
        int id = this.getLastId();
        try {
            Connection  conn = SQLUtil.getInstance().getConnection();
            
            PreparedStatement ps = 
            conn.prepareStatement( "INSERT INTO USERS VALUES( ?,?,?,?,?,?,?,? )" );
            ps.setInt(1, id);
            ps.setString( 2, user.getUsername());
            ps.setString( 3, SQLUtil.hash256(user.getPassword()));
            ps.setString( 4, user.getUserType());
            ps.setString( 5, user.getFirstName());
            ps.setString( 6, user.getLastName());
            ps.setString( 7, user.getPhoneNo());
            ps.setString( 8, SQLUtil.getInstance().getStringDate());
            ps.executeUpdate();
            
            conn.close();
        } catch (Exception ex) {
            Logger.getLogger(UserDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
        return true;
    }
    
    private int getLastId (){
        int id = 1;
        try {
            
            Connection  conn = SQLUtil.getInstance().getConnection();
            Statement stmt = conn.createStatement();
            ResultSet rs;
 
            rs = stmt.executeQuery("SELECT MAX(ID) FROM USERS");
            if ( rs.next() ) {
                id = rs.getInt(1);
                ++id;
            }
            stmt.close();
            rs.close();
        } catch (Exception ex) {
            Logger.getLogger(UserDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return id;
    }
}

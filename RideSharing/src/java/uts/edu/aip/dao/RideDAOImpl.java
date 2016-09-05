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
import uts.edu.aip.model.Ride;
import uts.edu.aip.model.Vehicle;

/**
 *
 * @author NQ
 */
public class RideDAOImpl implements RideDAO{

    @Override
    public List<Ride> getRides() {
        List<Ride> rides = new ArrayList<>();
        try {
            Connection conn = SQLUtil.getInstance().getConnection();
            Statement stmt = conn.createStatement();
            ResultSet rs;
 
            rs = stmt.executeQuery("SELECT * FROM USER_VEHICLE");
            while ( rs.next() ) {
                Ride ride = new Ride();
                ride.setId(rs.getInt(SQLUtil.ID_FIELD));
                ride.setUserId(rs.getInt(SQLUtil.USER_ID_FIELD));
                ride.setVehicleId(rs.getInt(SQLUtil.VEHICLE_ID_FIELD));
                ride.setAvailableSeats(rs.getInt(SQLUtil.AVAILABLE_SEATS_FIELD));
                ride.setPickupLocation(rs.getString(SQLUtil.PICKUP_LOCATION_FIELD));
                ride.setPublishDate(rs.getString(SQLUtil.PUBLISH_DATE_FIELD));
                ride.setStatus(rs.getBoolean(SQLUtil.STATUS_FIELD));
                ride.setPickupTime(rs.getString(SQLUtil.PICKUP_TIME_FIELD));
                ride.setFinalDestination(rs.getString(SQLUtil.FINAL_DESTINATION_FIELD));
                
                //Get Vehicle from vehice id
                ride.setVehicle(this.getVehicle(ride.getVehicleId()));
                
                rides.add(ride);
            }
            stmt.close();
            conn.close();
        } catch (SQLException ex) {
            Logger.getLogger(UserDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return rides;
    }
    
    private Vehicle getVehicle(int vehicleID){
         //Get Vehicle from vehice id
        VehicleDAO vehicleDAO = new VehicleDAOImpl();
        return vehicleDAO.findVehicle(vehicleID);  
    }

    @Override
    public Ride getRide(int rideID) {
        Ride ride = new Ride();
        try {
            Connection conn = SQLUtil.getInstance().getConnection();
            Statement stmt = conn.createStatement();
            ResultSet rs;
 
            rs = stmt.executeQuery("SELECT * FROM USER_VEHICLE WHERE ID="+rideID);
            
            while ( rs.next() ) {
                ride.setId(rs.getInt(SQLUtil.ID_FIELD));
                ride.setAvailableSeats(rs.getInt(SQLUtil.AVAILABLE_SEATS_FIELD));
                ride.setPickupLocation(rs.getString(SQLUtil.PICKUP_LOCATION_FIELD));
                ride.setPublishDate(rs.getString(SQLUtil.PUBLISH_DATE_FIELD));
                ride.setStatus(rs.getBoolean(SQLUtil.STATUS_FIELD));
                ride.setUserId(rs.getInt(SQLUtil.USER_ID_FIELD));
                ride.setVehicleId(rs.getInt(SQLUtil.VEHICLE_ID_FIELD));
                ride.setFinalDestination(rs.getString(SQLUtil.FINAL_DESTINATION_FIELD));
                ride.setPickupTime(rs.getString(SQLUtil.PICKUP_TIME_FIELD));
                
                //Get Vehicle from vehice id
                ride.setVehicle(this.getVehicle(ride.getVehicleId()));
                
            }
            conn.close();
        } catch (SQLException ex) {
            Logger.getLogger(UserDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return ride;
    }
    
    @Override
    public Ride getRideIDFromUserID(int userID) {
        Ride ride = new Ride();
        try {
            Connection conn = SQLUtil.getInstance().getConnection();
            Statement stmt = conn.createStatement();
            ResultSet rs;
 
            rs = stmt.executeQuery("SELECT * FROM USER_VEHICLE WHERE USER_ID="+userID);
            
            while ( rs.next() ) {
                ride.setId(rs.getInt(SQLUtil.ID_FIELD));
                ride.setAvailableSeats(rs.getInt(SQLUtil.AVAILABLE_SEATS_FIELD));
                ride.setPickupLocation(rs.getString(SQLUtil.PICKUP_LOCATION_FIELD));
                ride.setPublishDate(rs.getString(SQLUtil.PUBLISH_DATE_FIELD));
                ride.setStatus(rs.getBoolean(SQLUtil.STATUS_FIELD));
                ride.setUserId(rs.getInt(SQLUtil.USER_ID_FIELD));
                ride.setVehicleId(rs.getInt(SQLUtil.VEHICLE_ID_FIELD));
                ride.setFinalDestination(rs.getString(SQLUtil.FINAL_DESTINATION_FIELD));
                ride.setPickupTime(rs.getString(SQLUtil.PICKUP_TIME_FIELD));
                
                //Get Vehicle from vehice id
                ride.setVehicle(this.getVehicle(ride.getVehicleId()));
                
            }
            conn.close();
        } catch (SQLException ex) {
            Logger.getLogger(UserDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return ride;
    }

    @Override
    public boolean addRide(Ride ride) {
        int id = this.getLastId();
        try {
            Connection conn = SQLUtil.getInstance().getConnection();
            PreparedStatement ps = 
            conn.prepareStatement( "INSERT INTO USER_VEHICLE VALUES( ?,?,?,?,?,?,?,?,? )" );
            ps.setInt(1, ride.getVehicleId());
            ps.setString(2, ride.getPickupLocation());
            ps.setBoolean( 3, ride.isStatus());
            ps.setInt(4, ride.getAvailableSeats());
            ps.setInt(5, id);
            ps.setInt(6, ride.getUserId());
            ps.setString(7, SQLUtil.getInstance().getStringDate());
            ps.setString(8, ride.getPickupTime());
            ps.setString(9, ride.getFinalDestination());
            
            ps.executeUpdate();
            
            ps.close();
            conn.close();
        } catch (SQLException ex) {
            Logger.getLogger(UserDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
        return true;
    }

    @Override
    public boolean updateRide(Ride ride) {
        try {
            Connection conn = SQLUtil.getInstance().getConnection();
            PreparedStatement ps = 
            conn.prepareStatement( "UPDATE USER_VEHICLE SET USER_ID = ?, "
                    + "VEHICLE_ID = ? , PICKUP_LOCATION = ?  , PUBLISH_DATE = ? "
                    + " , STATUS = ? , AVAILABLE_SEATS= ?,PICKUP_TIME= ? ,FINAL_DESTINATION= ?"
                    + "WHERE ID='"+ ride.getId() +"'");
            ps.setInt(1, ride.getUserId());
            ps.setInt(2, ride.getVehicleId());
            ps.setString(3, ride.getPickupLocation());
            ps.setString(4, ride.getPublishDate());
            ps.setBoolean(5, ride.isStatus());
            ps.setInt(6, ride.getAvailableSeats());
            ps.setString(7, ride.getPickupTime());
            ps.setString(8, ride.getFinalDestination());
            
            ps.executeUpdate();
           
            ps.close();
            conn.close();
        } catch (SQLException ex) {
            Logger.getLogger(UserDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
        return true;
    }

    @Override
    public boolean deleteRide(Ride ride) {
        try {
            Connection conn = SQLUtil.getInstance().getConnection();
            Statement stmt = conn.createStatement();
            stmt.executeUpdate("DELETE FROM USER_VEHICLE WHERE ID='"+ride.getId()+"'");
            
            conn.close();
        } catch (SQLException ex) {
            Logger.getLogger(UserDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
        return true;
    }
    
    private int getLastId (){
        int id = 1;
        try {
            Connection conn = SQLUtil.getInstance().getConnection();
            ResultSet rs;
            try (Statement stmt = conn.createStatement()) {
                rs = stmt.executeQuery("SELECT MAX(ID) FROM USER_VEHICLE");
                if ( rs.next() ) {
                    id = rs.getInt(1);
                    ++id;
                }
            }
            rs.close();
            conn.close();
        } catch (SQLException ex) {
            Logger.getLogger(UserDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return id;
    } 
}

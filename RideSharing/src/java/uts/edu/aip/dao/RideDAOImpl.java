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
                
                rides.add(ride);
            }
            conn.close();
        } catch (SQLException ex) {
            Logger.getLogger(UserDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return rides;
    }

    @Override
    public Ride getRide(int trasactionId) {
        Ride ride = new Ride();
        try {
            Connection conn = SQLUtil.getInstance().getConnection();
            
            String selectSQL = "SELECT * FROM USER_VEHICLE WHERE ID=?";
            
            PreparedStatement preparedStatement = conn.prepareStatement(selectSQL);
            preparedStatement.setInt(1, trasactionId);
            ResultSet rs = preparedStatement.executeQuery(selectSQL);
            
            while ( rs.next() ) {
                ride.setId(rs.getInt(SQLUtil.ID_FIELD));
                ride.setAvailableSeats(rs.getInt(SQLUtil.AVAILABLE_SEATS_FIELD));
                ride.setPickupLocation(rs.getString(SQLUtil.PICKUP_LOCATION_FIELD));
                ride.setPublishDate(rs.getString(SQLUtil.PUBLISH_DATE_FIELD));
                ride.setStatus(rs.getBoolean(SQLUtil.STATUS_FIELD));
                ride.setUserId(rs.getInt(SQLUtil.USER_ID_FIELD));
                ride.setVehicleId(rs.getInt(SQLUtil.VEHICLE_ID_FIELD));
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
            conn.prepareStatement( "INSERT INTO USER_VEHICLE VALUES( ?,?,?,?,?,?,? )" );
            ps.setInt(1, ride.getVehicleId());
            ps.setString(2, ride.getPickupLocation());
            ps.setBoolean( 3, ride.isStatus());
            ps.setInt(4, ride.getAvailableSeats());
            ps.setInt(5, ride.getId());
            ps.setInt(6, ride.getUserId());
            ps.setString(7, ride.getPublishDate());
            
            ps.executeUpdate();
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
                    + " , STATUS = ? , AVAILABLE SEATS= ?  "
                    + "WHERE ID='"+ ride.getId() +"'");
            ps.setInt(1, ride.getUserId());
            ps.setInt(2, ride.getVehicleId());
            ps.setString(3, ride.getPickupLocation());
            ps.setString(4, ride.getPublishDate());
            ps.setBoolean(5, ride.isStatus());
            ps.setInt(6, ride.getAvailableSeats());
            
            ps.executeUpdate();
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
                rs = stmt.executeQuery("SELECT MAX(ID) FROM VEHICLES");
                if ( rs.next() ) {
                    id = rs.getInt(1);
                    ++id;
                }
            }
            rs.close();
        } catch (SQLException ex) {
            Logger.getLogger(UserDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return id;
    }
    
}

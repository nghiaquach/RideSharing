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
import uts.edu.aip.model.Vehicle;

/**
 *
 * @author NQ
 */
public class VehicleDAOImpl implements VehicleDAO{

    @Override
    public List<Vehicle> getAllVehicle() {
        List<Vehicle> vehicles = new ArrayList<>();
        try {
            Connection conn = SQLUtil.getInstance().getConnection();
            Statement stmt = conn.createStatement();
            ResultSet rs;
 
            rs = stmt.executeQuery("SELECT * FROM VEHICLES");
            while ( rs.next() ) {
                Vehicle vehicle = new Vehicle();
                vehicle.setId(rs.getInt(SQLUtil.ID_FIELD));
                vehicle.setModel(rs.getString(SQLUtil.MODEL_FIELD));
                vehicle.setImage(rs.getBytes(SQLUtil.IMAGE_FIELD));
                
                vehicles.add(vehicle);
            }
            conn.close();
        } catch (SQLException ex) {
            Logger.getLogger(UserDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return vehicles;
    }

    @Override
    public Vehicle findVehicle(int vehicleId) {
        Vehicle vehicle = new Vehicle();
        try {
            
            Connection conn = SQLUtil.getInstance().getConnection();
            
            String selectSQL = "SELECT * FROM VEHICLES WHERE ID=?";
            
            PreparedStatement preparedStatement = conn.prepareStatement(selectSQL);
            preparedStatement.setInt(1, vehicleId);
            ResultSet rs = preparedStatement.executeQuery(selectSQL);
            
            while ( rs.next() ) {
                vehicle.setId(rs.getInt(SQLUtil.ID_FIELD));
                vehicle.setModel(rs.getString(SQLUtil.MODEL_FIELD));
                vehicle.setImage(rs.getBytes(SQLUtil.IMAGE_FIELD));
            }
            conn.close();
        } catch (SQLException ex) {
            Logger.getLogger(UserDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return vehicle;
    }

    @Override
    public boolean updateVehicle(Vehicle vehicle) {
        try {
            Connection conn = SQLUtil.getInstance().getConnection();
            PreparedStatement ps = 
            conn.prepareStatement( "UPDATE VEHICLES SET MODEL = ?, IMAGE = ? "
                    + "WHERE ID='"+ vehicle.getId() +"'");
            ps.setString( 1, vehicle.getModel());
            ps.setBytes(2, vehicle.getImage());
            ps.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(UserDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
        return true;
    }

    @Override
    public boolean deleteVehicle(Vehicle vehicle) {
        try {
            Connection conn = SQLUtil.getInstance().getConnection();
            Statement stmt = conn.createStatement();
            stmt.executeUpdate("DELETE FROM VEHICLES WHERE ID='"+vehicle.getId()+"'");
            
            conn.close();
        } catch (SQLException ex) {
            Logger.getLogger(UserDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
        return true;
    }

    @Override
    public boolean addVehicle(Vehicle vehicle) {
        try {
            Connection conn = SQLUtil.getInstance().getConnection();
            PreparedStatement ps = 
            conn.prepareStatement( "INSERT INTO VEHICLES VALUES( ?,?,? )" );
            ps.setInt(1, vehicle.getId());
            ps.setString( 2, vehicle.getModel());
            ps.setBytes(3, vehicle.getImage());
            ps.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(UserDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
        return true;
    }
    
}

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
import uts.edu.aip.utilities.AppUtil;
import uts.edu.aip.model.Vehicle;
import uts.edu.aip.utilities.Constant;

/**
 *
 * @author NQ
 */
public class VehicleDAOImpl implements VehicleDAO{

    @Override
    public List<Vehicle> getAllVehicle() throws SQLException{
        List<Vehicle> vehicles = new ArrayList<>();
        
        try (Connection conn = AppUtil.getInstance().getConnection();
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT id, model, image FROM VEHICLES")){
            
            while ( rs.next() ) {
                Vehicle vehicle = new Vehicle();
                vehicle.setId(rs.getInt(Constant.ID_FIELD));
                vehicle.setModel(rs.getString(Constant.MODEL_FIELD));
                vehicle.setImage(rs.getString(Constant.IMAGE_FIELD));
                
                vehicles.add(vehicle);
            }
        }
        
        return vehicles;
    }

    @Override
    public Vehicle findVehicle(int vehicleId) throws SQLException{
        Vehicle vehicle = new Vehicle();
        try (
            Connection conn = AppUtil.getInstance().getConnection();
            Statement stmt = conn.createStatement();
            ResultSet rs= stmt.executeQuery("SELECT id, model, image FROM VEHICLES WHERE ID="+vehicleId)){
            
            while ( rs.next() ) {
                vehicle.setId(rs.getInt(Constant.ID_FIELD));
                vehicle.setModel(rs.getString(Constant.MODEL_FIELD));
                vehicle.setImage(rs.getString(Constant.IMAGE_FIELD));
            }
        } 
        return vehicle;
    }

    @Override
    public void updateVehicle(Vehicle vehicle) throws SQLException{
        try (
            Connection conn = AppUtil.getInstance().getConnection();
            PreparedStatement ps = 
            conn.prepareStatement( "UPDATE VEHICLES SET MODEL = ?, IMAGE = ? "
                    + "WHERE ID="+ vehicle.getId())){
            ps.setString( 1, vehicle.getModel());
            ps.setString(2, vehicle.getImage());
            ps.executeUpdate();
        }
    }

    @Override
    public void deleteVehicle(Vehicle vehicle) throws SQLException{
        try (
            Connection conn = AppUtil.getInstance().getConnection();
            Statement stmt = conn.createStatement()){
            stmt.executeUpdate("DELETE FROM VEHICLES WHERE ID="+vehicle.getId());
        } 
    }

    @Override
    public int addVehicle(Vehicle vehicle) throws SQLException{
        int id = this.getLastId();
        try (
            Connection conn = AppUtil.getInstance().getConnection();
            PreparedStatement ps = 
            conn.prepareStatement( "INSERT INTO VEHICLES VALUES( ?,?,? )" )){
            ps.setInt(1, id);
            ps.setString( 2, vehicle.getModel());
            ps.setString(3, vehicle.getImage());
            ps.executeUpdate();
            return id;
        }
    }
    
    private int getLastId () throws SQLException{
        int id = 1;
        try (
            Connection  conn = AppUtil.getInstance().getConnection();
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT MAX(ID) FROM VEHICLES")){
            if ( rs.next() ) {
                id = rs.getInt(1);
                ++id;
            }
            return id;
        }
    }
}

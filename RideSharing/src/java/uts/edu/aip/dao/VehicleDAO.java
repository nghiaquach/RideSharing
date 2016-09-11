/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package uts.edu.aip.dao;

import java.sql.SQLException;
import java.util.List;
import uts.edu.aip.model.Vehicle;

/**
 *
 * @author NQ
 */
public interface VehicleDAO {
   public List<Vehicle> getAllVehicle() throws SQLException;
   public Vehicle findVehicle(int vehicleId) throws SQLException;
   public int addVehicle(Vehicle vehicle) throws SQLException;
   public void updateVehicle(Vehicle vehicle) throws SQLException;
   public void deleteVehicle(Vehicle vehicle) throws SQLException;
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package uts.edu.aip.dao;

import java.util.List;
import uts.edu.aip.model.Vehicle;

/**
 *
 * @author NQ
 */
public interface VehicleDAO {
   public List<Vehicle> getVehicles();
   public Vehicle getVehicle(int vehicleId);
   public boolean addVehicle(Vehicle vehicle);
   public boolean updateVehicle(Vehicle vehicle);
   public boolean deleteVehicle(Vehicle vehicle);
}

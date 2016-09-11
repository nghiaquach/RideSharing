package uts.edu.aip.dao;

import java.sql.SQLException;
import java.util.List;
import uts.edu.aip.dto.Vehicle;

 /**
 *
 * @author NQ
 * @version 1.0
 * 
 * the list of interface methods for VehicleDAO
 * 
 */
public interface VehicleDAO {
   public List<Vehicle> getAllVehicle() throws SQLException;
   public Vehicle findVehicle(int vehicleId) throws SQLException;
   public int addVehicle(Vehicle vehicle) throws SQLException;
   public void updateVehicle(Vehicle vehicle) throws SQLException;
   public void deleteVehicle(Vehicle vehicle) throws SQLException;
}

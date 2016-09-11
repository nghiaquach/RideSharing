/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package uts.edu.aip.dao;

import java.sql.SQLException;
import java.util.List;
import uts.edu.aip.model.Ride;

/**
 *
 * @author NQ
 */
public interface RideDAO {
   public List<Ride> getRides() throws SQLException;
   public Ride getRide(int rideId) throws SQLException;
   public void addRide(Ride ride) throws SQLException;
   public void updateRide(Ride ride) throws SQLException;
   public void deleteRide(Ride ride) throws SQLException;
   public Ride getRideIDFromUserID(int userID) throws SQLException;
   public void bookRide(Ride ride, int passengerID) throws SQLException;
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package uts.edu.aip.dao;

import java.util.List;
import uts.edu.aip.model.Ride;

/**
 *
 * @author NQ
 */
public interface RideDAO {
   public List<Ride> getRides();
   public Ride getRide(int rideId);
   public boolean addRide(Ride ride);
   public boolean updateRide(Ride ride);
   public boolean deleteRide(Ride ride);
   public Ride getRideIDFromUserID(int userID);
   public boolean bookRide(Ride ride, int passengerID);
}

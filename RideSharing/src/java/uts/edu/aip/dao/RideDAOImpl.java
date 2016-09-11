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
import uts.edu.aip.model.Ride;
import uts.edu.aip.model.Vehicle;
import uts.edu.aip.utilities.Constant;

/**
 *
 * @author NQ
 */
public class RideDAOImpl implements RideDAO {

    @Override
    public List<Ride> getRides() throws SQLException {
        List<Ride> rides = new ArrayList<>();
        try (
                Connection conn = AppUtil.getInstance().getConnection();
                Statement stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery("SELECT id,user_id,vehicle_id,available_seats,"
                        + "pickup_location,publish_date,status,pickup_time,final_destination,booked_by FROM USER_VEHICLE")) {

            while (rs.next()) {
                Ride ride = new Ride();
                ride.setId(rs.getInt(Constant.ID_FIELD));
                ride.setUserId(rs.getInt(Constant.USER_ID_FIELD));
                ride.setVehicleId(rs.getInt(Constant.VEHICLE_ID_FIELD));
                ride.setAvailableSeats(rs.getInt(Constant.AVAILABLE_SEATS_FIELD));
                ride.setPickupLocation(rs.getString(Constant.PICKUP_LOCATION_FIELD));
                ride.setPublishDate(rs.getString(Constant.PUBLISH_DATE_FIELD));
                ride.setStatus(rs.getBoolean(Constant.STATUS_FIELD));
                ride.setPickupTime(rs.getString(Constant.PICKUP_TIME_FIELD));
                ride.setFinalDestination(rs.getString(Constant.FINAL_DESTINATION_FIELD));
                ride.setBookedBy(rs.getInt(Constant.BOOKED_BY_FIELD));

                //Get Vehicle from vehice id
                ride.setVehicle(this.getVehicle(ride.getVehicleId()));
                rides.add(ride);
            }
            return rides;
        }
    }

    private Vehicle getVehicle(int vehicleID) throws SQLException {
        VehicleDAO vehicleDAO = new VehicleDAOImpl();
        return vehicleDAO.findVehicle(vehicleID);
    }

    @Override
    public Ride getRide(int rideID) throws SQLException {
        Ride ride = new Ride();
        try (Connection conn = AppUtil.getInstance().getConnection();
                Statement stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery("SELECT id,user_id,vehicle_id,available_seats,"
                        + "pickup_location,publish_date,status,pickup_time,final_destination,booked_by FROM USER_VEHICLE WHERE ID=" + rideID)) {

            if (rs.next()) {
                ride.setId(rs.getInt(Constant.ID_FIELD));
                ride.setAvailableSeats(rs.getInt(Constant.AVAILABLE_SEATS_FIELD));
                ride.setPickupLocation(rs.getString(Constant.PICKUP_LOCATION_FIELD));
                ride.setPublishDate(rs.getString(Constant.PUBLISH_DATE_FIELD));
                ride.setStatus(rs.getBoolean(Constant.STATUS_FIELD));
                ride.setUserId(rs.getInt(Constant.USER_ID_FIELD));
                ride.setVehicleId(rs.getInt(Constant.VEHICLE_ID_FIELD));
                ride.setFinalDestination(rs.getString(Constant.FINAL_DESTINATION_FIELD));
                ride.setPickupTime(rs.getString(Constant.PICKUP_TIME_FIELD));
                ride.setBookedBy(rs.getInt(Constant.BOOKED_BY_FIELD));

                //Get Vehicle from vehice id
                ride.setVehicle(this.getVehicle(ride.getVehicleId()));
            }
            return ride;
        }
    }

    @Override
    public Ride getRideIDFromUserID(int userID) throws SQLException {
        Ride ride = new Ride();
        try (
                Connection conn = AppUtil.getInstance().getConnection();
                Statement stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery("SELECT id,user_id,vehicle_id,available_seats,"
                        + "pickup_location,publish_date,status,pickup_time,final_destination,booked_by FROM USER_VEHICLE WHERE USER_ID=" + userID)) {

            if (rs.next()) {
                ride.setId(rs.getInt(Constant.ID_FIELD));
                ride.setAvailableSeats(rs.getInt(Constant.AVAILABLE_SEATS_FIELD));
                ride.setPickupLocation(rs.getString(Constant.PICKUP_LOCATION_FIELD));
                ride.setPublishDate(rs.getString(Constant.PUBLISH_DATE_FIELD));
                ride.setStatus(rs.getBoolean(Constant.STATUS_FIELD));
                ride.setUserId(rs.getInt(Constant.USER_ID_FIELD));
                ride.setVehicleId(rs.getInt(Constant.VEHICLE_ID_FIELD));
                ride.setFinalDestination(rs.getString(Constant.FINAL_DESTINATION_FIELD));
                ride.setPickupTime(rs.getString(Constant.PICKUP_TIME_FIELD));
                ride.setBookedBy(rs.getInt(Constant.BOOKED_BY_FIELD));

                //Get Vehicle from vehice id
                ride.setVehicle(this.getVehicle(ride.getVehicleId()));
            }
            return ride;
        }
    }

    @Override
    public void addRide(Ride ride) throws SQLException {
        int id = this.getLastId();
        try (
                Connection conn = AppUtil.getInstance().getConnection();
                PreparedStatement ps
                = conn.prepareStatement("INSERT INTO USER_VEHICLE VALUES( ?,?,?,?,?,?,?,?,?,? )")) {
            ps.setInt(1, ride.getVehicleId());
            ps.setString(2, ride.getPickupLocation());
            ps.setInt(3, ride.getAvailableSeats());
            ps.setInt(4, id);
            ps.setInt(5, ride.getUserId());
            ps.setString(6, AppUtil.getInstance().getStringDate());
            ps.setString(7, ride.getPickupTime());
            ps.setString(8, ride.getFinalDestination());
            ps.setInt(9, ride.getBookedBy());
            ps.setBoolean(10, ride.isStatus());

            ps.executeUpdate();
        }
    }

    @Override
    public void updateRide(Ride ride) throws SQLException {
        try (
                Connection conn = AppUtil.getInstance().getConnection();
                PreparedStatement ps
                = conn.prepareStatement("UPDATE USER_VEHICLE SET USER_ID = ?, "
                        + "VEHICLE_ID = ? , PICKUP_LOCATION = ?  , PUBLISH_DATE = ? "
                        + " , STATUS = ? , AVAILABLE_SEATS= ?,PICKUP_TIME= ? ,FINAL_DESTINATION= ?"
                        + "WHERE ID=" + ride.getId())) {

            ps.setInt(1, ride.getUserId());
            ps.setInt(2, ride.getVehicleId());
            ps.setString(3, ride.getPickupLocation());
            ps.setString(4, ride.getPublishDate());
            ps.setBoolean(5, ride.isStatus());
            ps.setInt(6, ride.getAvailableSeats());
            ps.setString(7, ride.getPickupTime());
            ps.setString(8, ride.getFinalDestination());

            ps.executeUpdate();
        }
    }

    @Override
    public void deleteRide(Ride ride) throws SQLException {
        try (
                Connection conn = AppUtil.getInstance().getConnection();
                Statement stmt = conn.createStatement()) {
            stmt.executeUpdate("DELETE FROM USER_VEHICLE WHERE ID=" + ride.getId());
        }
    }

    private int getLastId() throws SQLException {
        int id = 1;
        try (
                Connection conn = AppUtil.getInstance().getConnection();
                Statement stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery("SELECT MAX(ID) FROM USER_VEHICLE")) {
            if (rs.next()) {
                id = rs.getInt(1);
                ++id;
            }
        }
        return id;
    }

    @Override
    public void bookRide(Ride ride, int passengerID) throws SQLException {
        try (Connection conn = AppUtil.getInstance().getConnection();
             PreparedStatement ps = conn.prepareStatement("UPDATE USER_VEHICLE SET BOOKED_BY = ?, STATUS = ? "
                        + "WHERE ID=" + ride.getId())) {

            ps.setInt(1, passengerID);
            ps.setBoolean(2, false);

            ps.executeUpdate();
        }
    }
}

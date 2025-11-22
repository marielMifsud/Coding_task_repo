package org.example.service;

import org.example.models.driver.Driver;
import org.example.models.ride.Ride;
import org.example.response.RideResponse;

import java.util.List;
import java.util.Optional;

public interface IFindCabService {

    /**
     * Register a new driver  Returns the driver id.
     */
    int registerDriver(Integer driverId, double x, double y, boolean available);

    /**
     * Update an existing driver's location / availability.
     * Returns true if driver existed and was updated, false otherwise.
     */
    boolean updateDriver(int driverId, double x, double y, boolean available);

    /**
     * Request a ride for the given riderId and pickup coordinates.
     * Returns RideResponse if a driver was allocated, otherwise Optional.empty()
     */
    Optional<RideResponse> requestRide(int riderId, double pickupX, double pickupY);

    /**
     * Complete a ride by rideId. Returns true if ride was completed successfully.
     */
    boolean completeRide(int rideId);

    /**
     * Return up to `limit` nearest available drivers.
     */
    List<Driver> getNearestAvailableDrivers(double x, double y, int limit);

    /**
     * Find ride by id
     */
    Optional<Ride> findRideById(int rideId);

}

package org.example.repository;

import org.example.models.ride.Ride;

import java.util.List;
import java.util.Optional;

public interface IRideRepository {

    // Save a new ride
    Ride save(Ride ride);

    // Update existing ride
    Ride update(Ride ride);

    // Find ride by id
    Optional<Ride> findRideById(int rideId);

    // Get all rides
    List<Ride> getAllRides();
}

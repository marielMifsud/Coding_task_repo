package org.example.repository;

import org.example.models.ride.Ride;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

public class RideRepoImpl implements IRideRepository{

    private final ConcurrentHashMap<Integer, Ride> rideStorage = new ConcurrentHashMap<>();


    /**
     * Save a ride
     * @param ride object of the ride pojo
     * @return the whole ride object
     */
    @Override
    public Ride save(Ride ride) {
        rideStorage.put(ride.getId(), ride);
        return ride;
    }

    /**
     * Updates a ride
     * @param ride object of the ride pojo
     * @return the whole ride object
     */
    @Override
    public Ride update(Ride ride) {
        rideStorage.put(ride.getId(), ride);
        return ride;
    }

    /**
     * Finds a ride with the specific ride id
     * @param rideId the specific ride it
     * @return the ride object if the ride exist or null if it doesn't
     */
    @Override
    public Optional<Ride> findRideById(int rideId) {
        return Optional.ofNullable(rideStorage.get(rideId));
    }

    /**
     * Get all the rides inside the stored data
     * @return a list of stored rides
     */
    @Override
    public List<Ride> getAllRides() {
        return List.copyOf(rideStorage.values());
    }
}

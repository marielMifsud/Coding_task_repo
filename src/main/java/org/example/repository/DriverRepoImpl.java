package org.example.repository;

import org.example.models.driver.Driver;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

public class DriverRepoImpl implements IDriverRepository{

    private final ConcurrentHashMap<Integer, Driver> driverStorage = new ConcurrentHashMap<>();

    /**
     * Save a ride
     * @param driver object of the driver pojo
     * @return the whole driver object
     */
    @Override
    public Driver save(Driver driver) {
        driverStorage.put(driver.getId(), driver);
        return driver;
    }

    /**
     * Updates a driver
     * @param driver object of the driver pojo
     * @return the whole drive object
     */
    @Override
    public Driver update(Driver driver) {
        driverStorage.put(driver.getId(), driver);
        return driver;
    }

    /**
     * Finds a driver with the specific driver id
     * @param driverId the specific id to identify driver
     * @return if the driver exist returns the driver object, if not returns null
     */
    @Override
    public Optional<Driver> findDriverById(int driverId) {
        return Optional.ofNullable(driverStorage.get(driverId));
    }

    /**
     * Get all the drivers inside the stored data
     * @return a list of stored driverss
     */
    @Override
    public List<Driver> getAllDrivers() {
        return List.copyOf(driverStorage.values());
    }
}

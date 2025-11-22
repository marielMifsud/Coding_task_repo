package org.example.repository;

import org.example.models.driver.Driver;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

public interface IDriverRepository {

    // Save a new driver
    Driver save(Driver driver);

    // Update existing driver
    Driver update(Driver driver);

    // Find driver by id
    Optional<Driver> findDriverById(int driverId);

    // Get all drivers
    List<Driver> getAllDrivers();
}

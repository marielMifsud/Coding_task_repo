package org.example.service;

import org.example.models.driver.Driver;
import org.example.models.ride.Ride;
import org.example.repository.DriverRepoImpl;
import org.example.repository.RideRepoImpl;
import org.example.response.RideResponse;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;

import static java.awt.geom.Point2D.distance;

public class FindCabServiceImpl implements IFindCabService{

    private final DriverRepoImpl driverRepo;
    private final RideRepoImpl rideRepo;

    // Setting the automatic integers' id. (id will start from 1 not 0)
    private final AtomicInteger driverIdInteger = new AtomicInteger(1);
    private final AtomicInteger rideIdInteger = new AtomicInteger(1);

    public FindCabServiceImpl(DriverRepoImpl driverRepo, RideRepoImpl rideRepo) {
        this.driverRepo = driverRepo;
        this.rideRepo = rideRepo;
    }

    @Override
    public int registerDriver(Integer driverId, double x, double y, boolean available) {

        System.out.println("About to register driver \n");

        int id = driverIdInteger.getAndIncrement();

        Driver driver = new Driver(id, x, y);

        if(!available){
            driver.tryMarkUnavailable();
        }

        driverRepo.save(driver);
        System.out.println(" \n Driver has been saved with id: " + id);
        return id;

    }

    @Override
    public boolean updateDriver(int driverId, double x, double y, boolean available) {

        Optional<Driver> existing = driverRepo.findDriverById(driverId);

        if (existing.isEmpty()) {
            System.out.println("\n Driver does not exist \n");
            return false;
        }

        Driver driver = existing.get();

        // Thread-safe updates
        driver.setLocation(x, y);

        if (available) {
            driver.markAvailable();
        } else {
            driver.tryMarkUnavailable();
        }

        driverRepo.update(driver);
        System.out.println("\n Driver has been updated \n");
        return true;

    }

    @Override
    public Optional<RideResponse> requestRide(int riderId, double pickupX, double pickupY) {

        List<Driver> allDrivers = driverRepo.getAllDrivers();

        // Filter only available driver
        List<Driver> availableDrivers = new java.util.ArrayList<>(allDrivers.stream()
                .filter(Driver::isAvailable)
                .toList());

        if(availableDrivers.isEmpty()){
            System.out.println("\n There is no available drivers \n");
            return Optional.empty();
        }

        // Sort drivers by their distance to the pickup point
        availableDrivers.sort(Comparator.comparingDouble(
                driver -> distance(driver.getXCoordinates(), driver.getYCoordinates(), pickupX, pickupY)
        ));

        for (Driver driver : availableDrivers) {
            if(driver.tryMarkUnavailable()){

                // Driver is available  and can accept a ride
                int rideId = rideIdInteger.getAndIncrement();

                // Creates ride
                Ride ride = new Ride(rideId, riderId, driver.getId(), pickupX, pickupY);

                rideRepo.save(ride);
                System.out.println("\n Saved ride \n");

                driverRepo.update(driver);
                System.out.println("\n Driver has been updated \n");

                RideResponse rideResponse = new RideResponse(
                        ride.getId(),
                        driver.getId(),
                        driver.getXCoordinates(),
                        driver.getYCoordinates()
                );

                System.out.println("Successful ride allocation \n");
                return Optional.of(rideResponse);

            }
        }

        System.out.println("No driver can be claimed, all drivers are unavailable");
        return Optional.empty();
    }

    @Override
    public boolean completeRide(int rideId) {

        // Fetching ride
        Optional<Ride> rideList = rideRepo.findRideById(rideId);

        if(rideList.isEmpty()){
            System.out.println("\n Ride does not exist \n");
            return false;
        }

        Ride ride = rideList.get();

        // Fetch driver
        Optional<Driver> driverList = driverRepo.findDriverById(ride.getDriverId());

        if(driverList.isEmpty()){
            System.out.println("\n Driver was not found \n");
            return false;
        }

        Driver driver = driverList.get();

        // Setting the driver back to available and updating driver
        driver.markAvailable();
        driverRepo.update(driver);

        // Setting ride complete
        System.out.println(" \n Ride has been completed \n");
        ride.markRideComplete();
        rideRepo.update(ride);
        return true;
    }

    @Override
    public List<Driver> getNearestAvailableDrivers(double x, double y, int limit) {

        List<Driver> availableDrivers = driverRepo.getAllDrivers().stream()
                .filter(Driver::isAvailable)
                .toList();

        // Sorting the nearest driver first and the farthest last
        availableDrivers.sort(Comparator.comparingDouble(
                driver -> distance(driver.getXCoordinates(), driver.getYCoordinates(), x, y)
        ));

        // Ensures we return at most the limit provided drivers
        // else if it is less then the limit return all available drivers
        if(availableDrivers.size() > limit){
            availableDrivers = availableDrivers.subList(0, limit);
        }
        return availableDrivers;
    }

    @Override
    public Optional<Ride> findRideById(int rideId) {

        return rideRepo.findRideById(rideId);
    }
}

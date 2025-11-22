package org.example;

import org.example.models.driver.Driver;
import org.example.models.ride.Ride;
import org.example.repository.DriverRepoImpl;
import org.example.repository.RideRepoImpl;
import org.example.response.RideResponse;
import org.example.service.FindCabServiceImpl;

import java.util.List;
import java.util.Optional;
import java.util.Scanner;

public class RideServiceCLI {

    private final FindCabServiceImpl findCabService;
    private final DriverRepoImpl driverRepo;
    private final RideRepoImpl rideRepo;
    private final Scanner scanner = new Scanner(System.in);

    public RideServiceCLI(FindCabServiceImpl findCabService, DriverRepoImpl driverRepo, RideRepoImpl rideRepo){
        this.findCabService = findCabService;
        this.driverRepo = driverRepo;
        this.rideRepo = rideRepo;
    }

    public void start() {
        int choice = -1;
        while (choice != 0) {
            printMenu();
            choice = Integer.parseInt(scanner.nextLine());
            switch (choice) {
                case 1 -> registerDriverMenu();
                case 2 -> updateDriverMenu();
                case 3 -> requestRideMenu();
                case 4 -> completeRideMenu();
                case 5 -> nearestDriversMenu();
                case 6 -> findRideByIdMenu();
                case 7 -> findDriverByIdMenu();
                case 0 -> System.out.println("Exiting...");
                default -> System.out.println("Invalid choice!");
            }
        }
    }

    private void printMenu() {
        System.out.println("\n===== Ride Service Menu =====");
        System.out.println("1. Register a driver");
        System.out.println("2. Update a driver");
        System.out.println("3. Request a ride");
        System.out.println("4. Complete a ride");
        System.out.println("5. Get nearest available drivers");
        System.out.println("6. Find ride by ID");
        System.out.println("7. Find driver by ID");
        System.out.println("0. Exit");
        System.out.print("Enter your choice: ");
    }

    // Each menu method can prompt for inputs and call the service
    private void registerDriverMenu() {
        System.out.print("Enter X coordinate: ");
        double x = Double.parseDouble(scanner.nextLine());
        System.out.print("Enter Y coordinate: ");
        double y = Double.parseDouble(scanner.nextLine());
        System.out.print("Is the driver available? (true/false): ");
        boolean available = Boolean.parseBoolean(scanner.nextLine());
        int driverId = findCabService.registerDriver(null, x, y, available);
        System.out.println("Driver registered with ID: " + driverId);
    }

    private void requestRideMenu() {
        System.out.print("Enter Rider ID: ");
        int riderId = Integer.parseInt(scanner.nextLine());
        System.out.print("Enter Pickup X: ");
        double px = Double.parseDouble(scanner.nextLine());
        System.out.print("Enter Pickup Y: ");
        double py = Double.parseDouble(scanner.nextLine());

        Optional<RideResponse> rideOpt = findCabService.requestRide(riderId, px, py);
        rideOpt.ifPresentOrElse(
                r -> System.out.println("Ride allocated! Driver ID: " + r.driverId + ", Ride ID: " + r.rideId),
                () -> System.out.println("No available drivers.")
        );
    }

    private void updateDriverMenu() {
        System.out.print("Enter Driver ID: ");
        int driverId = Integer.parseInt(scanner.nextLine());
        System.out.print("Enter new X coordinate: ");
        double x = Double.parseDouble(scanner.nextLine());
        System.out.print("Enter new Y coordinate: ");
        double y = Double.parseDouble(scanner.nextLine());
        System.out.print("Is the driver available? (true/false): ");
        boolean available = Boolean.parseBoolean(scanner.nextLine());

        boolean success = findCabService.updateDriver(driverId, x, y, available);
        if (success) System.out.println("Driver updated successfully.");
        else System.out.println("Driver not found.");
    }

    private void completeRideMenu() {
        System.out.print("Enter Ride ID to complete: ");
        int rideId = Integer.parseInt(scanner.nextLine());
        boolean success = findCabService.completeRide(rideId);
        if (success) System.out.println("Ride completed successfully.");
        else System.out.println("Ride not found.");
    }

    private void nearestDriversMenu() {
        System.out.print("Enter X coordinate: ");
        double x = Double.parseDouble(scanner.nextLine());
        System.out.print("Enter Y coordinate: ");
        double y = Double.parseDouble(scanner.nextLine());
        System.out.print("Enter limit: ");
        int limit = Integer.parseInt(scanner.nextLine());

        List<Driver> drivers = findCabService.getNearestAvailableDrivers(x, y, limit);
        if (drivers.isEmpty()) System.out.println("No available drivers.");
        else drivers.forEach(d -> System.out.println("Driver ID: " + d.getId() + ", X: " + d.getXCoordinates() + ", Y: " + d.getYCoordinates()));
    }

    private void findRideByIdMenu() {
        System.out.print("Enter Ride ID: ");
        int rideId = Integer.parseInt(scanner.nextLine());
        Optional<Ride> rideOpt = findCabService.findRideById(rideId);
        rideOpt.ifPresentOrElse(
                r -> System.out.println("Ride found: ID=" + r.getId() + ", Driver ID=" + r.getDriverId()),
                () -> System.out.println("Ride not found.")
        );
    }

    private void findDriverByIdMenu() {
        System.out.print("Enter Driver ID: ");
        int driverId = Integer.parseInt(scanner.nextLine());
        Optional<Driver> driverOpt = driverRepo.findDriverById(driverId);
        driverOpt.ifPresentOrElse(
                d -> System.out.println("Driver found: ID=" + d.getId() + ", X=" + d.getXCoordinates() + ", Y=" + d.getYCoordinates() + ", Available=" + d.isAvailable()),
                () -> System.out.println("Driver not found.")
        );
    }
}

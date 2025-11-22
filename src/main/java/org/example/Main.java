package org.example;

import org.example.repository.DriverRepoImpl;
import org.example.repository.RideRepoImpl;
import org.example.service.FindCabServiceImpl;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {
        DriverRepoImpl driverRepo = new DriverRepoImpl();
        RideRepoImpl rideRepo = new RideRepoImpl();
        FindCabServiceImpl findCabService = new FindCabServiceImpl(driverRepo, rideRepo);

        RideServiceCLI cli = new RideServiceCLI(findCabService, driverRepo, rideRepo);
        cli.start();
    }
}
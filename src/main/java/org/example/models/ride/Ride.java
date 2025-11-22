package org.example.models.ride;

import org.example.models.enums.RideStatus;

import java.util.concurrent.atomic.AtomicReference;

public class Ride {


    private final int id;
    private final int riderId;
    private final int driverId;
    private final double pickupX;
    private final double pickupY;

    private final AtomicReference<RideStatus> rideStatus = new AtomicReference<>(RideStatus.ACTIVE);

    public Ride(int id, int riderId, int driverId, double pickupX, double pickupY) {
        this.id = id;
        this.riderId = riderId;
        this.driverId = driverId;
        this.pickupX = pickupX;
        this.pickupY = pickupY;
    }

    public int getId() {
        return id;
    }

    public int getRiderId() {
        return riderId;
    }

    public int getDriverId() {
        return driverId;
    }

    public double getPickupX() {
        return pickupX;
    }

    public double getPickupY() {
        return pickupY;
    }

    public RideStatus getStatus() {
        return rideStatus.get();
    }

    public void markRideComplete() {
        rideStatus.set(RideStatus.COMPLETED);
    }






}

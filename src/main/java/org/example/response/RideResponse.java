package org.example.response;

public class RideResponse {

    public final int rideId;
    public final int driverId;
    public final double driverX;
    public final double driverY;

    public RideResponse(int rideId, int driverId, double driverX, double driverY) {
        this.rideId = rideId;
        this.driverId = driverId;
        this.driverX = driverX;
        this.driverY = driverY;
    }
}

package org.example.models.driver;

import java.util.concurrent.atomic.AtomicBoolean;

public class Driver {

    private final int id;

    // Using volatile since it provides a safe and visibility of variables between threads
    // So when a thread writes other threads can see the updated value immediately
    // without volatile threads might be reading from a cached/stale value
    private volatile double xCoordinates;
    private volatile double yCoordinates;
    private final AtomicBoolean available = new AtomicBoolean(true);

    public Driver(int id, double xCoordinates, double yCoordinates) {
        this.id = id;
        this.xCoordinates = xCoordinates;
        this.yCoordinates = yCoordinates;
        this.available.set(true);
    }

    public int getId() {
        return id;
    }

    public double getXCoordinates() {
        return xCoordinates;
    }

    public double getYCoordinates() {
        return yCoordinates;
    }

    public boolean isAvailable() {
        return available.get();
    }

    public synchronized void setLocation(double x, double y){
        this.xCoordinates = x;
        this.yCoordinates = y;
    }

    public void markAvailable() {
        available.set(true);
    }

    public boolean tryMarkUnavailable() {
        // If driver is currently available so true, set it to unavailable false ONLY IF no other thread changed it first
        return available.compareAndSet(true, false);
    }

}

package me.kgdo.upanddown;

public class PickupRequest {

    private int destinationFloor;

    private int pickupFloor;

    private Object person;

    public PickupRequest(int pickupFloor, int destinationFloor) {
        this.pickupFloor = pickupFloor;
        this.destinationFloor = destinationFloor;
    }

    public int getDestinationFloor() {
        return destinationFloor;
    }

    public int getPickupFloor() {
        return pickupFloor;
    }
}

package me.kgdo.upanddown;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Elevator {

    public static final String ELEVATOR_ID_KEY = "elevator-id";
    public static final String CURRENT_FLOOR_KEY = "current-floor";
    public static final String CURRENT_DIRECTION_KEY = "current-direction";
    public static final String CURRENT_PICKUP_KEY = "current-pickup";
    public static final String PENDING_PICKUPS_KEY = "pending-pickups";

    private int elevatorId;

    private int currentFloor;

    private List<PickupRequest> pickupRequests;

    private Direction currentDirection = null;

    private int currentDestination;

    private PickupRequest currentServingPickupRequest;

    Elevator(int elevatorId) {
        this.elevatorId = elevatorId;
        this.pickupRequests = new ArrayList<PickupRequest>();
        this.currentFloor = 0;
    }

    void queueRequest(PickupRequest pickupRequest) {

        this.pickupRequests.add(pickupRequest);
    }

    void update(int floorNumber) {
        this.currentFloor = floorNumber;
    }


    // Update its status according to the pending requests.
    void refresh() {

        // Standby, can process pending pickup requests
        if (currentDirection == null) {
            // Find new Request to put as current
            if (pickupRequests.size() > 0) {
                PickupRequest pr = getPickupRequest();
                this.currentServingPickupRequest = pr;
                this.pickupRequests.remove(pr);

                // Go pickup person
                if (currentServingPickupRequest.getPickupFloor() != currentFloor){
                    currentDestination = currentServingPickupRequest.getPickupFloor();
                }
                // Somebody to pickup already on the same floor as us.
                else {
                    currentDestination = currentServingPickupRequest.getDestinationFloor();
                }
            }
        }
        // Moving
        else {
            // Arrived, either to pickup someone, or someone's arrived at its destination.
            if (currentDestination == currentFloor) {
                // Arrived to pickup someone.
                if (currentDestination == currentServingPickupRequest.getPickupFloor()){
                    currentDestination = currentServingPickupRequest.getDestinationFloor();
                }

                // Arrived to destination of someone.
                else if (currentDestination == currentServingPickupRequest.getDestinationFloor()){
                    // nobody's served anymore
                    currentServingPickupRequest = null;

                    // set current "status" to Standby
                    currentDirection = null;

                    // TODO: multiple people could be arrived at the floor, clean them up
                }
            }
        }
    }

    // Should only change currentDirection (indicator of movement)
    void maybeMove() {
        if (currentFloor == ElevatorControlSystem.MAX_FLOOR) {
            return;
        }

        // If in Moving state
        if (currentServingPickupRequest != null) {
            if (currentDestination > currentFloor) {
                currentDirection = Direction.UP;
                currentFloor ++;
            }
            else if (currentDestination < currentFloor) {
                currentDirection = Direction.DOWN;
                currentFloor --;
            }
        }
    }

    int getElevatorId() {
        return this.elevatorId;
    }

    // To be optimized (closest, direction)
    private PickupRequest getPickupRequest() {
        return this.pickupRequests.get(0);
    }

    public Map<String, Object> getStatus() {
        Map<String, Object> status = new HashMap<String, Object>();
        status.put(ELEVATOR_ID_KEY, elevatorId);
        status.put(CURRENT_FLOOR_KEY, currentFloor);
        status.put(CURRENT_DIRECTION_KEY, currentDirection);
        status.put(CURRENT_PICKUP_KEY, currentServingPickupRequest);
        status.put(PENDING_PICKUPS_KEY, pickupRequests);

        return status;
    }
}

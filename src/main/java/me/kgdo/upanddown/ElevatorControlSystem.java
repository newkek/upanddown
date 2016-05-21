package me.kgdo.upanddown;

import java.util.*;


public class ElevatorControlSystem {

    public static final int MAX_FLOOR = 20;

    private Map<Integer, Elevator> elevators;

    public ElevatorControlSystem(int numberOfElevators) {
        this.elevators = new HashMap<Integer, Elevator>(numberOfElevators);
        for (int i = 0; i < numberOfElevators; i++) {
            this.elevators.put(i, new Elevator(i));
        }
    }

    public void step() {
        // Update every elevators because requests may have been added.
        elevators.values().forEach(me.kgdo.upanddown.Elevator::refresh);
        elevators.values().forEach(me.kgdo.upanddown.Elevator::maybeMove);
    }

    public void pickup(int pickupFloor, int destinationFloor) {
        PickupRequest pickupRequest = new PickupRequest(pickupFloor, destinationFloor);
        Elevator elevator = findAvailableElevator(pickupRequest);
        if (elevator != null) {
            elevator.queueRequest(pickupRequest);
        }
        else {
            // TODO
            System.out.println("There's no elevator available, all full.");
        }
    }

    // INFO : you can only set an Elevator's current floor.
    public void update(int elevatorId, int floorNumber) {
        Elevator elevator = elevators.get(elevatorId);
        elevator.update(floorNumber);
    }

    // Status returns a Map for easy printing/debug in Java
    public Map<String, Object> status(int elevatorId){
        return elevators.get(elevatorId).getStatus();
    }

    public List<Map<String, Object>> status() {
        final List<Map<String, Object>> status = new ArrayList<Map<String, Object>>();
        elevators.values().forEach(e -> status.add(e.getStatus()));
        return status;
    }

    // WARNING : May return *null*
    private Elevator findAvailableElevator(PickupRequest pickupRequest) {
        List<Elevator> allAvailableElevators = new ArrayList<>();
        for (Elevator elevator : elevators.values()) {
            if (elevator.getStatus().get(Elevator.CURRENT_DIRECTION_KEY) == null) {
                allAvailableElevators.add(elevator);
            }
        }

        Collection<Elevator> findClosestElevator;
        if (allAvailableElevators.size() == 0)
            findClosestElevator = elevators.values();
        else
            findClosestElevator = allAvailableElevators;


        Elevator closest = null;
        int closestDistance = Integer.MAX_VALUE;
        for (Elevator elevator : findClosestElevator) {
            // Algorithm to find the closest elevator
            int distance = Math.abs(pickupRequest.getPickupFloor() - (int)elevator.getStatus().get(Elevator.CURRENT_FLOOR_KEY));
            if (distance < closestDistance) {
                closestDistance = distance;
                closest = elevator;
            }
        }

        return closest;
    }

}

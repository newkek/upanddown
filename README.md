# UpAndDown

Elevator management service simulation.

## Concept

A `ElevatorControlSystem` class manages multiple functioning elevators, persons can submit Pickup requests to the system and the system will send an elevator to pick them up.

The system will choose if possible, the first closest available elevator, if no elevator is available an the closest one will be chosen, and will come after processing its requests.

Pickup requests have to specify the floor on which the request is made, and the destination floor.

## Testing

Examples can be found in the `src/test/` directory. There are some test simulations I left there that I used when testing, plus some explanations.

Utils to print the status and the Elevators can be found in `me.kgdo.upanddown.util`.

## Example running

```java

        // ElevatorControSystem takes the number of elevators running in parameter.
        ElevatorControlSystem ecs = new ElevatorControlSystem(4);

        ecs.pickup(3, 6);
        // Elevator 0 is chosen for the pickup because first available.
        ecs.step();
        ecs.step();
        ecs.step();
        prettyPrintStatus(ecs);

        ecs.pickup(0, 8);
        // Elevator 1 is chosen for the pickup because 0 is busy.
        ecs.step();
        ecs.step();
        ecs.step();
        ecs.step();
        ecs.step();
        ecs.step();
        ecs.step();
        ecs.step();
        ecs.step();
        ecs.step();
        prettyPrintStatus(ecs);

        ecs.pickup(0, 5);
        // Elevator 2 is chosen for the pickup because it's the closest available.
        ecs.step();
        prettyPrintStatus(ecs);

```

## Comments

I tried to put comments on the algorithmic part, so Finding an elevator, and the elevator movements logic.

## Time

Started at : 15:11 (British Standard Time)
Finished at : 19:43 (British Standard Time)

4h32min spent.

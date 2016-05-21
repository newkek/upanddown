package me.kgdo.upanddown;

import org.testng.annotations.Test;

import static me.kgdo.upanddown.Direction.*;
import static me.kgdo.upanddown.util.PrettyPrintUtils.*;

import static org.assertj.core.api.Assertions.*;

public class ElevatorControlSystemTest {

    @Test
    public void simulationTest1() {
        ElevatorControlSystem ecs = new ElevatorControlSystem(4);

        ecs.update(1, 3);

        assertThat(ecs.status(0).get(Elevator.CURRENT_FLOOR_KEY)).isEqualTo(0);
        assertThat(ecs.status(1).get(Elevator.CURRENT_FLOOR_KEY)).isEqualTo(3);
    }

    @Test
    public void simulationTest2() {
        ElevatorControlSystem ecs = new ElevatorControlSystem(4);
        ecs.update(1, 3);
        ecs.step();
        prettyPrintElevator(ecs, 1);

        fastForwardSimu(ecs, 30);

        prettyPrintElevator(ecs, 1);
        prettyPrintElevator(ecs, 0);
    }

    @Test
    public void simulationTest3() {
        ElevatorControlSystem ecs = new ElevatorControlSystem(4);

        ecs.update(0, 4);
        ecs.pickup(0, 3);

        ecs.step();
        ecs.step();
        ecs.step();


//        fastForwardSimu(ecs, 30);

        prettyPrintStatus(ecs);
    }

    @Test
    public void simulationTest4() {
        ElevatorControlSystem ecs = new ElevatorControlSystem(4);

        ecs.pickup(3, 6);
        // Elevator 0 is chosen for the pickup because first available.
        ecs.step();
        ecs.step();
        ecs.step();

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

        ecs.pickup(0, 5);
        // Elevator 2 is chosen for the pickup because it's the closest available.
        fastForwardSimu(ecs, 20);
        prettyPrintStatus(ecs);
    }


    @Test
    public static void simulationTest5() {
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
    }

    public static void fastForwardSimu(ElevatorControlSystem ecs, int numberOfStepsToApply) {
        for (int i = 0; i < numberOfStepsToApply; i++) {
            ecs.step();
        }
    }
}

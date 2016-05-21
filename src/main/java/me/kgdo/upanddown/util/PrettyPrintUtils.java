package me.kgdo.upanddown.util;

import me.kgdo.upanddown.ElevatorControlSystem;
import org.apache.commons.collections.MapUtils;

import java.util.Map;

public class PrettyPrintUtils {

    public static void prettyPrintStatus(ElevatorControlSystem ecs) {
        System.out.println("--------------   CURRENT STATUS   -----------------");
        for (Map<String, Object> status : ecs.status()) {
            MapUtils.debugPrint(System.out, "Elevator", status);
        }
        System.out.println("--------------   END STATUS   ------------------");
    }

    public static void prettyPrintElevator(ElevatorControlSystem ecs, int elevatorId) {
        System.out.println("///////////// ELEVATOR PRINT ////////////");
        MapUtils.debugPrint(System.out, "Elevator", ecs.status(elevatorId));
        System.out.println("///////////// END ELEVATOR PRINT ///////////");
    }
}

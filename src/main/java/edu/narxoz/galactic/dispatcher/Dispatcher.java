package edu.narxoz.galactic.dispatcher;

import edu.narxoz.galactic.cargo.Cargo;
import edu.narxoz.galactic.drones.Drone;
import edu.narxoz.galactic.drones.DroneStatus;
import edu.narxoz.galactic.task.DeliveryTask;
import edu.narxoz.galactic.task.TaskState;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

public class Dispatcher {

    public Result assignTask(DeliveryTask task, Drone drone) {
        if (task == null || drone == null) {
            return Result.failure("task and drone must not be null");
        }

        if (drone.getStatus() != DroneStatus.IDLE) {
            return Result.failure("drone is not IDLE");
        }

        Cargo cargo = task.getCargo();
        if (cargo == null) {
            return Result.failure("task cargo is null");
        }

        if (cargo.getWeightKg() > drone.getMaxPayloadKg()) {
            return Result.failure("cargo overweight for this drone");
        }

        if (task.getState() != TaskState.CREATED) {
            return Result.failure("task state is not CREATED");
        }

        setTaskState(task, TaskState.ASSIGNED);
        setTaskAssignedDrone(task, drone);
        setDroneStatus(drone, DroneStatus.IN_FLIGHT);

        return Result.success();
    }

    public Result completeTask(DeliveryTask task) {
        if (task == null) {
            return Result.failure("task must not be null");
        }

        if (task.getState() != TaskState.ASSIGNED) {
            return Result.failure("task state is not ASSIGNED");
        }

        Drone drone = task.getAssignedDrone();
        if (drone == null) {
            return Result.failure("assigned drone is null");
        }

        if (drone.getStatus() != DroneStatus.IN_FLIGHT) {
            return Result.failure("drone is not IN_FLIGHT");
        }

        setTaskState(task, TaskState.DONE);
        setDroneStatus(drone, DroneStatus.IDLE);

        return Result.success();
    }

    private static void setTaskState(DeliveryTask task, TaskState state) {
        try {
            Method m = DeliveryTask.class.getDeclaredMethod("setState", TaskState.class);
            m.setAccessible(true);
            m.invoke(task, state);
            return;
        } catch (Exception ignored) {
        }

        try {
            Field f = DeliveryTask.class.getDeclaredField("state");
            f.setAccessible(true);
            f.set(task, state);
        } catch (Exception e) {
            throw new IllegalStateException("Cannot set task state", e);
        }
    }

    private static void setTaskAssignedDrone(DeliveryTask task, Drone drone) {
        try {
            Method m = DeliveryTask.class.getDeclaredMethod("setAssignedDrone", Drone.class);
            m.setAccessible(true);
            m.invoke(task, drone);
            return;
        } catch (Exception ignored) {
        }

        try {
            Field f = DeliveryTask.class.getDeclaredField("assignedDrone");
            f.setAccessible(true);
            f.set(task, drone);
        } catch (Exception e) {
            throw new IllegalStateException("Cannot set assigned drone", e);
        }
    }

    private static void setDroneStatus(Drone drone, DroneStatus status) {
        try {
            Method m = Drone.class.getDeclaredMethod("setStatus", DroneStatus.class);
            m.setAccessible(true);
            m.invoke(drone, status);
            return;
        } catch (Exception ignored) {
        }

        try {
            Field f = Drone.class.getDeclaredField("status");
            f.setAccessible(true);
            f.set(drone, status);
        } catch (Exception e) {
            throw new IllegalStateException("Cannot set drone status", e);
        }
    }
}

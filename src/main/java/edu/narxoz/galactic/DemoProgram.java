package edu.narxoz.galactic;

import edu.narxoz.galactic.bodies.Planet;
import edu.narxoz.galactic.bodies.SpaceStation;
import edu.narxoz.galactic.cargo.Cargo;
import edu.narxoz.galactic.dispatcher.Dispatcher;
import edu.narxoz.galactic.dispatcher.Result;
import edu.narxoz.galactic.drones.Drone;
import edu.narxoz.galactic.factory.DroneCreator;
import edu.narxoz.galactic.factory.HeavyDroneCreator;
import edu.narxoz.galactic.factory.LightDroneCreator;
import edu.narxoz.galactic.task.DeliveryTask;

public class DemoProgram {

    public static void main(String[] args) {
        Planet earth = new Planet("Earth", 0.0, 0.0, "Nitrogen-Oxygen");
        SpaceStation station = new SpaceStation("Orbital-1", 30.0, 40.0, 3);

        DroneCreator lightCreator = new LightDroneCreator();
        DroneCreator heavyCreator = new HeavyDroneCreator();

        Drone light = lightCreator.create("LD-01", 10.0);
        Drone heavy = heavyCreator.create("HD-99", 25.0);

        Cargo cargo = new Cargo(15.0, "Supply crate");

        DeliveryTask task = new DeliveryTask(earth, station, cargo);
        Dispatcher dispatcher = new Dispatcher();

        System.out.println("1) Try assigning overweight cargo to LightDrone:");
        Result r1 = dispatcher.assignTask(task, light);
        System.out.println("   ok=" + r1.ok() + ", reason=" + r1.reason());
        System.out.println("   taskState=" + task.getState() + ", lightStatus=" + light.getStatus());
        System.out.println();

        System.out.println("2) Assign the same task to HeavyDrone:");
        Result r2 = dispatcher.assignTask(task, heavy);
        System.out.println("   ok=" + r2.ok() + ", reason=" + r2.reason());
        System.out.println("   taskState=" + task.getState() + ", heavyStatus=" + heavy.getStatus());
        System.out.println();

        System.out.println("3) Estimated time (km/min):");
        double eta = task.estimateTime();
        System.out.println("   estimateTime=" + eta);
        System.out.println();

        System.out.println("4) Complete task + final statuses:");
        Result r3 = dispatcher.completeTask(task);
        System.out.println("   complete ok=" + r3.ok() + ", reason=" + r3.reason());
        System.out.println("   final taskState=" + task.getState() + ", heavyStatus=" + heavy.getStatus());
    }
}

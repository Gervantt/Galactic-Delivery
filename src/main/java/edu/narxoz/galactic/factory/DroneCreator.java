package edu.narxoz.galactic.factory;

import edu.narxoz.galactic.drones.Drone;

public abstract class DroneCreator {

    public final Drone create(String id, double maxPayloadKg) {
        return createDrone(id, maxPayloadKg);
    }
    protected abstract Drone createDrone(String id, double maxPayloadKg);
}

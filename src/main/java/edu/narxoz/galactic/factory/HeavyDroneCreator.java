package edu.narxoz.galactic.factory;

import edu.narxoz.galactic.drones.Drone;
import edu.narxoz.galactic.drones.HeavyDrone;

public class HeavyDroneCreator extends DroneCreator {

    @Override
    protected Drone createDrone(String id, double maxPayloadKg) {
        return new HeavyDrone(id, maxPayloadKg);
    }
}

package edu.narxoz.galactic.factory;

import edu.narxoz.galactic.drones.Drone;
import edu.narxoz.galactic.drones.LightDrone;

public class LightDroneCreator extends DroneCreator {

    @Override
    protected Drone createDrone(String id, double maxPayloadKg) {
        return new LightDrone(id, maxPayloadKg);
    }
}
